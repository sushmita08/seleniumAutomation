package tbi_RTO_Automation.tbi_RTO_Automation;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.testng.Assert;
//import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;

public class ZCSE_2447_LoggedinCheck extends ZCSE_2552_BaseClass{
 
	static LocalStorage localStorage = null;
	WebDriver d;
	String url = "https://tst5.menswearhouse.com/";

	Random random = new Random();
	// generate random number from 0 to 3
	int number = random.nextInt(100000);

	String fname = "test";
	String lname = "tbi";
	String email = "tbilogin" + number + "@tbi.com";
	String pwd = "Test@123";
	static boolean connectData;
	static boolean result;
	int len = 5;

	@Test(priority = 1)
	public void userSignedIn() throws InterruptedException {

		System.setProperty("webdriver.chrome.driver","//Applications//chromedriver");//add your chrome driver path
		//System.setProperty("webdriver.chrome.driver", "E:\\Selenium 64 bit\\Web Driver\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--window-size=1920,1080");
		options.addArguments("--start-maximized");
		options.addArguments("--headless");
		d = new ChromeDriver(options);// to run chrome browser in backside
		//d = new ChromeDriver();
		d.manage().window().maximize();
		d.get(url);
		Thread.sleep(4000); 

		logger = extent.createTest("Zineone Setup and User Login Check");

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
			extent.flush();
		}
		System.out.println("setup complete");
		if (connectData == true) {

			Actions a = new Actions(d);

			d.findElement(By.xpath("//*[@id=\"header-account-section\"]/div[1]/span")).click(); // click on account
			Thread.sleep(1000);
			System.out.println("clicked on account");

			d.findElement(By.xpath("//*[@id=\"sign-in-link-header\"]")).click(); // click on signin/register
			Thread.sleep(2000);
			System.out.println("clicked on signin/register");

			d.findElement(By.xpath("//*[@id=\"create-new-account\"]")).click(); // click on create new acc
			Thread.sleep(2000);
			System.out.println("clicked on create account");

			WebElement fdiv = d.findElement(By.xpath("//*[@id=\"firstName\"]"));
			fdiv.sendKeys(fname);
			Thread.sleep(2000);
			System.out.println("typing fname");

			WebElement ldiv = d.findElement(By.xpath("//*[@id=\"lastName\"]"));
			ldiv.sendKeys(lname);
			Thread.sleep(2000);
			System.out.println("typing last name");

			WebElement ediv = d.findElement(By.xpath("//*[@id=\"email\"]"));
			ediv.sendKeys(email);
			Thread.sleep(2000);
			System.out.println("typing email ");

			WebElement pdiv = d.findElement(By.xpath("//*[@id=\"logonPassword\"]"));
			pdiv.sendKeys(pwd);
			Thread.sleep(2000);
			System.out.println("typing password");

			WebElement prediv = d.findElement(By.xpath("//*[@id=\"confirmPassword\"]"));
			prediv.sendKeys(pwd);
			Thread.sleep(2000);
			System.out.println("retyping password ");

			JavascriptExecutor js = (JavascriptExecutor) d; // scrolling down to create acc button
			js.executeScript("window.scrollBy(0,3000)", "");
			System.out.println("scrolling to click button");

			WebElement button = d.findElement(By.xpath("//*[@id=\"submitRegister\"]"));
			button.click();
			Thread.sleep(5000);
			System.out.println("cliked on create acc");

			WebElement dropacc = d.findElement(By.xpath("//*[@id=\"header-user-greeting\"]"));
			a.moveToElement(dropacc).perform();
			Thread.sleep(1000);
			System.out.println("clicking on acc tab again to log out");

			WebElement logout = d.findElement(By.xpath("//*[@id=\"acct-dd\"]/ul/li[7]/button"));
			logout.click();
			Thread.sleep(5000);
			System.out.println("clicked on logout");

			WebElement login = d.findElement(By.xpath("//*[@id=\"header-account-section\"]/div[1]/span"));
			login.click();
			Thread.sleep(1000);

			WebElement signin = d.findElement(By.xpath("//*[@id=\"sign-in-link-header\"]"));
			signin.click();
			Thread.sleep(1000);

			WebElement sname = d.findElement(By.xpath("//*[@id=\"logonID\"]"));
			sname.sendKeys(email);
			Thread.sleep(2000);

			WebElement pass = d.findElement(By.xpath("//*[@id=\"password\"]"));
			pass.sendKeys(pwd);
			Thread.sleep(2000);

			WebElement button1 = d.findElement(By.xpath("//*[@id=\"sign-in-submit\"]"));
			button1.click();
			Thread.sleep(5000);
			System.out.println("cliked on login button");
			System.out.println("Login successfully");

			String heading = d.findElement(By.xpath("//*[@id=\"header-account-section\"]/div[2]")).getText();

			System.out.println("Login successfully" + heading);
			if (!heading.equals("Accounts")) {
				logger.log(Status.PASS, "Login successfull");
				result = true;
				Assert.assertTrue(result);
			}
		} else {
			logger.log(Status.FAIL, "Zineone Setup Failed");
			result = false;
			Assert.assertTrue(result);
			extent.flush();
		}
	}

	@Test(priority = 2)
	public void TBI_TestGroupCheckLoggedin() throws InterruptedException {
		// extent.attachReporter(repo);
		logger = extent.createTest("TBI Test Group check - Logged In");
		if (connectData == true) {
			// Home page
			d.findElement(By.xpath("//*[@id=\"main-header\"]/div[7]/section/a")).click();
			Thread.sleep(10000);
			for (int i = 0; i <= 1; i++) {
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
					Thread.sleep(15000);
					d.findElement(By.xpath("//*[@id=\"z1-rto-div\"]")).isDisplayed();
					System.out.println("Got banner on product page");
					logger.log(Status.PASS, "Received Repeated Offer on Product Page");
					result = true;
					Assert.assertTrue(result);
					//Thread.sleep(3000);
					/*
					d.findElement(By.xpath("//*[@id=\"add-to-cart_700903033\"]")).click();
					Thread.sleep(500);
					d.findElement(By.xpath("//*[@id=\"add-to-cart_700903033\"]")).click();
					Thread.sleep(1000);
					// cart page
					d.findElement(By.xpath("//*[@id=\"mini-cart-section\"]/div[4]/div[2]/a[1]")).click();
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
	public void TBI_ControlGroupCheckLoggedin() throws InterruptedException {
		// extent.attachReporter(repo);
		logger = extent.createTest("TBI Control Group check - Logged In");
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
	public void TBI_CopyClickCheckLoggedin() throws InterruptedException {
		// extent.attachReporter(repo);
		logger = extent.createTest("TBI Offer Code Copy Check - Logged In");
		if (connectData == true) {
			Actions a = new Actions(d);
			// copying coupen code
			d.findElement(By.xpath("//*[@id=\"btn_addoffer\"]")).click();
			Thread.sleep(1000);
			// catalog page
			d.findElement(By.xpath("//*[@id=\"TopCategoryLink_9\"]")).click();
			Thread.sleep(13000);
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
			System.out.println("Way to cart page");
			WebElement checkout = d.findElement(By.xpath("//*[@id=\"header-cart-with-count\"]"));
			a.moveToElement(checkout).perform();
			Thread.sleep(1000);

			WebElement viewcart = d.findElement(By.xpath("//*[@id=\"mini-cart-section\"]/div[4]/div[2]/a[1]"));
			viewcart.click();
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
			// Apply click
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

	/*
	 * @AfterClass public void quit() { d.close(); }
	 */

}
