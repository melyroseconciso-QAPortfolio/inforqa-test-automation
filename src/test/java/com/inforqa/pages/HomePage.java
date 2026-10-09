package com.inforqa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Page Object for the Infor home page. All "how to find things" lives here, not in the tests. */
public class HomePage extends BasePage {

    private static final By MAIN_HEADING = By.tagName("h1");
    private static final By HEADER = By.tagName("header");
    private static final By FOOTER = By.tagName("footer");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void openHome() {
        open("/");
    }

    public boolean isMainHeadingVisible() {
        return isVisible(MAIN_HEADING);
    }

    public boolean isHeaderVisible() {
        return isVisible(HEADER);
    }

    public boolean isFooterVisible() {
        // The footer is at the bottom of the page, so presence matters more than being on screen.
        return !driver.findElements(FOOTER).isEmpty();
    }
}
