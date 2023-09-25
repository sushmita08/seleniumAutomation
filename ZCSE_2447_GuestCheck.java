<<<<<<< HEAD:src/test/java/tbi_RTO_Automation/tbi_RTO_Automation/ZCSE_2447_GuestCheck.java
package tbi_RTO_Automation.tbi_RTO_Automation;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.Augmenter;
import org.testng.Assert;
//import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;

public class ZCSE_2447_GuestCheck extends ZCSE_2552_BaseClass {

	WebDriver d;
	static boolean connectData;
	static boolean result;
	static LocalStorage localStorage = null;
	String url = "https://tst5.menswearhouse.com/";
	int len = 5;

	@Test(priority = 1)
	public void TBI_SetupGuest() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver","//Applications//chromedriver");//add your chrome driver path
		//System.setProperty("webdriver.chrome.driver", "E:\\Selenium 64 bit\\Web Driver\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--window-size=1920,1080");
		options.addArguments("--start-maximized");
		options.addArguments("--headless");
		 d = new ChromeDriver(options);// to run chrome browser in backside
		//d = new ChromeDriver(); // to see working in browser
		d.manage().window().maximize();
		d.get(url);
		Thread.sleep(5000);
		// extent.attachReporter(repo);
		logger = extent.createTest("Zineone Setup Check - Guest");
		for (int i = 0; i <= len; i++) {
			WebStorage webStorage = (WebStorage) new Augmenter().augment(d);
			localStorage = webStorage.getLocalStorage();
			Thread.sleep(2000);
			if (localStorage.getItem("z1_connectData") != null && localStorage.getItem("z1_connectData").length() > 0) {
				connectData = true;
				System.out.println("Local storage variable found");
				Thread.sleep(7000);
				logger.log(Status.PASS, "Zineone connectData Found");
				result = true;
				Assert.assertTrue(result);
				break;
			} else if (i == len) {
				connectData = false;
				logger.log(Status.FAIL, "Zineone connectData not found");
				extent.flush();
				result = false;
				Assert.assertTrue(result);
			} else {
				connectData = false;
				System.out.println("Local storage variable not found:" + i);
				d.navigate().refresh();
				Thread.sleep(5000);
			}
		}
		extent.flush();
	}

	@Test(priority = 2)
	public void TBI_TestGroupCheckGuest() throws InterruptedException {
		// extent.attachReporter(repo);
		logger = extent.createTest("TBI Test Group check -- Guest");
		if (connectData == true) {
			for (int i = 0; i <= 3; i++) {
				System.out.println("Home page load:" + i);
				d.navigate().refresh();
				Thread.sleep(12000);
			}
			WebStorage webStorage = (WebStorage) new Augmenter().augment(d);
			localStorage = webStorage.getLocalStorage();
			String z1_campaign = localStorage.getItem("z1_campaign");
			System.out.println("z1_campaign found:" + z1_campaign);
			if (localStorage.getItem("z1_campaign") == null) {
				logger.log(Status.FAIL, "User not eligible for test after 5 clicks");
				result = false;
				Assert.assertTrue(result);
				extent.flush();
			} else {
				// test check
				String[] campaigntst = z1_campaign.split(",");
				String vartst = campaigntst[0];
				System.out.println("vartst:" + vartst);
				Thread.sleep(5000);
				if (!vartst.equals("Control")) {
					d.findElement(By.xpath("//*[@id=\"z1-rto-div\"]")).isDisplayed();
					System.out.println("Got banner on entry page");
					logger.log(Status.PASS, "Received offer on entry page");
					result = true;
					Assert.assertTrue(result);
					extent.flush();
					
					// search page

					System.out.println("Going to serach a product");
					Thread.sleep(2000);
					d.findElement(By.xpath("//*[@id=\"search-toggle\"]")).click();
					d.findElement(By.xpath("//*[@id=\"SimpleSearchForm_SearchTerm\"]")).sendKeys("shoes");
					System.out.println("Im on typing shoes on page");
					d.findElement(By.xpath("//*[@id=\"SimpleSearchForm_SearchTerm\"]")).sendKeys(Keys.ENTER);
					Thread.sleep(12000);
					System.out.println("Im on search page");
					d.findElement(By.xpath("//*[@id=\"z1-rto-div\"]")).isDisplayed();
					logger.log(Status.PASS, "Received Repeated Offer on Search Page");
					result = true;
					Assert.assertTrue(result);
					System.out.println("Got banner on search page");
					Thread.sleep(3000);
					// catalog page
					d.findElement(By.xpath("//*[@id=\"TopCategoryLink_9\"]")).click();
					Thread.sleep(15000);
					d.findElement(By.xpath("//*[@id=\"z1-rto-div\"]")).isDisplayed();
					System.out.println("Got banner on catalog page");
					logger.log(Status.PASS, "Received Repeated Offer on Catalog Page");
					result = true;
					Assert.assertTrue(result);
					Thread.sleep(3000);
					// product page
					JavascriptExecutor js = (JavascriptExecutor) d; // scrolling down to select produtc
					js.executeScript("window.scrollBy(0,3000)", "");

					d.findElement(By.xpath("//*[@id=\"catalogEntry_img700903033\"]")).click();
					Thread.sleep(15000);
					d.findElement(By.xpath("//*[@id=\"z1-rto-div\"]")).isDisplayed();
					System.out.println("Got banner on product page");
					logger.log(Status.PASS, "Received Repeated Offer on Product Page");
					result = true;
					Assert.assertTrue(result);
					Thread.sleep(3000);
					/*d.findElement(By.xpath("//*[@id=\"add-to-cart_700903033\"]")).click();
					Thread.sleep(500);
					d.findElement(By.xpath("//*[@id=\"add-to-cart_700903033\"]")).click();
					Thread.sleep(5000);
					// cart page
					d.findElement(By.xpath("//*[@id=\"header-cart-with-count\"]")).click();
					Thread.sleep(12000);
					d.findElement(By.xpath("//*[@id=\"z1-rto-div\"]")).isDisplayed();
					System.out.println("Got banner on cart page");
					logger.log(Status.PASS, "Received Repeated Offer on Cart Page");
					result = true;
					Assert.assertTrue(result);
					Thread.sleep(3000);*/
					// Home page
					d.findElement(By.xpath("//*[@id=\"main-header\"]/div[7]/section/a")).click();
					Thread.sleep(13000);
					d.findElement(By.xpath("//*[@id=\"z1-rto-div\"]")).isDisplayed();
					System.out.println("Got banner on home page");
					logger.log(Status.PASS, "Received Repeated Offer on Home Page");
					result = true;
					Assert.assertTrue(result);
					Thread.sleep(3000);
					extent.flush();
				} else {
					System.out.println("Failed to get banner");
					logger.log(Status.PASS, "User qualified for control group");
					result = true;
					Assert.assertTrue(result);
					extent.flush();
				}
			}
		} else {
			logger.log(Status.FAIL, "Zineone Setup Failed");
			result = false;
			Assert.assertTrue(result);
			extent.flush();
		}
	}

	@Test(priority = 3)
	public void TBI_ControlGroupCheckGuest() throws InterruptedException {   
		
		// extent.attachReporter(repo);
		logger = extent.createTest("TBI Control Group check - Guest");
		if (connectData == true) {

			// boolean Camp;
			WebStorage webStorage = (WebStorage) new Augmenter().augment(d);
			localStorage = webStorage.getLocalStorage();
			String z1_campaign = localStorage.getItem("z1_campaign");
			System.out.println("z1_campaign found:" + z1_campaign);
			if (localStorage.getItem("z1_campaign") == null) {
				logger.log(Status.FAIL, "User not eligible for control after 5 clicks");
				extent.flush();
			} else {
				String[] campaignctr = z1_campaign.split(",");
				String varctr = campaignctr[0];
				System.out.println("varctr:" + varctr);
				Thread.sleep(5000);
				if (!varctr.equals(null) && varctr.equals("Control")) {
					System.out.println("In control grp");
					logger.log(Status.PASS, "User in control Group");
					result = true;
					Assert.assertTrue(result);
					extent.flush();
				} else {
					logger.log(Status.PASS, "User qualified for test group");
					result = true;
					Assert.assertTrue(result);
					System.out.println("not in control");
					extent.flush();
				}
			}
		} else {
			logger.log(Status.FAIL, "Zineone Setup Failed");
			result = false;
			Assert.assertTrue(result);
			extent.flush();
		}
	}

	@Test(priority = 4)
	public void TBI_CopyClickCheckGuest() throws InterruptedException {
		// extent.attachReporter(repo);
		logger = extent.createTest("TBI Offer Code Copy Check - Guest");
		if (connectData == true) {
			// copying coupen code
			d.findElement(By.xpath("//*[@id=\"btn_addoffer\"]")).click();
			Thread.sleep(1000);
			// catalog page
			d.findElement(By.xpath("//*[@id=\"TopCategoryLink_9\"]")).click();
			Thread.sleep(10000);
			System.out.println("Im on catalog page");
			try {
				WebElement z1_div = d.findElement(By.xpath("//*[@id=\"z1-rto-div\"]"));
				if (z1_div.isDisplayed()) {
					logger.log(Status.FAIL, "Repeated Banner on Catalog Page");
					result = false;
					Assert.assertTrue(result);
					extent.flush();
				}

			} catch (Exception e) {
				System.out.println("Offer not displayed on Catalog page");
				logger.log(Status.PASS, "No Repeated Banner on Catalog Page");
				result = true;
				Assert.assertTrue(result);
				extent.flush();

			}
			/*
			// cart page
			d.findElement(By.xpath("//*[@id=\"header-cart-with-count\"]")).click();
			Thread.sleep(10000);

			try {
				WebElement z1_div = d.findElement(By.xpath("//*[@id=\"z1-rto-div\"]"));
				if (z1_div.isDisplayed()) {
					logger.log(Status.FAIL, "Repeated Offer on Cart Page");
					result = false;
					Assert.assertTrue(result);
					extent.flush();
				}

			} catch (Exception e) {
				System.out.println("Offer not displayed on Cart page");
				logger.log(Status.PASS, "No Repeated Offer on Cart Page");
				result = true;
				Assert.assertTrue(result);
				extent.flush();

			}

			d.findElement(By.xpath("//*[@id=\"WC_PromotionCodeDisplay_links_1\"]")).click();

			Thread.sleep(3000);
			try {
				WebElement z1_coupon = d.findElement(By.xpath("//*[@id=\"appliedPromotionCodes\"]/div/div"));
				if (z1_coupon.isDisplayed()) {
					logger.log(Status.PASS, "Coupon Applied Successfully");
					result = true;
					Assert.assertTrue(result);
					extent.flush();
				}

			} catch (Exception e) {
				System.out.println("Offer not displayed on Cart page");
				logger.log(Status.FAIL, "Coupon Not Applied");
				result = false;
				Assert.assertTrue(result);
				extent.flush();

			}*/

		} else {
			logger.log(Status.FAIL, "Zineone Setup Failed");
			result = false;
			Assert.assertTrue(result);
		}
	}

	@Test(priority = 5)
	public void TBI_CloseClickCheckGuest() throws InterruptedException {
		// extent.attachReporter(repo);
		logger = extent.createTest("TBI Close Click check - Guest");

		if (d instanceof WebStorage) {
			WebStorage webStorage = (WebStorage) d;
			webStorage.getLocalStorage().clear();
		}
		Thread.sleep(500);
		d.manage().deleteAllCookies();
		d.findElement(By.xpath("//*[@id=\"main-header\"]/div[7]/section/a")).click();
		Thread.sleep(5000);
		for (int i = 0; i <= len; i++) {
			WebStorage webStorage = (WebStorage) new Augmenter().augment(d);
			localStorage = webStorage.getLocalStorage();
			Thread.sleep(2000);
			if (localStorage.getItem("z1_connectData") != null && localStorage.getItem("z1_connectData").length() > 0) {
				connectData = true;
				System.out.println("Local storage variable found for close click");
				Thread.sleep(7000);
				logger.log(Status.PASS, "Zineone connectData Found for new user");
				result = true;
				Assert.assertTrue(result);
				break;
			} else if (i == len) {
				connectData = false;
				logger.log(Status.FAIL, "Zineone connectData not found for new user");
				extent.flush();
				result = false;
				Assert.assertTrue(result);
			} else {
				connectData = false;
				System.out.println("Local storage variable not found for new user:" + i);
				d.navigate().refresh();
				Thread.sleep(5000);
			}
		}

		for (int i = 0; i <= 3; i++) {
			System.out.println("Home page load:" + i);
			d.navigate().refresh();
			// d.findElement(By.xpath("//*[@id=\"main-header\"]/div[7]/section/a")).click();
			Thread.sleep(12000);
		}
		WebStorage webStorage = (WebStorage) new Augmenter().augment(d);
		localStorage = webStorage.getLocalStorage();
		String z1_campaign = localStorage.getItem("z1_campaign");
		System.out.println("z1_campaign found:" + z1_campaign);
		if (localStorage.getItem("z1_campaign") == null) {
			logger.log(Status.FAIL, "New user not eligible for test after 5 clicks");
			result = false;
			Assert.assertTrue(result);
			extent.flush();
		} else {
			// test check
			String[] campaigntst = z1_campaign.split(",");
			String vartst = campaigntst[0];
			System.out.println("vartst:" + vartst);
			Thread.sleep(5000);
			if (!vartst.equals("Control")) {
				d.findElement(By.xpath("//*[@id=\"z1-rto-div\"]")).isDisplayed();
				System.out.println("Got banner on entry page for new user");
				logger.log(Status.PASS, "Received offer on entry page for new user");
				result = true;
				Assert.assertTrue(result);
				extent.flush();
			} else {
				System.out.println("Failed to get banner");
				logger.log(Status.PASS, "New user is in control group");
				result = true;
				Assert.assertTrue(result);
				extent.flush();
			}
			// close the offer
			d.findElement(By.xpath("//*[@id=\"z1-rto-div\"]/div/div[1]")).click();
			Thread.sleep(1000);
			// catalog page
			d.findElement(By.xpath("//*[@id=\"TopCategoryLink_9\"]")).click();
			Thread.sleep(10000);
			System.out.println("Im on catalog page");
			try {
				WebElement z1_div = d.findElement(By.xpath("//*[@id=\"z1-rto-div\"]"));
				if (z1_div.isDisplayed()) {
					logger.log(Status.FAIL, "Repeated Offer on Catalog Page");
					result = false;
					Assert.assertTrue(result);
					extent.flush();
				}

			} catch (Exception e) {
				System.out.println("Offer not displayed on Catalog page");
				logger.log(Status.PASS, "No Repeated Offer on Catalog Page");
				result = true;
				Assert.assertTrue(result);
				extent.flush();

			}
			JavascriptExecutor js = (JavascriptExecutor) d; // scrolling down to select produtc
			js.executeScript("window.scrollBy(0,3000)", "");

			d.findElement(By.xpath("//*[@id=\"catalogEntry_img700903033\"]")).click();
			Thread.sleep(10000);
			System.out.println("Im on product page");
			try {
				WebElement z1_div = d.findElement(By.xpath("//*[@id=\"z1-rto-div\"]"));
				if (z1_div.isDisplayed()) {
					logger.log(Status.FAIL, "Repeated Offer on product Page");
					result = false;
					Assert.assertTrue(result);
					extent.flush();
				}

			} catch (Exception e) {
				System.out.println("Offer not displayed on product page");
				logger.log(Status.PASS, "No Repeated Offer on product Page");
				result = true;
				Assert.assertTrue(result);
				extent.flush();

			}
		}
	}

	/*
	 * @AfterClass public void quit() { d.close(); }
	 */

}
=======
package Selenium_withMaven2111.Selenium_withMaven2111;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.Augmenter;
import org.testng.Assert;
//import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class TBIAutomationGuestCheck {

	ExtentHtmlReporter repo = new ExtentHtmlReporter("./extendReport/Repo.html");
	ExtentReports extent = new ExtentReports();
	WebDriver d;
	static boolean connectData;
	static boolean result;
	static LocalStorage localStorage = null;
	String url = "https://tst5.menswearhouse.com/";
	int len = 5;
String height;
	@Test(priority = 1)
	public void TBI_SetupGuest() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "//Applications//chromedriver");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--window-size=1920,1080");
		options.addArguments("--start-maximized");
		options.addArguments("--headless");
		d = new ChromeDriver(options);// to run chrome browser in backend
		//d = new ChromeDriver(); // to see working in browser
		d.manage().window().maximize();
		d.get(url);
		Thread.sleep(5000);
		extent.attachReporter(repo);
		ExtentTest logger = extent.createTest("Zineone Setup Check - Guest");
		for (int i = 0; i <= len; i++) {
			WebStorage webStorage = (WebStorage) new Augmenter().augment(d);
			localStorage = webStorage.getLocalStorage();
			Thread.sleep(2000);
			if (localStorage.getItem("z1_connectData") != null && localStorage.getItem("z1_connectData").length() > 0) { // z1_connectDirty,z1_connectData
				connectData = true;
				System.out.println("Local storage variable found");
				Thread.sleep(7000);
				logger.log(Status.PASS, "Zineone connectData Found");
				result = true;
				Assert.assertTrue(result);
				break;
			} else if (i == len + 1) {
				connectData = false;
				logger.log(Status.FAIL, "Zineone connectData not found");
				result = false;
				Assert.assertTrue(result);
			} else {
				connectData = false;
				System.out.println("Local storage variable not found:" + i);
				d.navigate().refresh();
				Thread.sleep(5000);
			}
		}
	}

	@Test(priority = 2)
	public void TBI_TestGroupCheckGuest() throws InterruptedException {
		extent.attachReporter(repo);
		ExtentTest logger = extent.createTest("TBI Test Group check -- Guest");
		if (connectData == true) {
			for (int i = 0; i <= 3; i++) {
				System.out.println("Home page load:" + i);
				d.navigate().refresh();
				Thread.sleep(12000);
			}
			WebStorage webStorage = (WebStorage) new Augmenter().augment(d);
			localStorage = webStorage.getLocalStorage();
			String z1_campaign = localStorage.getItem("z1_campaign");
			System.out.println("z1_campaign found:" + z1_campaign);
			if (localStorage.getItem("z1_campaign") == null) {
				logger.log(Status.FAIL, "User not eligible for test after 5 clicks");
				result = false;
				Assert.assertTrue(result);
				extent.flush();
			} else {
				// test check
				String[] campaigntst = z1_campaign.split(",");
				String vartst = campaigntst[0];
				System.out.println("vartst:" + vartst);
				Thread.sleep(5000);
				if (!vartst.equals("Control")) {
					d.findElement(By.xpath("//*[@id=\"z1-rto-div\"]")).isDisplayed();
					System.out.println("Got banner on entry page");
					logger.log(Status.PASS, "Received offer on entry page");
					result = true;
					Assert.assertTrue(result);
					extent.flush();
					// search page

					System.out.println("Going to serach a product");
					Thread.sleep(2000);
					d.findElement(By.xpath("//*[@id=\"search-toggle\"]")).click();
					d.findElement(By.xpath("//*[@id=\"SimpleSearchForm_SearchTerm\"]")).sendKeys("shoes");
					System.out.println("Im on typing shoes on page");
					d.findElement(By.xpath("//*[@id=\"SimpleSearchForm_SearchTerm\"]")).sendKeys(Keys.ENTER);
					Thread.sleep(12000);
					System.out.println("Im on search page");
					d.findElement(By.xpath("//*[@id=\"z1-rto-div\"]")).isDisplayed();
					logger.log(Status.PASS, "Received Repeated Offer on Search Page");
					result = true;
					Assert.assertTrue(result);
					System.out.println("Got banner on search page");
					Thread.sleep(3000);
					// catalog page
					d.findElement(By.xpath("//*[@id=\"TopCategoryLink_9\"]")).click();
					Thread.sleep(12000);
					d.findElement(By.xpath("//*[@id=\"z1-rto-div\"]")).isDisplayed();
					System.out.println("Got banner on catalog page");
					logger.log(Status.PASS, "Received Repeated Offer on Catalog Page");
					result = true;
					Assert.assertTrue(result);
					Thread.sleep(3000);
					// product page
					JavascriptExecutor js = (JavascriptExecutor) d; // scrolling down to select produtc
					js.executeScript("window.scrollBy(0,3000)", "");

					d.findElement(By.xpath("//*[@id=\"catalogEntry_img700903033\"]")).click();
					Thread.sleep(12000);
					d.findElement(By.xpath("//*[@id=\"z1-rto-div\"]")).isDisplayed();
					System.out.println("Got banner on product page");
					logger.log(Status.PASS, "Received Repeated Offer on Product Page");
					result = true;
					Assert.assertTrue(result);
					Thread.sleep(3000);
					d.findElement(By.xpath("//*[@id=\"add-to-cart_700903033\"]")).click();
					Thread.sleep(500);
					d.findElement(By.xpath("//*[@id=\"add-to-cart_700903033\"]")).click();
					Thread.sleep(5000);
					// cart page
					d.findElement(By.xpath("//*[@id=\"header-cart-with-count\"]")).click();
					Thread.sleep(12000);
					d.findElement(By.xpath("//*[@id=\"z1-rto-div\"]")).isDisplayed();
					System.out.println("Got banner on cart page");
					logger.log(Status.PASS, "Received Repeated Offer on Cart Page");
					result = true;
					Assert.assertTrue(result);
					Thread.sleep(3000);
					// Home page
					d.findElement(By.xpath("//*[@id=\"main-header\"]/div[7]/section/a")).click();
					Thread.sleep(10000);
					d.findElement(By.xpath("//*[@id=\"z1-rto-div\"]")).isDisplayed();
					System.out.println("Got banner on home page");
					logger.log(Status.PASS, "Received Repeated Offer on Home Page");
					result = true;
					Assert.assertTrue(result);
					Thread.sleep(3000);
					extent.flush();
				} else {
					System.out.println("Failed to get banner");
					logger.log(Status.PASS, "User qualified for control group");
					result = true;
					Assert.assertTrue(result);
					extent.flush();
				}
			}
		} else {
			logger.log(Status.FAIL, "Zineone Setup Failed");
			result = false;
			Assert.assertTrue(result);
			extent.flush();
		}
	}

	@Test(priority = 3)
	public void TBI_ControlGroupCheckGuest() throws InterruptedException {
		extent.attachReporter(repo);
		ExtentTest logger = extent.createTest("TBI Control Group check - Guest");
		if (connectData == true) {

			// boolean Camp;
			WebStorage webStorage = (WebStorage) new Augmenter().augment(d);
			localStorage = webStorage.getLocalStorage();
			String z1_campaign = localStorage.getItem("z1_campaign");
			System.out.println("z1_campaign found:" + z1_campaign);
			if (localStorage.getItem("z1_campaign") == null) {
				logger.log(Status.FAIL, "User not eligible for control after 5 clicks");
				extent.flush();
			} else {
				String[] campaignctr = z1_campaign.split(",");
				String varctr = campaignctr[0];
				System.out.println("varctr:" + varctr);
				Thread.sleep(5000);
				if (!varctr.equals(null) && varctr.equals("Control")) {
					System.out.println("In control grp");
					logger.log(Status.PASS, "User in control Group");
					result = true;
					Assert.assertTrue(result);
					extent.flush();
				} else {
					logger.log(Status.PASS, "User qualified for test group");
					result = true;
					Assert.assertTrue(result);
					System.out.println("not in control");
					extent.flush();
				}
			}
		} else {
			logger.log(Status.FAIL, "Zineone Setup Failed");
			result = false;
			Assert.assertTrue(result);
			extent.flush();
		}
	}

	@Test(priority = 4)
	public void TBI_CopyClickCheckGuest() throws InterruptedException {
		extent.attachReporter(repo);
		ExtentTest logger = extent.createTest("TBI Offer Code Copy Check - Guest");
		if (connectData == true) {
			// copying coupen code
			d.findElement(By.xpath("//*[@id=\"btn_addoffer\"]")).click();
			Thread.sleep(1000);
			// catalog page
			d.findElement(By.xpath("//*[@id=\"TopCategoryLink_9\"]")).click();
			Thread.sleep(10000);
			System.out.println("Im on catalog page");
			try {
				WebElement z1_div = d.findElement(By.xpath("//*[@id=\"z1-rto-div\"]"));
				if (z1_div.isDisplayed()) {
					logger.log(Status.FAIL, "Repeated Banner on Catalog Page");
					result = false;
					Assert.assertTrue(result);
					extent.flush();
				}

			} catch (Exception e) {
				System.out.println("Offer not displayed on Catalog page");
				logger.log(Status.PASS, "No Repeated Banner on Catalog Page");
				result = true;
				Assert.assertTrue(result);
				extent.flush();

			}

			// cart page
			d.findElement(By.xpath("//*[@id=\"header-cart-with-count\"]")).click();
			Thread.sleep(10000);

			try {
				WebElement z1_div = d.findElement(By.xpath("//*[@id=\"z1-rto-div\"]"));
				if (z1_div.isDisplayed()) {
					logger.log(Status.FAIL, "Repeated Offer on Cart Page");
					result = false;
					Assert.assertTrue(result);
					extent.flush();
				}

			} catch (Exception e) {
				System.out.println("Offer not displayed on Cart page");
				logger.log(Status.PASS, "No Repeated Offer on Cart Page");
				result = true;
				Assert.assertTrue(result);
				extent.flush();

			}

			d.findElement(By.xpath("//*[@id=\"WC_PromotionCodeDisplay_links_1\"]")).click();

			Thread.sleep(3000);
			try {
				WebElement z1_coupon = d.findElement(By.xpath("//*[@id=\"appliedPromotionCodes\"]/div/div"));
				if (z1_coupon.isDisplayed()) {
					logger.log(Status.PASS, "Coupon Applied Successfully");
					result = true;
					Assert.assertTrue(result);
					extent.flush();
				}

			} catch (Exception e) {
				System.out.println("Offer not displayed on Cart page");
				logger.log(Status.FAIL, "Coupon Not Applied");
				result = false;
				Assert.assertTrue(result);
				extent.flush();

			}

		} else {
			logger.log(Status.FAIL, "Zineone Setup Failed");
			result = false;
			Assert.assertTrue(result);
		}
	}

	@Test(priority = 5)
	public void TBI_CloseClickCheckGuest() throws InterruptedException {
		extent.attachReporter(repo);
		ExtentTest logger = extent.createTest("TBI Close Click check - Guest");

		if (d instanceof WebStorage) {
			WebStorage webStorage = (WebStorage) d;
			webStorage.getLocalStorage().clear();
		}
		Thread.sleep(500);
		d.manage().deleteAllCookies();
		d.findElement(By.xpath("//*[@id=\"main-header\"]/div[7]/section/a")).click();
		Thread.sleep(5000);
		for (int i = 0; i <= len; i++) {
			WebStorage webStorage = (WebStorage) new Augmenter().augment(d);
			localStorage = webStorage.getLocalStorage();
			Thread.sleep(2000);
			if (localStorage.getItem("z1_connectData") != null && localStorage.getItem("z1_connectData").length() > 0) { // z1_connectDirty,z1_connectData
				connectData = true;
				System.out.println("Local storage variable found for close click");
				Thread.sleep(7000);
				logger.log(Status.PASS, "Zineone connectData Found for new user");
				result = true;
				Assert.assertTrue(result);
				break;
			} else if (i == len + 1) {
				connectData = false;
				logger.log(Status.FAIL, "Zineone connectData not found for new user");
				result = false;
				Assert.assertTrue(result);
			} else {
				connectData = false;
				System.out.println("Local storage variable not found for new user:" + i);
				d.navigate().refresh();
				Thread.sleep(5000);
			}
		}

		for (int i = 0; i <= 3; i++) {
			System.out.println("Home page load:" + i);
			d.navigate().refresh();
			// d.findElement(By.xpath("//*[@id=\"main-header\"]/div[7]/section/a")).click();
			Thread.sleep(12000);
		}
		WebStorage webStorage = (WebStorage) new Augmenter().augment(d);
		localStorage = webStorage.getLocalStorage();
		String z1_campaign = localStorage.getItem("z1_campaign");
		System.out.println("z1_campaign found:" + z1_campaign);
		if (localStorage.getItem("z1_campaign") == null) {
			logger.log(Status.FAIL, "New user not eligible for test after 5 clicks");
			result = false;
			Assert.assertTrue(result);
			extent.flush();
		} else {
			// test check
			String[] campaigntst = z1_campaign.split(",");
			String vartst = campaigntst[0];
			System.out.println("vartst:" + vartst);
			Thread.sleep(5000);
			if (!vartst.equals("Control")) {
				d.findElement(By.xpath("//*[@id=\"z1-rto-div\"]")).isDisplayed();
				System.out.println("Got banner on entry page for new user");
				logger.log(Status.PASS, "Received offer on entry page for new user");
				result = true;
				Assert.assertTrue(result);
				extent.flush();
			} else {
				System.out.println("Failed to get banner");
				logger.log(Status.PASS, "New user is in control group");
				result = true;
				Assert.assertTrue(result);
				extent.flush();
			}
			// close the offer
			d.findElement(By.xpath("//*[@id=\"z1-rto-div\"]/div/div[1]")).click();
			Thread.sleep(1000);
			// catalog page
			d.findElement(By.xpath("//*[@id=\"TopCategoryLink_9\"]")).click();
			Thread.sleep(10000);
			System.out.println("Im on catalog page");
			try {
				WebElement z1_div = d.findElement(By.xpath("//*[@id=\"z1-rto-div\"]"));
				if (z1_div.isDisplayed()) {
					logger.log(Status.FAIL, "Repeated Offer on Catalog Page");
					result = false;
					Assert.assertTrue(result);
					extent.flush();
				}

			} catch (Exception e) {
				System.out.println("Offer not displayed on Catalog page");
				logger.log(Status.PASS, "No Repeated Offer on Catalog Page");
				result = true;
				Assert.assertTrue(result);
				extent.flush();

			}
			JavascriptExecutor js = (JavascriptExecutor) d; // scrolling down to select produtc
			js.executeScript("window.scrollBy(0,3000)", "");

			d.findElement(By.xpath("//*[@id=\"catalogEntry_img700903033\"]")).click();
			Thread.sleep(10000);
			System.out.println("Im on product page");
			try {
				WebElement z1_div = d.findElement(By.xpath("//*[@id=\"z1-rto-div\"]"));
				if (z1_div.isDisplayed()) {
					logger.log(Status.FAIL, "Repeated Offer on product Page");
					result = false;
					Assert.assertTrue(result);
					extent.flush();
				}

			} catch (Exception e) {
				System.out.println("Offer not displayed on product page");
				logger.log(Status.PASS, "No Repeated Offer on product Page");
				result = true;
				Assert.assertTrue(result);
				extent.flush();

			}
		}
	}

	/*
	 * @AfterClass public void quit() { d.close(); }
	 */

}
>>>>>>> 1c3068a27bf94e50be8c2e282c178f98fb1eacab:src/test/java/Selenium_withMaven2111/Selenium_withMaven2111/TBIAutomationGuestCheck.java
