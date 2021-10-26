package com.testvagrant.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.testvagrant.utilities.ExcelReader;
import com.testvagrant.utilities.ExtentManager;
import com.testvagrant.utilities.Utilities;

public class Project
{
	// Static Variables

	private static final String excelFileName = "testData.xlsx";
	public static final String baseDir = System.getProperty("user.dir");
	public static final String propertiesDir = "\\src\\test\\resources\\com\\testvagrant\\properties\\";
	public static final String reportsDir = "\\reports\\";
	public static final String screenshotsDir = "\\reports\\screenshots\\";
	public static final String excelDir = "\\src\\test\\resources\\com\\testvagrant\\excel\\";
	
	public static FileInputStream fis;
	static public Properties prop = new Properties();
	public static ExcelReader excelPath = new ExcelReader(baseDir + excelDir + excelFileName);
	public static String datePattern = "dd-MMM-yyyy_HH-mm-ss-SSS_z";
	public static String shortDatePattern = "dd-MMM_HH-mm-ss";
	public static Logger log = Logger.getLogger("mayureshLogger");
	private static String reportName = "Extent_" + Utilities.getCurrentDataTime(datePattern) + ".html";
	public static ExtentReports extentReport = ExtentManager.getInstance(baseDir + reportsDir + reportName);
	public static ThreadLocal<ExtentTest> testReport = new ThreadLocal<ExtentTest>();
	public static ExtentTest test;
	
	public static Properties getProperties(String propertiesFile)
	{
		try {
			fis = new FileInputStream(propertiesFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			prop.load(fis);
			log.debug("Config File Loaded");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;
	}


}
