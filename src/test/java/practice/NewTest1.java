package practice;

import org.testng.Reporter;
import org.testng.annotations.Test;

public class NewTest1 {

    @Test
    public void productCreation() {
        Reporter.log("productCreation", true);
    }

    @Test(dependsOnMethods = {"productCreation", "updateProduct"})
    public void deleteProduct() throws InterruptedException {
        System.out.println("deleteProduct");
    }

    @Test(dependsOnMethods = "productCreation")
    public void updateProduct() {
        Reporter.log("updateProduct", true);
    }
}
