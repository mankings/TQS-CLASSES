package tqs.lab04;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.Dimension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.github.bonigarcia.seljup.SeleniumJupiter;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SeleniumJupiter.class)
public class PageObjectTest {
    private WebDriver driver;

    private static String PAGE_URL = "https://blazedemo.com/";

    @FindBy(name = "fromPort")
    private WebElement fromPortButton;

    @FindBy(xpath = "//option[. = 'Boston']")
    private WebElement bostonOption;

    @FindBy(css = ".form-inline:nth-child(1) > option:nth-child(3)")
    private WebElement button1;

    @FindBy(name = "toPort")
    private WebElement toPortButton;

    @FindBy(xpath = "//option[. = 'Berlin']")
    private WebElement berlinOption;

    @FindBy(css = ".form-inline:nth-child(4) > option:nth-child(4)")
    private WebElement button2;

    @FindBy(css = ".btn-primary")
    private WebElement button3;

    @FindBy(css = "tr:nth-child(4) .btn")
    private WebElement button4;

    @FindBy(id = "inputName")
    private WebElement nameInput;

    @FindBy(id = "address")
    private WebElement addressInput;

    @FindBy(id = "city")
    private WebElement cityInput;

    @FindBy(id = "state")
    private WebElement stateInput;

    @FindBy(id = "zipCode")
    private WebElement zipCodeInput;

    @FindBy(xpath = "//option[. = 'American Express']")
    private WebElement cardTypeOption;

    @FindBy(id = "creditCardNumber")
    private WebElement cardNumber;

    @FindBy(id = "creditCardMonth")
    private WebElement cardMonth;

    @FindBy(id = "creditCardYear")
    private WebElement cardYear;

    @FindBy(id = "nameOnCard")
    private WebElement nameOnCard;

    @FindBy(css = ".btn-primary")
    private WebElement button5;

    @BeforeEach
    public void setUp() {
        driver = new FirefoxDriver();
        // vars = new HashMap<String, Object>();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void translatedTest() {
        driver.get(PAGE_URL);
        driver.manage().window().setSize(new Dimension(1600, 900));
        PageFactory.initElements(driver, this);

        fromPortButton.click();
        bostonOption.click();
        button1.click();

        toPortButton.click();
        berlinOption.click();
        button2.click();

        button3.click();
        button4.click();

        assertThat(driver.getTitle()).isEqualTo("BlazeDemo Purchase");

        nameInput.sendKeys("Miguel");
        addressInput.sendKeys("Rua Nervosa");
        cityInput.sendKeys("Candieira");
        stateInput.sendKeys("Bragança");
        zipCodeInput.sendKeys("420");

        cardTypeOption.click();

        cardNumber.sendKeys("9192");
        cardMonth.sendKeys("3");
        cardYear.sendKeys("2018");

        nameOnCard.sendKeys("Mankings");
        
        button5.click();

        assertThat(driver.getTitle()).isEqualTo("BlazeDemo Confirmation");
    }
}
