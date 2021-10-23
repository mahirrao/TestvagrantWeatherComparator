package com.testvagrant.testCases;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.testvagrant.base.Page;
import com.testvagrant.pages.actions.HomePage;

public class SiteWeatherTest extends BaseTest
{
	@Test
	public void siteWeatherTest()
	{
		String location = "Pune Maharashtra IN";
		HomePage home = new HomePage();
		String currentTemp = home.searchWeather(location).getCurrentTemperature();
		System.out.println("Current Temperature at " +location+ " is: " +currentTemp );
		Page.log.info("Current Temperature at " +location+ " is: " +currentTemp );
	}
	
}

