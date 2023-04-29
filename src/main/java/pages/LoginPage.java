package pages;

import com.relevantcodes.extentreports.LogStatus;

import base.Page;

public class LoginPage extends Page {

	public LoginPage() {
		// TODO Auto-generated constructor stub
	}
	public ZohoApppage doLogin(String username,String password)
	{
		try
		{
			System.out.println("***************************** doLogin *********************************");
			test.log(LogStatus.INFO,
					"***************************** doLogin*********************************");
			//click ("Google_icon_XPATH","Clicking google icon");
			Thread.sleep(2000);
			type("Email_XPATH",username,"Typing email");
			click("Nextbtn_XPATH","Clicking next button");
			Thread.sleep(2000);
			type("Password_XPATH",password,"Typing Password");
			click("Sign_in_btn_XPATH","Clicking Sign In button");
			return new ZohoApppage();
		}
		catch (Exception e)
		{
			String nameofCurrMethod = new Throwable().getStackTrace()[0].getMethodName(); 
		}
		return null;
	}
}
