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
import org.testng.annotations.Test;


import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;


public class ZCSE_2447_GuestTransitionTBI extends ZCSE_2552_BaseClass{
	
	
	WebDriver d;
	static boolean connectData;
	static boolean result;
	static LocalStorage localStorage = null;
	String url = "https://tst5.menswearhouse.com/";
	int len = 5;
	Random random = new Random();
    // generate random number from 0 to 3
    int number = random.nextInt(10000);
    
	
    String fname= "test";
	 String lname="test";
	//System.out.println("Random number:"+number+"@email");
	 String email=("tbitest"+number+"@transition.com");
	 String pwd="Test@123";
	@Test(priority = 1)
	public void TBI_SetupGuest() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "E:\\Selenium 64 bit\\Web Driver\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--window-size=1920,1080");  
		options.addArguments("--start-maximized");
		options.addArguments("--headless");
		d = new ChromeDriver(options);// to run chrome browser in backend
		// d = new ChromeDriver(); // to see working in browser
		d.manage().window().maximize();
		d.get(url);
		Thread.sleep(5000);
		
		 logger = extent.createTest("Zineone Setup Check - Guest transition");
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
				System.out.println("itration:"+i +","+len);
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
		
		logger = extent.createTest("TBI Test Group check -- Guest transition");
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
				}else {
					System.out.println("Failed to get banner");
					logger.log(Status.PASS, "User qualified for control group");
					result = true;
					Assert.assertTrue(result);
					extent.flush();
				}//2nd else condition end
}//1st else condition end
			
			}//if condition end
		else {
		logger.log(Status.FAIL, "Zineone Setup Failed");
		result = false;
		Assert.assertTrue(result);
		extent.flush();
		}
	}//method 2 body
	
	
	
	@Test(priority=3)
	public void createAccout() throws InterruptedException {
		logger = extent.createTest("TBI creating accout Transition");
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
	} 



		

	
	@Test(priority=4)
	public void RepeatedBannerCheck() throws InterruptedException {
		// search page
	 logger = extent.createTest("TBI Repeated Banner Check");
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
		Thread.sleep(10000);
		// catalog page
		d.findElement(By.xpath("//*[@id=\"TopCategoryLink_9\"]")).click();
		Thread.sleep(10000);
		d.findElement(By.xpath("//*[@id=\"z1-rto-div\"]")).isDisplayed();
		System.out.println("Got banner on catalog page");
		logger.log(Status.PASS, "Received Repeated Offer on Catalog Page");
		result = true;
		Assert.assertTrue(result);
		Thread.sleep(3000);
		// product page
		

		d.findElement(By.xpath("//*[@id=\"WC_CatalogEntryDBThumbnailDisplayJSPF_700801894_link_9b\"]")).click();
		Thread.sleep(12000);
		d.findElement(By.xpath("//*[@id=\"z1-rto-div\"]")).isDisplayed();
		System.out.println("Got banner on product page");
		logger.log(Status.PASS, "Received Repeated Offer on Product Page");
		result = true;
		Assert.assertTrue(result);
		Thread.sleep(3000);
		d.findElement(By.xpath("//*[@id=\"ada-qv-curr-size\"]")).click();
		Thread.sleep(1000);
		d.findElement(By.xpath("//*[@id=\"sizes_\"]/div[2]/div[2]/div/div/ul/li[4]/a")).click();
		Thread.sleep(2000);
		d.findElement(By.xpath("//*[@id=\"add-to-cart_700801894\"]")).click();
		Thread.sleep(500);
	
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
		Thread.sleep(5000);
		d.findElement(By.xpath("//*[@id=\"z1-rto-div\"]")).isDisplayed();
		System.out.println("Got banner on home page");
		logger.log(Status.PASS, "Received Repeated Offer on Home Page");
		result = true;
		Assert.assertTrue(result);
		Thread.sleep(3000);
		extent.flush();
	}

	@Test(priority = 5)
	public void TBI_CloseClickCheckGuest() throws InterruptedException {
		
	logger = extent.createTest("TBI Close Click check Transition");

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
			

			d.findElement(By.xpath("//*[@id=\\\"WC_CatalogEntryDBThumbnailDisplayJSPF_700801894_link_9b\\\"]")).click();
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
	
	
	
	}// classbody