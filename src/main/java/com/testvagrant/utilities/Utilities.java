package com.testvagrant.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import com.testvagrant.base.Page;
import com.testvagrant.base.Project;

import io.restassured.path.json.JsonPath;



public class Utilities extends Project
{
	public static String screenShotName;
	public static String screenShotPath;
	
	public static void captureScreenshot() throws IOException
	{
		screenShotName = "Error_" + getCurrentDataTime() + ".jpg";
		screenShotPath = baseDir + reportsDir + screenShotName;
		
		File ssFile = ((TakesScreenshot)Page.driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(ssFile, new File(screenShotPath));
	}
	
	@DataProvider(name="TestData")
	public Object[][] getData(Method method)
	{
//		String className = method.getDeclaringClass().getName();
//		String sheetName = className.substring(className.indexOf(".")+1, className.length());
		String sheetName = method.getName();
		System.out.println(sheetName);
		int rows = excelPath.getRowCount(sheetName);
		int columns = excelPath.getColumnCount(sheetName);
		
		Object[][] menuItems = new Object[rows-1][1];
		Hashtable<String, String> table = null;
		
		for(int rowNum=2; rowNum<=rows; rowNum++)
		{
			table = new Hashtable<String, String>();
			for(int colNum=0; colNum<columns; colNum++)
			{
				table.put(excelPath.getCellData(sheetName, colNum, 1), excelPath.getCellData(sheetName, colNum, rowNum));
				menuItems[rowNum-2][0] = table;
			}
		}

//		menuItems[0][0] = "Top Gainers";	FundamentalAnalysis
//		
//		menuItems[1][0] = "Top Losers";		
		
		return menuItems;		
	}
	
	public static JsonPath rawToJson(String response)
	{
		JsonPath js = new JsonPath(response);
		return js;
	}
	
	public static String getCurrentDataTime()
	{
		Date date = new Date();
		SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
		String currentDateTime = dateFormatter.format(date);
		return currentDateTime;
	}

}
