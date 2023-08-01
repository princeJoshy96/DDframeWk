package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EnterUsername {
	
	WebDriver driver;
	
	public EnterUsername (WebDriver driver) {
		this.driver = driver;
		
		PageFactory.initElements(driver, this);
	}
	@FindBy(xpath="//input[@id='username']")
	private WebElement clearuserbox;
	
	@FindBy(xpath="//input[@id='username']")
	private WebElement UsernameBox;
	
	@FindBy(xpath="//input[@id='password']")
	private WebElement ClearPasswordBox;
	
	@FindBy(xpath="//input[@id='password']")
	private WebElement PasswordBox;
	
	@FindBy(xpath="//input[@id='Login']")
	private WebElement LoginBox;
	
	public void clearuserbox() {
		clearuserbox.clear();
	}
	
	public void Usernamebox(String Username) {
		UsernameBox.sendKeys(Username);
	}
	
	public void ClearPassword() {
		ClearPasswordBox.clear();;
	}
	
	public void EnterPassword(String Password) {
		PasswordBox.sendKeys(Password);
	}
	
	public HomePage clicklogin() {
		LoginBox.click();
		
		return new HomePage(driver);
	}
}
