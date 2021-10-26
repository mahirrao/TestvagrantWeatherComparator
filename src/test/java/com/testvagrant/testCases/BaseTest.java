package com.testvagrant.testCases;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.testvagrant.base.Page;
import com.testvagrant.base.Project;
import com.testvagrant.utilities.ExcelReader;
import com.testvagrant.utilities.Utilities;

public class BaseTest
{
	public static String resultSheet;

	@BeforeTest
	public void setup()
	{
		Page.initConfiguration();
		if (resultSheet == null)
			resultSheet = "Temperature_" + Utilities.getCurrentDataTime(Project.shortDatePattern);
//		System.out.println(resultSheet);
		Utilities.creatResultSheet(resultSheet);
	}

	@AfterTest
	public void tearDown()
	{
		Page.quitBrowser();
	}

}
