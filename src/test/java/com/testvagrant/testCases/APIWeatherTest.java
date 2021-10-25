package com.testvagrant.testCases;

import java.util.HashMap;
import java.util.Hashtable;

import org.testng.annotations.Test;

import com.testvagrant.api.WeatherAPI;
import com.testvagrant.base.API;
import com.testvagrant.utilities.Utilities;

public class APIWeatherTest
{
	@Test
	public void apiWeatherTest()
	{
		WeatherAPI api = new WeatherAPI();
		Hashtable<String,String> queries = new Hashtable<String,String>();
		queries.put("q", "pune");
		queries.put("appid", API.apiKey);
		queries.put("units", "metric");		
		
		String getResponse = api.getWeatherData(queries);
		String currentTemp = Utilities.rawToJson(getResponse).getString("main.temp");
		System.out.println(currentTemp);
	}
}
