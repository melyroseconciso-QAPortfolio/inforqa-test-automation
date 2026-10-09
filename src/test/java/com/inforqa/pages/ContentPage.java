package com.inforqa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** Page Object for any ordinary content page (products, industries, ...). */
public class ContentPage extends BasePage {

    public ContentPage(WebDriver driver) {
        super(driver);
    }

    public boolean loadedWithoutError() {
        String title = getTitle() == null ? "" : getTitle().toLowerCase();
        boolean errorTitle = title.contains("404") || title.contains("not found") || title.contains("error");
        String bodyText = driver.findElement(By.tagName("body")).getText();
        return !errorTitle && !title.isBlank() && !bodyText.isBlank();
    }
}
