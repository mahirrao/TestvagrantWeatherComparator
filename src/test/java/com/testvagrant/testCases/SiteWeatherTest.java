package com.testvagrant.testCases;

import java.util.Hashtable;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.testvagrant.base.Page;
import com.testvagrant.pages.actions.HomePage;
import com.testvagrant.utilities.Utilities;

public class SiteWeatherTest extends BaseTest
{
	@Test (dataProviderClass = Utilities.class, dataProvider = "TestData")
	public void siteWeatherTest(Hashtable<String,String> data)
	{
		String location = data.get("Place") + "," + data.get("State")+ "," + data.get("CountryCode");
		HomePage home = new HomePage();
		String currentTemp = home.searchWeather(location).getCurrentTemperature();
		System.out.println("Current Temperature at " +location+ " is: " +currentTemp );
		Page.log.info("Current Temperature at " +location+ " is: " +currentTemp );
		Hashtable<String,String> result = new Hashtable<String,String>();
		result.put("Location", location);
		result.put("UI_CurrentTemperature", currentTemp);
		Utilities.recordResults(resultSheet, result);
	}
	
}

