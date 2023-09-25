package kohls_urgency_Automation;

import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

public class ZCSE_2555_urgency_GuestCheck extends ZCSE_2555_BaseClass {
	WebDriver d;
	static LocalStorage localStorage = null;
	Boolean divPresent = false;
	static boolean testresult;
	static boolean result;
	static boolean connectdata;
	static boolean isControl;
	String url0 = "https://www-qa02-green.kohlsecommerce.com/?relversion=123";
	String url1 = "https://www-qa02.kohlsecommerce.com/?relversion=123";
	String url2 = "https://www-qa02-green.kohlsecommerce.com/search.jsp?submit-search=web-regular&search=shirt&kls_sbp=69499132430993837840694830663287407451";// search
																																								// page
																																								// url
	String oneonenineminurl = "https://www-qa02-green.kohlsecommerce.com/product/prd-1170625/null.jsp";
	String fifteenminurl = "https://www-qa02-green.kohlsecommerce.com/product/prd-1334760/null.jsp";
	String popularpic = "https://www-qa02-green.kohlsecommerce.com/product/prd-1997448/null.jsp";
	int len = 10;
	String entryData = null;
	
	@Test(priority = 1)
	public void setup() throws InterruptedException {
		System.out.println("finaldate:"+finalDate);
		// System.setProperty("webdriver.chrome.driver", "E:\\Selenium 64 bit\\Web
		// Driver\\chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", "//Applications//chromedriver");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--window-size=1920,1080");
		options.addArguments("--start-maximized");
		options.addArguments("--headless");
		DesiredCapabilities caps = DesiredCapabilities.chrome();
		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.BROWSER, Level.INFO);
		caps.setCapability("goog:loggingPrefs", logPrefs);
		caps.setCapability(ChromeOptions.CAPABILITY, options);// comment this line for nonheadless
		d = new ChromeDriver(caps);

		
		// d = new ChromeDriver(options);// to run chrome browser in backside
		// d = new ChromeDriver();
		d.get(url0);
		Thread.sleep(2000);
		d.manage().window().maximize();
		Thread.sleep(2000);
		d.get(url1);
		Thread.sleep(2000);
		d.get(url2);
		Thread.sleep(2000);
		logger = extent.createTest("Zineone Setup Check");
		for (int i = 0; i <= len; i++) {
			WebStorage webStorage = (WebStorage) new Augmenter().augment(d);
			localStorage = webStorage.getLocalStorage();
			Thread.sleep(5000);
			if (localStorage.getItem("z1_connectData") != null && localStorage.getItem("z1_connectData").length() > 0) { // z1_connectDirty,z1_connectData
				System.out.println("Local storage variable found");
				connectdata = true;
				logger.log(Status.PASS, "Zineone connectData Found");
				result = true;
				Assert.assertTrue(result);
				break;
			} else if (i == len) {
				connectdata = false;
				logger.log(Status.FAIL, "Zineone connectData not found");
				extent.flush();
				result = false;
				Assert.assertTrue(result);
			} else {
				connectdata = false;
				System.out.println("Local storage variable not found:" + i);
				d.navigate().refresh();
				Thread.sleep(15000);
			}

		}
		extent.flush();
	}

	@Test(priority = 3)
	public void urgency_purchase24hrsGuest() throws InterruptedException {
		boolean purchase24hrs = false;
		logger = extent.createTest("purchase24hrs -Guest Check");
		// d.get(popularpic);
		// Thread.sleep(10000);
		if (connectdata == true) {
			System.out.println("In last 24 hrs -- people purchase ");
			WebElement display_urgency = d.findElement(By.id("z1-pdpurgency"));
			if (display_urgency.isDisplayed()) {
				System.out.println("urgency is displayed:");
				LogEntries logEntries = d.manage().logs().get(LogType.BROWSER);
				for (LogEntry entry : logEntries) {
					entryData = entry.getMessage().toString();

					if (entryData.contains("w>z1pu|b")) {
						System.out.println("w>z1pu: " + entryData);
						purchase24hrs = true;

					}
				}
				if (purchase24hrs) {
					logger.log(Status.PASS, "Received Popular Pick Message");
					extent.flush();
				} else {
					System.out.println("not received Omniture");
					logger.log(Status.FAIL, " Not Received Omniture");
				}

			} else if (isControl == true) {
				logger.log(Status.PASS, " User in Control group");
			} else {
				System.out.println("not received popular pic message");
				logger.log(Status.FAIL, "Use case fail");
			}
		} else {
			logger.log(Status.FAIL, "Connect data not found");
		}
		extent.flush();
	}

	@Test(priority = 4)
	public void urgency_viewsin15minGuest() throws InterruptedException {
		boolean viewsin15min = false;
		logger = extent.createTest("Views in 15 min - Guest Check");
		if (connectdata == true) {
		d.get(fifteenminurl);
		Thread.sleep(35000);
		
			System.out.println("Views in 15 mincheck -- Guest Check ");
			WebElement display_urgency = d.findElement(By.id("z1-pdpurgency"));
			if (display_urgency.isDisplayed()) {
				System.out.println("urgency is displayed:");

				LogEntries logEntries = d.manage().logs().get(LogType.BROWSER);
				for (LogEntry entry : logEntries) {
					entryData = entry.getMessage().toString();

					if (entryData.contains("w>z1pu|c")) {
						System.out.println("w>z1pu: " + entryData);
						viewsin15min = true;

					}
				}
				if (viewsin15min) {
					logger.log(Status.PASS, "Received Views in 15 min Message");
					extent.flush();
				} else

				{
					System.out.println("not received Omniture");
					logger.log(Status.FAIL, " Not Received Omniture");
				}

			} else if (isControl == true) {
				logger.log(Status.PASS, " User in Control group");
			} else {
				System.out.println("not received Views in 15 min message");
				logger.log(Status.FAIL, "Use Case fail");
			}
		} else {
			logger.log(Status.FAIL, "Connect data not found");
		}
		extent.flush();
	}

	@Test(priority = 5)
	public void urgency_viewsin119minGuest() throws InterruptedException {
		boolean viewsin119min = false;
		logger = extent.createTest("Views in 119 min - Guest Check");
		if (connectdata == true) {
		d.get(oneonenineminurl);
		Thread.sleep(35000);
		
			System.out.println("Views in 119 mincheck --guest check");
			WebElement display_urgency = d.findElement(By.id("z1-pdpurgency"));
			if (display_urgency.isDisplayed()) {
				System.out.println("urgency is displayed:");

				LogEntries logEntries = d.manage().logs().get(LogType.BROWSER);
				for (LogEntry entry : logEntries) {
					entryData = entry.getMessage().toString();

					if (entryData.contains("w>z1pu|d")) {
						System.out.println("w>z1pu: " + entryData);
						viewsin119min = true;

					}
				}
				if (viewsin119min) {
					logger.log(Status.PASS, "Received Views in 119 min Message");
					extent.flush();
				} else {
					System.out.println("not received Omniture");
					logger.log(Status.FAIL, " Not Received Omniture");
				}

			} else if (isControl == true) {
				logger.log(Status.PASS, " User in Control group");
			} else {
				System.out.println("Not received Views in 119 min message");
				logger.log(Status.FAIL, " Use Case Fail");
			}
		} else {
			logger.log(Status.FAIL, "Connect data not found");
		}
		extent.flush();
	}

	@Test(priority = 2)

	public void urgency_ControlCheckGuest() throws InterruptedException {
		System.out.println("Checking control grp");
		logger = extent.createTest("Control group- Guest Check");
		if (connectdata == true) {
		d.get(popularpic);
		Thread.sleep(35000);
		System.out.println("Connect data:" + connectdata);
		
		
			WebElement display_urgency = d.findElement(By.id("z1-pdpurgency"));
			System.out.println("urgency is displayed:");
			if (!display_urgency.isDisplayed()) {

				System.out.println("User in control group");
				LogEntries logEntries = d.manage().logs().get(LogType.BROWSER);
				for (LogEntry entry : logEntries) {
					entryData = entry.getMessage().toString();

					if (entryData.contains("w>z1pu|a")) {
						System.out.println("w>z1pu: " + entryData);
						isControl = true;

					}
				}
				if (isControl) {
					logger.log(Status.PASS, "User in Control group");
					extent.flush();
				} else

				{
					System.out.println("not received Omniture");
					logger.log(Status.FAIL, " Use case fail");
				}

			} else {
				isControl = false;
				Thread.sleep(5000);
				logger.log(Status.PASS, "User In Test group");
				extent.flush();
			}
		} else {
			logger.log(Status.FAIL, "Connect data not found");
		}
		extent.flush();
	}
}