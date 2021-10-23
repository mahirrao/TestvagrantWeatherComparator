package com.testvagrant.testCases;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.testvagrant.base.Page;

public class BaseTest
{
	@BeforeTest
	public void setup()
	{
		Page.initConfiguration();
	}
	
	@AfterTest
	public void tearDown()
	{
		Page.quitBrowser();
	}
}
