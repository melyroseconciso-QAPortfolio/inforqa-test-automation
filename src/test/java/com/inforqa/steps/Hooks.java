package com.inforqa.steps;

import com.inforqa.core.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

/** Runs automatically before and after every scenario. */
public class Hooks {

    @Before
    public void setUp() {
        DriverFactory.start();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed() && DriverFactory.getDriver() != null) {
            byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Screenshot on failure");
        }
        DriverFactory.quit();
    }
}
