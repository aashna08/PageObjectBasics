package TestCases;

import java.util.Hashtable;

import org.testng.annotations.Test;

import pages.HomePage;
import pages.LoginPage;
import utilities.Utilities;

public class LoginTest extends TestBase {

	public LoginTest() {
		// TODO Auto-generated constructor stub
	}
		
		@Test(dataProviderClass = Utilities.class, dataProvider = "dp")
		public void loginTest(Hashtable<String,String> data){
			
			HomePage home = new HomePage();
			LoginPage lp = home.goToSignIn();
			lp.doLogin(data.get("username"), data.get("password"));
			//Assert.fail("Login test failed");
		
			
		}

	}


