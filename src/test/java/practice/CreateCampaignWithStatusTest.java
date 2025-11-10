package practice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import genericutility.ExcelFileUtility;
import genericutility.PropertyFileUtility;
import genericutility.WebDriverUtility;
import objectrepository.CampaignsPage;
import objectrepository.CreateCampaignPage;
import objectrepository.HomePage;
import objectrepository.LoginPage;

public class CreateCampaignWithStatusTest {

	public static void main(String[] args) throws IOException {

		// Read common data from properties file

		PropertyFileUtility pLib = new PropertyFileUtility();

		String BROWSER = pLib.readDataFromPropertyFile("Browser");

		String URL = pLib.readDataFromPropertyFile("URL");

		String USERNAME = pLib.readDataFromPropertyFile("Username");

		String PASSWORD = pLib.readDataFromPropertyFile("Password");

		// Read test script data from excel

		

		ExcelFileUtility eLib = new ExcelFileUtility();

		String CAMPAIGN_NAME = eLib.readDataFromExcelFile("Campaign", 4, 2);

		String STATUS = eLib.readDataFromExcelFile("Campaign", 4, 3);

		String TARGET_SIZE = eLib.readDataFromExcelFile("Campaign", 4, 4);

		String TOAST_MSG_VERIFICATION = eLib.readDataFromExcelFile("Campaign", 4, 5);

		WebDriverUtility wLib = new WebDriverUtility();

		// Launch the browser

		ChromeOptions settings = new ChromeOptions();

		Map<String, Object> prefs = new HashMap<>();

		prefs.put("profile.password_manager_leak_detection", false);

		settings.setExperimentalOption("prefs", prefs);

		WebDriver driver = null;

		if (BROWSER.equalsIgnoreCase("chrome"))

			driver = new ChromeDriver(settings);

		else if (BROWSER.equalsIgnoreCase("edge"))

			driver = new EdgeDriver();

		else if (BROWSER.equalsIgnoreCase("firefox"))

			driver = new FirefoxDriver();

		else if (BROWSER.equalsIgnoreCase("safari"))

			driver = new SafariDriver();

		driver.manage().window().maximize();

		wLib.implicitWait(driver);

		// Login

		LoginPage loginPage = new LoginPage(driver);

		loginPage.loginToApp(URL, USERNAME, PASSWORD);

		// Create Campaign

		CampaignsPage campaignsPage = new CampaignsPage(driver);

		campaignsPage.getAddCreateCampaignBtn().click();

		CreateCampaignPage createCampaignPage = new CreateCampaignPage(driver);

		createCampaignPage.getCampaignNameTF().sendKeys(CAMPAIGN_NAME);

		createCampaignPage.getCampaignStatusTF().sendKeys(STATUS);

		createCampaignPage.getTargetSizeTF().clear();

		createCampaignPage.getTargetSizeTF().sendKeys(TARGET_SIZE);

		createCampaignPage.getCreateCampaignBtn().click();

		// Verification

		HomePage homePage = new HomePage(driver);

		WebElement toastMsg = homePage.getToastMsg();

		wLib.waitUntilElementToBeVisible(driver, toastMsg);

		if (toastMsg.getText().contains(TOAST_MSG_VERIFICATION))

			System.out.println("Campaign Created");

		else

			System.out.println("Campaign Not Created");

		homePage.getCloseToastMsg().click();

		// Logout

		homePage.logout();

		// Close the browser

		driver.quit();

	}

}