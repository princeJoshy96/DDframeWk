package basepackage;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class BaseClass {
	public Properties prop;
	
	public WebDriver Openbrowser(String browsername){
		
		prop = new Properties();
		File propfile = new File("src\\test\\resource\\Data.prop");
		try {
		FileInputStream fis = new FileInputStream(propfile);
			prop.load(fis);
		}catch(Throwable e) {
			e.printStackTrace();
		}
		
		WebDriver driver = null;
		
		if (browsername.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		
		}else if (browsername.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		
		}else if (browsername.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		  driver.manage().window().maximize();
		  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		  
		  driver.get(prop.getProperty("Url"));
			
		  return driver;
	}
}
