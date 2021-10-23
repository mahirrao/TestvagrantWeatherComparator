package com.testvagrant.pages.locators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchpageLocators
{
	@FindBy(css = "div.cur-con-weather-card__panel div.temp")
	public WebElement currentTemp;
}
