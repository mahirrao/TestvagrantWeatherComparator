package com.testvagrant.api;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Hashtable;

import com.testvagrant.base.API;

import io.restassured.RestAssured;

public class WeatherAPI extends API
{
	public String getWeatherData(Hashtable<String, String> weatherQueries)
	{
		String response = given()
				.queryParams(weatherQueries)
				.when().get(parameter)
				.then().extract().asString();
		return response;		
	}
}
