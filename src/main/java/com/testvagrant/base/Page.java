package com.testvagrant.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.testvagrant.utilities.Utilities;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Page extends Project

{	
	static private Properties webProp;
	private static String webProperties = "config.properties";
	public static WebDriver driver;
	public static WebDriverWait wait;
	public static Actions builder;
	private static String browser;
	
	public static boolean oldURL = false;

	public static void initConfiguration()
	{
		webProp = getProperties(baseDir + propertiesDir + webProperties);
		
		
		// Start the browser based upon the properties file

		browser = webProp.getProperty("browser");
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

		driver.get(webProp.getProperty("testURL"));
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
		testReport.get().log(Status.INFO, "Clicking on " + index + " Element from the list: " + elements);
	}

	public static void type(WebElement element, String value)
	{
		element.sendKeys(value);
		log.info("Typing in an Element : " + element + " entered value as : " + value);
		testReport.get().log(Status.INFO, "Typing in : " + element + " entered value as " + value);
	}

	public static void type(WebElement element, Keys key)
	{
		element.sendKeys(key);
		log.info("Entered the key: " + key + " for Element : " + element);
		testReport.get().log(Status.INFO, "Entered the key: " + key + " for Element : " + element);
	}
	
	public static void pressKey(WebElement element, Keys key)
	{
		builder.moveToElement(element).sendKeys(key).perform();
		log.info("Entered the key: " + key + " for Element : " + element);
	}
}
