package util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReprt {
	
	public static ExtentReports getExtentReport() {
		
		String Extentfile= System.getProperty("user.dir")+"\\Report\\extentreport.html";
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(Extentfile);
		sparkReporter.config().setReportName("Salesforce DDFwork login test result");
		sparkReporter.config().setDocumentTitle("SalesForce DDFwork");
		
		ExtentReports extentRport = new ExtentReports();
		extentRport.attachReporter(sparkReporter);
		extentRport.setSystemInfo("Selenium version", "4.5.0");
		extentRport.setSystemInfo("Operating System", "Windows");
		extentRport.setSystemInfo("Executed By", "Joshua Onyena");
		
		return extentRport;
	}

}
