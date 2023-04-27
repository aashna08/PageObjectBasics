package pages;

import base.Page;

public class LoginPage extends Page {

	public LoginPage() {
		// TODO Auto-generated constructor stub
	}
   public ZohoApppage doLogin(String username,String password)
   {
	   try
	   {
	   click ("Google_icon_xpath");
	   Thread.sleep(2000);
	   type("Email_xpath","username");
	   click("Nextbtn_xpath");
	   Thread.sleep(2000);
	   click("Nextbtn_xpath");
	   }
	   catch (Exception e)
	   {
		   
	   }
	   return new ZohoApppage();
   }
}
