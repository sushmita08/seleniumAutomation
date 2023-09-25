package kohls_urgency_Automation;

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

public class ZCSE_2555_BaseClass {
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentHtmlReporter htmlReporterv1;
	public static ExtentReports extent;
	public static ExtentTest logger;
	String finalDate;

	@BeforeSuite
	public void setUp() {
		 Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("MM_dd_yyyy_HH_mm");
		 finalDate = dateFormat.format(date)+"";

		htmlReporter = new ExtentHtmlReporter("./Reports/kolhsUrgencyReport.html");
		htmlReporterv1 = new ExtentHtmlReporter("./kolhsUrgencyHistoryReports/" +finalDate+ ".html");
		
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.attachReporter(htmlReporterv1);
		
		extent.setSystemInfo("Client", "Kolhs");
		extent.setSystemInfo("UseCase Name", "Urgency");
		extent.setSystemInfo("Environment", "QA02-green");

		htmlReporter.config().setDocumentTitle("AutomationTesting.in Demo Report");
		htmlReporter.config().setReportName("Kolhs Product Urgency Report ");
		htmlReporter.config().setTheme(Theme.DARK);
		
		
		htmlReporterv1.config().setDocumentTitle("AutomationTesting.in Demo Report");
		htmlReporterv1.config().setReportName("Kolhs Product Urgency Report ");
		htmlReporterv1.config().setTheme(Theme.DARK);
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


