package genericutility;

import java.io.File;
import java.io.IOException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenerImplementation implements ITestListener,ISuiteListener{

	@Override
	public void onStart(ISuite suite) {
		System.out.println("Report Configuration");
	}

	@Override
	public void onFinish(ISuite suite) {
		System.out.println("Report Backup");
	}

	@Override
	public void onTestStart(ITestResult result) {
		String testCaseName = result.getMethod().getMethodName();
		System.out.println(testCaseName+" Exceution started");	
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String testCaseName = result.getMethod().getMethodName();
		System.out.println(testCaseName+" Exceution success");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String testCaseName = result.getMethod().getMethodName();
		System.out.println(testCaseName+" Exceution failed");
		JavaUtility jLib=new JavaUtility();
		TakesScreenshot ts=(TakesScreenshot)BaseClass.sdriver;
		File src=ts.getScreenshotAs(OutputType.FILE);
		File dest=new File("./Errorshorts/"+testCaseName+jLib.getCurrentDateAndTime()+".png");
		try {
			org.openqa.selenium.io.FileHandler.copy(src, dest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String testCaseName = result.getMethod().getMethodName();
		System.out.println(testCaseName+" Exceution skipped");
	}
	

}
