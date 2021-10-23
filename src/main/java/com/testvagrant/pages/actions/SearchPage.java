package com.testvagrant.pages.actions;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.testvagrant.base.Page;
import com.testvagrant.pages.locators.SearchpageLocators;

public class SearchPage extends Page
{

	public SearchpageLocators search;
	public SearchPage()
	{
		this.search = new SearchpageLocators();
		AjaxElementLocatorFactory ajaxFactory = new AjaxElementLocatorFactory(driver, 10);
		PageFactory.initElements(ajaxFactory, this.search);
	}
	
	public String getCurrentTemperature()
	{
		String currentTemp = search.currentTemp.getText();
		return currentTemp;
	}
}
