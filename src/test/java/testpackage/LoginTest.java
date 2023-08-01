package testpackage;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import basepackage.BaseClass;
import io.github.bonigarcia.wdm.WebDriverManager;
import pageobject.EnterUsername;
import pageobject.HomePage;
import util.DataUtil;
import util.MyXLSReader;

@SuppressWarnings("unused")
public class LoginTest extends BaseClass {
	WebDriver driver;
	MyXLSReader excelReader;
	
	@Test(dataProvider="datasupp")
	public void testLogin(HashMap<String, String>hMap) {
		
		if(!DataUtil.isRunnable(excelReader, "LoginTest", "TesTcases") || hMap.get("Runmode").equals("N")) {
			
			throw new SkipException("Run mode is set to N, hence not exceuted");
		}
		
		driver = Openbrowser(hMap.get("Browser"));
		
			EnterUsername LoginPage = new EnterUsername(driver);
		
			LoginPage.clearuserbox();
			LoginPage.Usernamebox(hMap.get("Username"));
		
			LoginPage.ClearPassword();
			LoginPage.EnterPassword(hMap.get("Password"));
		
			HomePage homepage = LoginPage.clicklogin();
		
		String expectedresult = hMap.get("ExpectedResult");
		
		boolean expectedConvertedResult = false;
		
		if (expectedresult.equalsIgnoreCase("Success")) {
			
			expectedConvertedResult = true;
			
		}else if (expectedresult.equalsIgnoreCase("Failure")){
			 
			expectedConvertedResult = false;
		}
		 boolean actualresult = false;
		
		 	actualresult = homepage.verifyhomepage();
		
		 	Assert.assertEquals(actualresult, expectedConvertedResult);
		
		driver.quit();
	}
	
	@DataProvider
	public Object[][] datasupp()  {
		
		Object[][] data = null;
		
		try {
			excelReader = new MyXLSReader("src\\test\\resource\\TutorialsNinja.xlsx");
			data = DataUtil.getTestData(excelReader,"LoginTest", "Data");
		} catch(Throwable e) {
			
		}
		return data;
   }
	
//	@DataProvider
//	public Object[][] datasupp() {
//		
//		Object[][] data = {{"joshy96@urchtech.com","Joshy1T@"},
//				{"joshy96@urchtech.com","Joshy1T@"},
//				{"joshy96@urchtech.com","Joshy1T@"}};
//		
//		return data;
//	}
}
