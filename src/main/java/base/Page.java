package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.ExcelReader;
import utilities.ExtentManager;

//base package11
public class Page {
	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log=Logger.getLogger(Page.class.getName());
	public static String browser;
	public static WebDriverWait wait;
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\TestData.xlsx");
	public ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;

	public Page() {
		if(driver==null)
		{
			PropertyConfigurator.configure(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\log4j.properties");
			try {
				fis = new FileInputStream(System.getProperty("user.dir")
						+ "\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				log.debug("Config file loaded !!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				log.debug("OR file loaded !!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Jenkins Browser filter configuration
			if (System.getenv("browser") != null && !System.getenv("browser").isEmpty()) {

				browser = System.getenv("browser");
			} else {

				browser = config.getProperty("browser");

			}

			config.setProperty("browser", browser);
		}
		
		if(config.getProperty("browser").equalsIgnoreCase("chrome"))
		{
		WebDriverManager.chromedriver().setup();	
		driver=new ChromeDriver();
		log.info("chrome loaded successfully");
		}
		else if (config.getProperty("browser").equalsIgnoreCase("firefox"))
		{
			WebDriverManager.firefoxdriver().setup();
			driver=new FirefoxDriver();
			log.debug("firefox loaded successfully");
		}
		driver.get(config.getProperty("testsiteurl"));
		log.debug("Navigated to : " + config.getProperty("testsiteurl"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")),
				TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 5);
		
	}
	
public static void quit(){
		
		driver.quit();
		
	}

public static void click(String locator) {

	if (locator.endsWith("_CSS")) {
		driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
	} else if (locator.endsWith("_XPATH")) {
		driver.findElement(By.xpath(OR.getProperty(locator))).click();
	} else if (locator.endsWith("_ID")) {
		driver.findElement(By.id(OR.getProperty(locator))).click();
	}
	log.debug("Clicking on an Element : "+locator);
	test.log(LogStatus.INFO, "Clicking on : " + locator);
}

public static void type(String locator, String value) {

	if (locator.endsWith("_CSS")) {
		driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
	} else if (locator.endsWith("_XPATH")) {
		driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
	} else if (locator.endsWith("_ID")) {
		driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
	}

	log.debug("Typing in an Element : "+locator+" entered value as : "+value);
	
	test.log(LogStatus.INFO, "Typing in : " + locator + " entered value as " + value);

}

static WebElement dropdown;

public void select(String locator, String value) {

	if (locator.endsWith("_CSS")) {
		dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
	} else if (locator.endsWith("_XPATH")) {
		dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
	} else if (locator.endsWith("_ID")) {
		dropdown = driver.findElement(By.id(OR.getProperty(locator)));
	}
	
	Select select = new Select(dropdown);
	select.selectByVisibleText(value);

	log.debug("Selecting from an element : "+locator+" value as : "+value);
	test.log(LogStatus.INFO, "Selecting from dropdown : " + locator + " value as " + value);

}

public boolean isElementPresent(By by) {

	try {

		driver.findElement(by);
		return true;

	} catch (NoSuchElementException e) {

		return false;

	}
	

}



}
