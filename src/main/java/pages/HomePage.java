package pages;

import com.relevantcodes.extentreports.LogStatus;

import base.Page;



public class HomePage extends Page {

	public LoginPage goToSignIn()
	{
	try
	{
		System.out.println("***************************** LoginPage *********************************");
		test.log(LogStatus.INFO,
				"***************************** LoginPage *********************************");
	click("Sign_in_XPATH","Clicking sign in btn");
	return new LoginPage();
	}
	catch(Exception e)
	{
		//String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName();
	}
	return new LoginPage();
}

	
		
	

}
