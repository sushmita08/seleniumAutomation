package tbiProdAutomation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class MWH_BaseClaas {
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentHtmlReporter htmlReporter1;
	public static ExtentReports extent;
	public static ExtentTest logger;
	String finalDate;

	@BeforeSuite
	public void setUp() {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("MM_dd_yyyy_HH_mm");
		 finalDate = dateFormat.format(date)+"";

		htmlReporter = new ExtentHtmlReporter("./Reports/tbiRtoReport.html");
		htmlReporter1 = new ExtentHtmlReporter("./tbiRtoHistoryReports/" +finalDate+ ".html");

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.attachReporter(htmlReporter1);

		extent.setSystemInfo("Client", "Tailor Brand");
		extent.setSystemInfo("UseCase Name", "RTO");
		extent.setSystemInfo("Environment", "Production");

		htmlReporter.config().setDocumentTitle("AutomationTesting.in Demo Report");
		htmlReporter.config().setReportName("TBI RTO Report");
		htmlReporter.config().setTheme(Theme.DARK);

		htmlReporter1.config().setDocumentTitle("AutomationTesting.in Demo Report");
		htmlReporter1.config().setReportName("TBI RTO Report");
		htmlReporter1.config().setTheme(Theme.DARK);
	}

	@AfterMethod
	public void getResult(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(Status.FAIL, MarkupHelper
					.createLabel(result.getName() + " Test case FAILED due to below issues:", ExtentColor.RED));
			logger.fail(result.getThrowable());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			logger.log(Status.PASS,
					MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));
		} else {
			logger.log(Status.SKIP,
					MarkupHelper.createLabel(result.getName() + " Test Case SKIPPED", ExtentColor.ORANGE));
			logger.skip(result.getThrowable());
		}
	}

	@AfterSuite
	public void tearDown() {
		extent.flush();
	}
}
