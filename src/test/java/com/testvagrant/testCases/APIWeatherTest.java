package com.testvagrant.testCases;

import java.util.HashMap;
import java.util.Hashtable;

import org.testng.annotations.Test;

import com.testvagrant.api.WeatherAPI;
import com.testvagrant.base.API;
import com.testvagrant.utilities.Utilities;

public class APIWeatherTest extends BaseTest
{
	@Test(dataProviderClass = Utilities.class, dataProvider = "TestData")
	public void apiWeatherTest(Hashtable<String,String> data)
	{
		WeatherAPI api = new WeatherAPI();
		Hashtable<String,String> queries = new Hashtable<String,String>();
		String location = data.get("Place") + "," + data.get("State")+ "," + data.get("CountryCode");
		System.out.println(location);
		queries.put("q", location);
		queries.put("appid", API.apiKey);
		queries.put("units", data.get("Unit"));		
		
		String getResponse = api.getWeatherData(queries);
		String currentTemp = Utilities.rawToJson(getResponse).getString("main.temp");
		Hashtable<String,String> result = new Hashtable<String,String>();
		result.put("Location", location);
		result.put("API_CurrentTemperature", currentTemp);
		Utilities.recordResults(resultSheet, result);
	}
}
