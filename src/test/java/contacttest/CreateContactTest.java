package contacttest;

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
import org.testng.annotations.Test;

import genericutility.BaseClass;
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

public class CreateContactTest extends BaseClass {
	@Test
	public void createContactWithMandatoryFieldsTest() throws IOException
	{
		String CAMPAIGN_NAME = eLib.readDataFromExcelFile("Contacts", 1, 2);

		String TARGET_SIZE = eLib.readDataFromExcelFile("Contacts", 1, 3);

		String ORGANIZATION = eLib.readDataFromExcelFile("Contacts", 1, 4);

		String TITLE = eLib.readDataFromExcelFile("Contacts", 1, 5);

		String CONTACT_NAME = eLib.readDataFromExcelFile("Contacts", 1, 6);

		String SELECT_CAMPAIGN_PAGE_TITLE = eLib.readDataFromExcelFile("Contacts", 1, 8);

		String CAMPAIGN_DD_VALUE = eLib.readDataFromExcelFile("Contacts", 1, 9);

		String TOAST_MSG_VERIFICATION = eLib.readDataFromExcelFile("Contacts", 1, 10);
	
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

		String parentId = driver.getWindowHandle();

		wLib.switchDriverControlOnTitle(driver, SELECT_CAMPAIGN_PAGE_TITLE);

		// select campaignName from drop down

		SelectCampaignPage selectCampaignPage = new SelectCampaignPage(driver);

		WebElement campaignDD = selectCampaignPage.getCampaignDD();

		wLib.select(campaignDD, CAMPAIGN_DD_VALUE);

		// Enter Camapign name in search text field

		selectCampaignPage.getSearchTF().sendKeys(CAMPAIGN_NAME);

		// click on select button
		WebElement selectBtn = selectCampaignPage.getSelectBtn();

		wLib.waitUntilElementToBeVisible(driver, selectBtn);

		selectBtn.click();

		// switch the driver control to parent

		driver.switchTo().window(parentId);

		// click on create contact

		createContactPage.getCreateContactBtn().click();

		// Verification

		WebElement toastMsg = homePage.getToastMsg();

		wLib.waitUntilElementToBeVisible(driver, toastMsg);

		if (toastMsg.getText().contains(TOAST_MSG_VERIFICATION))

			System.out.println("Contact Created");

		else

			System.out.println("Contact Not Created");

		homePage.getCloseToastMsg().click();
		

	}
	}

