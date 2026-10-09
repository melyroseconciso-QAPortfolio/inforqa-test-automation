package com.inforqa.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * Creates and closes the browser. ThreadLocal keeps each test's browser
 * separate, which allows running tests in parallel later.
 */
public final class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static void start() {
        WebDriver driver;
        if (Config.browser().equals("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            if (Config.headless()) {
                options.addArguments("-headless");
            }
            driver = new FirefoxDriver(options);
        } else {
            ChromeOptions options = new ChromeOptions();
            if (Config.headless()) {
                options.addArguments("--headless=new");
            }
            options.addArguments("--window-size=1920,1080", "--disable-gpu", "--no-sandbox");
            driver = new ChromeDriver(options);
        }
        DRIVER.set(driver);
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void quit() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }
}
