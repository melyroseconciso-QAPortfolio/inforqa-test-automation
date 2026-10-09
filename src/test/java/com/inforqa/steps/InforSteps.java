package com.inforqa.steps;

import static org.assertj.core.api.Assertions.assertThat;

import com.inforqa.core.DriverFactory;
import com.inforqa.pages.ContentPage;
import com.inforqa.pages.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/** Connects the plain-English steps in the .feature files to Java code. */
public class InforSteps {

    private HomePage homePage() {
        return new HomePage(DriverFactory.getDriver());
    }

    private ContentPage contentPage() {
        return new ContentPage(DriverFactory.getDriver());
    }

    @Given("I open the Infor home page")
    public void iOpenTheInforHomePage() {
        homePage().openHome();
    }

    @When("I open the {string} page of the Infor website")
    public void iOpenThePageOfTheInforWebsite(String path) {
        contentPage().open(path);
    }

    @Then("the page title should contain {string}")
    public void thePageTitleShouldContain(String text) {
        assertThat(homePage().getTitle()).containsIgnoringCase(text);
    }

    @Then("the page address should contain {string}")
    public void thePageAddressShouldContain(String text) {
        assertThat(homePage().getUrl()).contains(text);
    }

    @Then("the main heading should be visible")
    public void theMainHeadingShouldBeVisible() {
        assertThat(homePage().isMainHeadingVisible()).as("main heading visible").isTrue();
    }

    @Then("the page header should be visible")
    public void thePageHeaderShouldBeVisible() {
        assertThat(homePage().isHeaderVisible()).as("header visible").isTrue();
    }

    @Then("the page footer should be visible")
    public void thePageFooterShouldBeVisible() {
        assertThat(homePage().isFooterVisible()).as("footer present").isTrue();
    }

    @Then("the page should load without an error")
    public void thePageShouldLoadWithoutAnError() {
        assertThat(contentPage().loadedWithoutError()).as("page loaded without error").isTrue();
    }
}
