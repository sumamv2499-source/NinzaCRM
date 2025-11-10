package genericutility;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;

import objectrepository.HomePage;
import objectrepository.LoginPage;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class BaseClass {
	public WebDriver driver = null;
	public PropertyFileUtility pLib = new PropertyFileUtility();
	public ExcelFileUtility eLib=new ExcelFileUtility();
	public JavaUtility jLib=new JavaUtility();
	public WebDriverUtility wLib=new WebDriverUtility();
	public static WebDriver sdriver= null;

	@BeforeSuite  (groups = {"smoke","Regression"})
	public void beforeSuite() {
		System.out.println("Establish the database connection");
	}

	@BeforeTest  (groups = {"smoke","Regression"})
	public void beforeTest() {
		System.out.println("Pre-conditions for parallel executions");
	}

	@BeforeClass  (groups = {"smoke","Regression"})
	public void beforeClass() throws IOException {
		
		System.out.println("Launch the browser");
		String BROWSER = pLib.readDataFromPropertyFile("Browser");
		
		ChromeOptions settings = new ChromeOptions();
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("profile.password_manager_leak_detection", false);
		settings.setExperimentalOption("prefs", prefs);

		if (BROWSER.equalsIgnoreCase("Edge"))
			driver = new EdgeDriver();
		else if (BROWSER.equalsIgnoreCase("Chrome"))
			driver = new ChromeDriver(settings);
		else if (BROWSER.equalsIgnoreCase("Firefox"))
			driver = new FirefoxDriver();
		else if (BROWSER.equalsIgnoreCase("Safari"))
			driver = new SafariDriver();
		
		sdriver=driver;

		driver.manage().window().maximize();
		wLib.implicitWait(driver);
	}

	@BeforeMethod  (groups = {"smoke","Regression"})
	public void beforeMethod() throws IOException {
		System.out.println("Login");
		String URL = pLib.readDataFromPropertyFile("URL");
		String USERNAME = pLib.readDataFromPropertyFile("Username");
		String PASSWORD = pLib.readDataFromPropertyFile("Password");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginToApp(URL, USERNAME, PASSWORD);

	}

	@AfterMethod (groups = {"smoke","Regression"})
	public void afterMethod() {
		System.out.println("Logout");
		HomePage homePage=new HomePage(driver);
		homePage.logout();
	}

	@AfterClass  (groups = {"smoke","Regression"})
	public void afterClass() {
		System.out.println("Close the browser");
		driver.quit();
	}

	@AfterTest  (groups = {"smoke","Regression"})
	public void afterTest() {
		System.out.println("Post-Conditions for parallel executions");
	}

	@AfterSuite  (groups = {"smoke","Regression"})
	public void afterSuite() {
		System.out.println("Close the database connection");
	}
}