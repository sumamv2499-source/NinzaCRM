package practice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.By;
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
import objectrepository.HomePage;
import objectrepository.LoginPage;

public class CreateCampaignWithExpectedCloseDateTest {

	public static void main(String[] args) throws IOException {

		// Read data from properties file
//		FileInputStream fis = new FileInputStream("src/test/resources/CommonData.properties");
//		Properties prop = new Properties();
//		prop.load(fis);
//		String BROWSER = prop.getProperty("Browser");
//		String URL = prop.getProperty("URL");
//		String USERNAME = prop.getProperty("Username");
//		String PASSWORD = prop.getProperty("Password");
		PropertyFileUtility pLib = new PropertyFileUtility();
		String BROWSER = pLib.readDataFromPropertyFile("Browser");
		String URL = pLib.readDataFromPropertyFile("URL");
		String USERNAME = pLib.readDataFromPropertyFile("Username");
		String PASSWORD = pLib.readDataFromPropertyFile("Password");

		// Read test script data from excel
//		FileInputStream fis1 = new FileInputStream("src/test/resources/Crm/Ninza.xlsx");
//		Workbook wb = WorkbookFactory.create(fis1);
//		String CAMPAIGN_NAME = wb.getSheet("Campaign").getRow(7).getCell(2).getStringCellValue();
//		String TARGET_SIZE = wb.getSheet("Campaign").getRow(7).getCell(3).getStringCellValue();
//		wb.close();
		ExcelFileUtility eLib = new ExcelFileUtility();
		String CAMPAIGN_NAME = eLib.readDataFromExcelFile("Campaign", 7, 2);
		String TARGET_SIZE = eLib.readDataFromExcelFile("Campaign", 7, 3);
		String TOAST_MSG_VERIFICATION = eLib.readDataFromExcelFile("Campaign", 7, 4);

		// Generate date after 30 days
//		Date date = new Date();
//		SimpleDateFormat sim = new SimpleDateFormat("dd-MM-yyyy");
//		sim.format(date);
//		Calendar cal = sim.getCalendar();
//		cal.add(Calendar.DAY_OF_MONTH, 30);
//		String requiredDate = sim.format(cal.getTime());

		JavaUtility ju = new JavaUtility();
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
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		wLib.implicitWait(driver);
		// Login
//		driver.get(URL);
//		driver.findElement(By.id("username")).sendKeys(USERNAME);
//		driver.findElement(By.id("inputPassword")).sendKeys(PASSWORD);
//		driver.findElement(By.xpath("//button[text()='Sign In']")).click();
		LoginPage loginpage = new LoginPage(driver);
		loginpage.loginToApp(URL, USERNAME, PASSWORD);

		// Create Campaign
		CampaignsPage campaignsPage = new CampaignsPage(driver);
		campaignsPage.getAddCreateCampaignBtn().click();
//		driver.findElement(By.xpath("//span[text()='Create Campaign']")).click();
		driver.findElement(By.name("campaignName")).sendKeys("Campaigntest");
		WebElement targetSize = driver.findElement(By.name("targetSize"));
		targetSize.clear();
		targetSize.sendKeys("8");
		driver.findElement(By.name("expectedCloseDate")).sendKeys(ju.getRequiredDate(50));
		driver.findElement(By.xpath("//button[text()='Create Campaign']")).click();

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
