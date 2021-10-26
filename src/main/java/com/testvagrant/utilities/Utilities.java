package com.testvagrant.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
	private static int rowCount = -1;
	private static boolean columnDataMatched;

	public static void captureScreenshot() throws IOException
	{
		screenShotName = "Error_" + getCurrentDataTime(datePattern) + ".jpg";
		screenShotPath = baseDir + screenshotsDir + screenShotName;

		File ssFile = ((TakesScreenshot) Page.driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(ssFile, new File(screenShotPath));
	}

	@DataProvider(name = "TestData")
	public Object[][] getData(Method method)
	{
//		String className = method.getDeclaringClass().getName();
//		String sheetName = className.substring(className.indexOf(".")+1, className.length());
		String sheetName = "Test_Locations";
		int rows = excelPath.getRowCount(sheetName);
		int columns = excelPath.getColumnCount(sheetName);

		Object[][] menuItems = new Object[rows - 1][1];
		Hashtable<String, String> table = null;

		for (int rowNum = 2; rowNum <= rows; rowNum++) {
			table = new Hashtable<String, String>();
			for (int colNum = 0; colNum < columns; colNum++) {
				table.put(excelPath.getCellData(sheetName, colNum, 1),
						excelPath.getCellData(sheetName, colNum, rowNum));
				menuItems[rowNum - 2][0] = table;
			}
		}

//		menuItems[0][0] = "Top Gainers";	FundamentalAnalysis
//		
//		menuItems[1][0] = "Top Losers";		

		return menuItems;
	}

	public static void recordResults(String resultSheet, Hashtable<String, String> result)
	{
		Set<String> keys = result.keySet();
		Iterator <String> keyItr = keys.iterator();
		while(keyItr.hasNext())
		{
			String column = keyItr.next();
			createAbsentColumn(resultSheet, column);
			rowCount = excelPath.getRowCount(resultSheet);
			System.out.println("Row count after adding the column " +column+ " is: " + rowCount);
		}
		keyItr = keys.iterator();
		while(keyItr.hasNext())
		{
			String column = keyItr.next();
			String data = result.get(column);
			String location = result.get(excelPath.getCellData(resultSheet, 0, 1));
			System.out.println("Location from the results sheet: " +location);
			rowCount = getRequiredRow(resultSheet, excelPath.getCellData(resultSheet, 0, 1), rowCount, location);
			
			excelPath.setCellData(resultSheet, column, rowCount, data);
		}
	}

	public static void creatResultSheet(String sheetName)
	{
		if (!(excelPath.isSheetExist(sheetName))) {
//			System.out.println("Sheet exists: " + excelPath.isSheetExist(sheetName));
			excelPath.addSheet(sheetName);
//			System.out.println("Sheet Added: " + excelPath.isSheetExist(sheetName));
		}
	}

	public static void createAbsentColumn(String sheetName, String columnName)
	{
		if (!excelPath.isColumnPresent(sheetName, columnName)) {
			if (excelPath.addColumn(sheetName, columnName))
				System.out.println("Column added: " + columnName);
		}
	}

	public static int getRequiredRow(String sheetName, String columnName, int rowCount, String data)
	{
		int rc = rowCount;
		rowCount = excelPath.getCellRowNum(sheetName, columnName, data);
		System.out.println("rowCount in getRequiredRow 1 : " +rowCount);
		if(rowCount == -1)
			rowCount = rc+1;
		
		System.out.println("rowCount in getRequiredRow 2 : " +rowCount);
		return rowCount;
	}

	public static JsonPath rawToJson(String response)
	{
		JsonPath js = new JsonPath(response);
		return js;
	}

	public static String getCurrentDataTime(String datePattern)
	{
		Date date = new Date();
		SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
		String currentDateTime = dateFormatter.format(date);
		return currentDateTime;
	}

}
