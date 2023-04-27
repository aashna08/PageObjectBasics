package TestCases;

import org.testng.annotations.AfterSuite;

import base.Page;

public class TestBase {

	public TestBase() {
		// TODO Auto-generated constructor stub
	}
	
	@AfterSuite
	public void tearDown(){
		
		Page.quit();
		
	}

}
