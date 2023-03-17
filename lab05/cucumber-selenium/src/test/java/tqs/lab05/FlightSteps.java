package tqs.lab05;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class FlightSteps {
    private WebDriver driver;

    @When("I am at {string}")
    public void goToUrl(String url) {
        driver = WebDriverManager.firefoxdriver().create();
        driver.get(url);
        driver.manage().window().setSize(new Dimension(1600, 900));
    }

    @And("I choose my departure city {string} and my destination city {string}")
    public void chooseCities(String from, String to) {
        driver.findElement(By.name("fromPort")).click();
        {
            WebElement dropdown = driver.findElement(By.name("fromPort"));
            dropdown.findElement(By.xpath("//option[. = '" + from + "']")).click();
        }
        driver.findElement(By.name("toPort")).click();
        {
            WebElement dropdown = driver.findElement(By.name("toPort"));
            dropdown.findElement(By.xpath("//option[. = '" + to + "']")).click();
        }
    }

    @And("I click Find Flights button")
    public void findFlights() {
        driver.findElement(By.cssSelector(".btn-primary")).click();
    }

    @And("Page should say {string}")
    public void pageSays(String text) {
        assertThat(driver.findElement(By.cssSelector("h3")).getText(), containsString(text));
    }

    @And("I choose a flight and click the button")
    public void chooseFlight() {
        driver.findElement(By.cssSelector("tr:nth-child(3) .btn")).click();
    }

    @And("I fill in my name as {string}")
    public void fillName(String name) {
        driver.findElement(By.id("inputName")).sendKeys(name);
    }

    @And("I fill in my city as {string}")
    public void fillCity(String city) {
        driver.findElement(By.id("city")).sendKeys(city);
    }

    @And("I click Purchase Flight button")
    public void purchaseFlight() {
        driver.findElement(By.cssSelector(".btn-primary")).click();
    }

    @Then("Page title should be {string}")
    public void assertTitle(String title) {
        assertThat(driver.getTitle(), equalTo(title));
    }

}
