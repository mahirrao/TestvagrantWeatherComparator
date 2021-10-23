package com.testvagrant.pages.locators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePageLocators
{
	@FindBy(css = "input.search-input")
	public WebElement searchLocation;
}
