package practice;

import org.apache.hc.core5.util.Asserts;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class WorkingWithAsseration {
//	@Test
//	public void t1() {
//		System.out.println("start");
//		Assert.assertEquals("hdfc", "hfdc");
//		System.out.println("end");
		
//		System.out.println("start");
//		SoftAssert soft= new SoftAssert();
//		soft.assertEquals("hdfc", "hfdc");
//		System.out.println("end");
//		soft.assertAll();
		
//		System.out.println("start");
//		Assert.assertNotEquals("hdfc", "hfdc");
//		System.out.println("end");
//	}
	@Test
	public void t2() {
//		System.out.println("start");
//		Assert.assertTrue("hdfc".equals("hfdc"));
//		System.out.println("end");
		
//		System.out.println("start");
//		Assert.assertFalse("hdfc".equals("hfdc"));
//		System.out.println("end");
		
		String s="java";
		System.out.println("start");
		Assert.assertNotNull(s);
		System.out.println("end");
	}
	

}
