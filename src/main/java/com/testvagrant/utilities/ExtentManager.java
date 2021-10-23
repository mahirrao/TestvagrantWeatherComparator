package com.testvagrant.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.testvagrant.base.Page;


public class ExtentManager extends Page
{
	private static ExtentReports extent;
	private static ExtentSparkReporter reporter;
	
	public static ExtentReports getInstance(String filePath)
	{
		if(extent == null)
		{
			reporter = new ExtentSparkReporter(filePath);
			reporter.config().setEncoding("utf-8");
			reporter.config().setDocumentTitle("Practice Automation Report");
			reporter.config().setReportName(" Automation Test Result");
			reporter.config().setTheme(Theme.STANDARD);
			extent = new ExtentReports();
			extent.attachReporter(reporter);
			extent.setSystemInfo("Automation Tester", "Mayuresh Ahirrao");
			extent.setSystemInfo("Organization", "MyOrganization");
			extent.setSystemInfo("Build No.: ", "001");
		}
		return extent;
	}
}
