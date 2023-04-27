package pages;

import base.Page;

public class HomePage extends Page{

	public void goToCustomers()
	{
		
	}
	
	public LoginPage goToSignIn()
	{
		click("Sign_in_xpath");
		return new LoginPage();
	}

}
