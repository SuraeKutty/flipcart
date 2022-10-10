package com.flipkart.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.flipkart.utility.ScreenShot;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
public static WebDriver driver;
public static ExtentReports reports;
public ExtentTest log;

@BeforeSuite
public void reportsInitialization() {
	ExtentHtmlReporter extent = new ExtentHtmlReporter(new File(
			System.getProperty("user.dir") + "/Reports/Flipkart_" + ScreenShot.getCurrentDateTime() + ".html"));
	reports = new ExtentReports();
	reports.attachReporter(extent);
}

@BeforeMethod
public void commonProperties() {
	WebDriverManager.chromedriver().setup();
	driver = new ChromeDriver();
	driver.get("https://www.flipkart.com/account/login");
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
}

@AfterMethod()
public void screenShotAttacher(ITestResult result) throws IOException {
	if(result.getStatus()==ITestResult.FAILURE) {
		log.fail("Test case failed", MediaEntityBuilder.createScreenCaptureFromPath(ScreenShot.captureScreenshot(driver, result.getName())).build());
	} else if (result.getStatus()==ITestResult.SUCCESS){
		log.pass("Test case Passed", MediaEntityBuilder.createScreenCaptureFromPath(ScreenShot.captureScreenshot(driver, result.getName())).build());
	}
	
	driver.close();
}

@AfterSuite()
public void TearDown()  {
reports.flush();
}




}
