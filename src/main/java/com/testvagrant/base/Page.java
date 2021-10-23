package com.testvagrant.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.testvagrant.utilities.ExcelReader;
import com.testvagrant.utilities.ExtentManager;
import com.testvagrant.utilities.Utilities;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Page
{
	// Static Variables

	private static final String excelFileName = "testData.xlsx";
	public static final String baseDir = System.getProperty("user.dir");
	public static final String propertiesDir = "\\src\\test\\resources\\com\\testvagrant\\properties\\";
	public static final String reportsDir = "\\reports";
	public static final String screenshotsDir = "\\reports\\screenshots";
	
	public static final String excelDir = "\\src\\test\\resources\\com\\testvagrant\\excel\\";

	public static WebDriver driver;
	public static WebDriverWait wait;
	public static Actions builder;

	public static String browser;

	public static Logger log = Logger.getLogger("mayureshLogger");

	public static FileInputStream fis;
	static public Properties config = new Properties();
	public static ExcelReader excelPath = new ExcelReader(baseDir + excelDir + excelFileName);
	public static String datePattern = "dd-MMM-yyyy_HH-mm-ss-SSS_z";
	private static String reportName = "ExtentReport.html";// "Extent_" + getCurrentDataTime() + ".html";
	public static ExtentReports extentReport = ExtentManager.getInstance(baseDir + reportsDir + reportName);
	public static ThreadLocal<ExtentTest> testReport = new ThreadLocal<ExtentTest>();
	public static ExtentTest test;

	public static boolean oldURL = false;

	public static void initConfiguration()
	{
		try {
			fis = new FileInputStream(baseDir + propertiesDir + "config.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			config.load(fis);
			log.debug("Config File Loaded");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// Start the browser based upon the properties file

		browser = config.getProperty("browser");
		switch (browser) {
		case "Firefox":
			launchFirefoxBrowser();
			break;

		case "Chrome":
			launchChromeBrowser();
			break;

		case "IE":
			launchIEbrowser();
			break;

		default:
			launchChromeBrowser();
			break;
		}

		driver.get(config.getProperty("testURL"));
		driver.manage().window().maximize();
		builder = new Actions(driver);
	}

	private static void launchFirefoxBrowser()
	{
		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();
		log.info("Firefox browser launched");
	}

	private static void launchIEbrowser()
	{
		WebDriverManager.iedriver().setup();
		driver = new InternetExplorerDriver();
		log.info("IE browser launched");
	}

	private static void launchChromeBrowser()
	{
		WebDriverManager.chromedriver().setup();
		/*
		 * Removes the unwanted pop-ups at the start of the test
		 */
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_setting_values.notifications", 2);
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);
		options.addArguments("--disable-extensions");
		options.addArguments("--disable-infobars");
		driver = new ChromeDriver(options);
		log.info("Chrome browser launched");
	}

	public static void quitBrowser()
	{
		if (driver != null) {
			driver.quit();
		}
		log.info("Test Execution completed!!!");
	}

	// Methods Library

	public static String getCurrentDataTime()
	{
		Date date = new Date();
		SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
		String currentDateTime = dateFormatter.format(date);
		return currentDateTime;
	}

	public void verifyEquals(String pageTitle, String expectedTitle) throws IOException
	{
		try {
			Assert.assertEquals(pageTitle, expectedTitle);
		} catch (Throwable t) {
			Utilities.captureScreenshot();
			
//				Extent Reports Settings
			testReport.get().log(Status.FAIL, "Verification failed with exception: " + "");
			testReport.get().fail("<b>" + "<font color=" + "red>" + "Screenshot of failure" + "</font>" + "</b>",
					MediaEntityBuilder.createScreenCaptureFromPath(Utilities.screenShotName).build());

		}
	}

	/*
	 * Click method for individual element
	 */
	public static void click(WebElement element)
	{
		element.click();
		log.info("Clicking on an Element : " + element);
		testReport.get().log(Status.INFO, "Clicking on : " + element);
	}

	/*
	 * Click method for element in an array list of elements
	 */
	public static void click(List<WebElement> elements, int index)
	{
		elements.get(index).click();
		log.info("Clicking on " + index + " Element from the list: " + elements);
//		testReport.get().log(Status.INFO, "Clicking on " + index + " Element from the list: " + elements);
	}

	public static void type(WebElement element, String value)
	{
		element.sendKeys(value);
		log.info("Typing in an Element : " + element + " entered value as : " + value);
//		testReport.get().log(Status.INFO, "Typing in : " + element + " entered value as " + value);
	}

	public static void type(WebElement element, Keys key)
	{
		element.sendKeys(key);
		log.info("Entered the key: " + key + " for Element : " + element);
//		testReport.get().log(Status.INFO, "Entered the key: " + key + " for Element : " + element);
	}
	
	public static void pressKey(WebElement element, Keys key)
	{
		builder.moveToElement(element).sendKeys(key).perform();
		log.info("Entered the key: " + key + " for Element : " + element);
	}
}
