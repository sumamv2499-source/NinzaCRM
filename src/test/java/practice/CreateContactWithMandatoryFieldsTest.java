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
import genericutility.JavaUtility;
import genericutility.PropertyFileUtility;
import genericutility.WebDriverUtility;
import objectrepository.CampaignsPage;
import objectrepository.ContactsPage;
import objectrepository.CreateCampaignPage;
import objectrepository.CreateContactPage;
import objectrepository.HomePage;
import objectrepository.LoginPage;
import objectrepository.SelectCampaignPage;

public class CreateContactWithMandatoryFieldsTest {

	public static void main(String[] args) throws IOException {

		// Create campaign

		// Read common data from properties file

		PropertyFileUtility pLib = new PropertyFileUtility();

		String BROWSER = pLib.readDataFromPropertyFile("Browser");

		String URL = pLib.readDataFromPropertyFile("URL");

		String USERNAME = pLib.readDataFromPropertyFile("Username");

		String PASSWORD = pLib.readDataFromPropertyFile("Password");

		// Read test script data from excel

		ExcelFileUtility eLib = new ExcelFileUtility();

		String CAMPAIGN_NAME = eLib.readDataFromExcelFile("Contacts", 1, 2);

		String TARGET_SIZE = eLib.readDataFromExcelFile("Contacts", 1, 3);

		String ORGANIZATION = eLib.readDataFromExcelFile("Contacts", 1, 4);

		String TITLE = eLib.readDataFromExcelFile("Contacts", 1, 5);

		String CONTACT_NAME = eLib.readDataFromExcelFile("Contacts", 1, 6);

		String SELECT_CAMPAIGN_PAGE_TITLE = eLib.readDataFromExcelFile("Contacts", 1, 8);

		String CAMPAIGN_DD_VALUE = eLib.readDataFromExcelFile("Contacts", 1, 9);

		String TOAST_MSG_VERIFICATION = eLib.readDataFromExcelFile("Contacts", 1, 10);

		JavaUtility jLib = new JavaUtility();

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

		createCampaignPage.getTargetSizeTF().clear();

		createCampaignPage.getTargetSizeTF().sendKeys(TARGET_SIZE);

		createCampaignPage.getCreateCampaignBtn().click();

		HomePage homePage = new HomePage(driver);

		homePage.getCloseToastMsg().click();

		// Click on contacts

		homePage.getContactsLink().click();

		// click on +create contact

		ContactsPage contactsPage = new ContactsPage(driver);

		contactsPage.getAddCreateContactBtn().click();

		// enter the manadatory fields

		CreateContactPage createContactPage = new CreateContactPage(driver);

		createContactPage.getOrganizationNameTF().sendKeys(ORGANIZATION);

		createContactPage.getTitleTF().sendKeys(TITLE);

		createContactPage.getContactNameTF().sendKeys(CONTACT_NAME);

		createContactPage.getMobileTF().sendKeys("9" + jLib.generateNineDigitNumber());

		createContactPage.getPlusBtn().click();

//				driver.findElement(By.name("organizationName")).sendKeys(ORGANIZATION);

//				driver.findElement(By.name("title")).sendKeys(TITLE);

//				driver.findElement(By.name("contactName")).sendKeys(CONTACT_NAME);

//				driver.findElement(By.name("mobile")).sendKeys("9" + jLib.generateNineDigitNumber());

		//

//				// click on plus button

//				driver.findElement(By.xpath("//*[name()='svg'and @data-icon='plus']")).click();

		// get parentId

		String parentId = driver.getWindowHandle();

		// Switch the driver control to child

//				String parentId = driver.getWindowHandle();

//				Set<String> allIds = driver.getWindowHandles();

//				allIds.remove(parentId);

//				for (String id : allIds) {

//					driver.switchTo().window(id);

//					if (driver.getTitle().contains("Select Campaign"))

//						break;

//				}

//				wLib.switchDriverControlOnTitle(driver, "Select Campaign");

		wLib.switchDriverControlOnTitle(driver, SELECT_CAMPAIGN_PAGE_TITLE);

		// select campaignName from drop down

		SelectCampaignPage selectCampaignPage = new SelectCampaignPage(driver);

		WebElement campaignDD = selectCampaignPage.getCampaignDD();

//				WebElement campaignDD = driver.findElement(By.id("search-criteria"));

//				Select obj = new Select(campaignDD);

//				obj.selectByValue("campaignName");

		wLib.select(campaignDD, CAMPAIGN_DD_VALUE);

		// Enter Camapign name in search text field

		selectCampaignPage.getSearchTF().sendKeys(CAMPAIGN_NAME);

//				driver.findElement(By.id("search-input")).sendKeys(CAMPAIGN_NAME);

		// click on select button

//				WebElement selectBtn = driver.findElement(By.className("select-btn"));

		WebElement selectBtn = selectCampaignPage.getSelectBtn();

		wLib.waitUntilElementToBeVisible(driver, selectBtn);

		selectBtn.click();

		// switch the driver control to parent

		driver.switchTo().window(parentId);

		// click on create contact

		createContactPage.getCreateContactBtn().click();

//				driver.findElement(By.xpath("//button[text()='Create Contact']")).click();

		// Verification

		WebElement toastMsg = homePage.getToastMsg();

		wLib.waitUntilElementToBeVisible(driver, toastMsg);

		if (toastMsg.getText().contains(TOAST_MSG_VERIFICATION))

			System.out.println("Contact Created");

		else

			System.out.println("Contact Not Created");

		homePage.getCloseToastMsg().click();

		// Logout

		homePage.logout();

		// Close the browser

		driver.quit();

	}
}
