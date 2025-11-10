package practice;

import org.testng.Assert;
import org.testng.annotations.Test;

public class WorkingWithzziRetryAnalyser {
	
	@Test(retryAnalyzer = genericutility.IRetryImplementation.class)
	public void test() {
		Assert.assertEquals("hdfc", "hfdc");
	}

}
