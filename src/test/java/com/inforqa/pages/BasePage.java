package com.inforqa.pages;

import com.inforqa.core.Config;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/** Common helpers shared by every page class. */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    private static final By COOKIE_ACCEPT = By.xpath(
        "//*[@id='onetrust-accept-btn-handler'] | //button[contains(translate(normalize-space(.),"
            + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'accept')]");

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Config.timeoutSeconds()));
    }

    /** Opens a path on the site, e.g. "/products", then closes the cookie banner if shown. */
    public void open(String path) {
        driver.get(Config.baseUrl() + path);
        dismissCookieBanner();
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    protected boolean isVisible(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    private void dismissCookieBanner() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(COOKIE_ACCEPT))
                .click();
        } catch (Exception ignored) {
            // No banner appeared - that is fine.
        }
    }
}
