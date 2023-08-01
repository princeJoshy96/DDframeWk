package listener;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import util.ExtentReprt;

public class Mylistener implements ITestListener{

	ExtentReports extentReport = ExtentReprt.getExtentReport();
	ExtentTest extentTest;
	
	@Override
	public void onTestStart(ITestResult result) {
		
		extentTest=extentReport.createTest(result.getName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		extentTest.log(Status.PASS, "Test was Successfully completed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		extentTest.log(Status.FAIL, "Test was Unsuccessful");
		extentTest.fail(result.getThrowable());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
	}

	@Override
	public void onStart(ITestContext context) {
	}

	@Override
	public void onFinish(ITestContext context) {
		extentReport.flush();
	}

}
