package com.testvagrant.base;

import java.util.Properties;

import io.restassured.RestAssured;

public class API extends Project
{
	static private Properties apiProp;
	private static String apiProperties = "api.properties";

	public static String baseURI; 
	public static String parameter; 
	public static String apiKey; 

	public API()
	{
		apiProp = getProperties(baseDir + propertiesDir + apiProperties);
		RestAssured.baseURI = apiProp.getProperty("baseURI");
		parameter = apiProp.getProperty("parameter");
		apiKey = apiProp.getProperty("apiKey");
	}
}
