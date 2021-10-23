package com.testvagrant.pages.actions;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.testvagrant.base.Page;
import com.testvagrant.pages.locators.HomePageLocators;


public class HomePage extends Page
{
	public HomePageLocators home;
	public HomePage()
	{
		this.home = new HomePageLocators();
		AjaxElementLocatorFactory ajaxFactory = new AjaxElementLocatorFactory(driver, 10);
		PageFactory.initElements(ajaxFactory, this.home);
	}
	
	public SearchPage searchWeather(String place)
	{
		type(home.searchLocation, place);
		pressKey(home.searchLocation, Keys.ENTER);
		return new SearchPage();
	}
		
}
