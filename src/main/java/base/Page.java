package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
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
	public static WebElement element;

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


			if(config.getProperty("browser").equalsIgnoreCase("chrome"))
			{
				WebDriverManager.chromedriver().setup();	
				driver=new ChromeDriver();
				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("profile.default_content_settings.popups", 0);
				
				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("prefs", chromePrefs);
				options.addArguments("incognito");
				options.addArguments("chrome.switches", "--disable-extensions");
				options.addArguments("--no-sandbox");
				options.addArguments("test-type");
				options.addArguments("--start-maximized");
				
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

	}

	public static void quit(){

		driver.quit();

	}

	public static void click(String locator,String actionInfo) {
		try
		{
			if (locator.endsWith("_CSS")) {
				element=driver.findElement(By.cssSelector(OR.getProperty(locator)));
			} else if (locator.endsWith("_XPATH")) {
				element=driver.findElement(By.xpath(OR.getProperty(locator)));
			} else if (locator.endsWith("_ID")) {
				element=driver.findElement(By.id(OR.getProperty(locator)));
			}

			element.click();
			System.out.println("Clicking on an Element : "+locator);
			log.debug("Clicking on an Element : "+locator);
			test.log(LogStatus.INFO, "Clicking on : " + locator);

		}
		catch (Exception e) {
			System.out.println("RUNTIME_ERROR : : Not able to  : " + actionInfo);
			throw new java.lang.RuntimeException(
					"RUNTIME_ERROR : : Not able to  : " + actionInfo + "--------------->" + e);

		}

	}

	public static void type(String locator, String value,String actionInfo) {
		try
		{
			if (locator.endsWith("_CSS")) {
				element=driver.findElement(By.cssSelector(OR.getProperty(locator)));
			} else if (locator.endsWith("_XPATH")) {
				element=driver.findElement(By.xpath(OR.getProperty(locator)));
			} else if (locator.endsWith("_ID")) {
				element=driver.findElement(By.id(OR.getProperty(locator)));
			}
			element.sendKeys(value);
			System.out.println("Typing in an Element : "+locator+" entered value as : "+value);
			log.debug("Typing in an Element : "+locator+" entered value as : "+value);
			test.log(LogStatus.INFO, "Typing in : " + locator + " entered value as " + value);
		}
		catch (Exception e) {
			System.out.println("RUNTIME_ERROR : : Not able to  : " + actionInfo);
			throw new java.lang.RuntimeException(
					"RUNTIME_ERROR : : Not able to  : " + actionInfo + "--------------->" + e);

		}
	}

	public void select(String locator, String value,String actionInfo) {
		try
		{
			if (locator.endsWith("_CSS")) {
				element = driver.findElement(By.cssSelector(OR.getProperty(locator)));
			} else if (locator.endsWith("_XPATH")) {
				element = driver.findElement(By.xpath(OR.getProperty(locator)));
			} else if (locator.endsWith("_ID")) {
				element = driver.findElement(By.id(OR.getProperty(locator)));
			}

			Select select = new Select(element);
			select.selectByVisibleText(value);
			System.out.println("Selecting from an element : "+locator+" value as : "+value);
			log.debug("Selecting from an element : "+locator+" value as : "+value);
			test.log(LogStatus.INFO, "Selecting from dropdown : " + locator + " value as " + value);
		}
		catch (Exception e) {
			System.out.println("RUNTIME_ERROR : : Not able to  : " + actionInfo);
			throw new java.lang.RuntimeException(
					"RUNTIME_ERROR : : Not able to  : " + actionInfo + "--------------->" + e);

		}
	}
//boolean method
	public boolean isElementPresent(By by) {

		try {

			driver.findElement(by);
			return true;

		} catch (NoSuchElementException e) {

			return false;

		}


	}



}
