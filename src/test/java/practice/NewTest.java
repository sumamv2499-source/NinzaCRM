package practice;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class NewTest {
	 @Test(priority = -200, invocationCount = 0)
	    public void productCreation() {
	        Reporter.log("productCreation", true);
	    }

	    @Test(priority = -200, invocationCount = 5, threadPoolSize = 3)
	    public void deleteproduct() throws InterruptedException {
	    	Reporter.log("productCreation", true);
	        WebDriver driver = new ChromeDriver();
	        Thread.sleep(2000);
	        driver.close();
	    }

}
