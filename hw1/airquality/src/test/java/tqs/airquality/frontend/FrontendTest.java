package tqs.airquality.frontend;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import io.github.bonigarcia.wdm.WebDriverManager;

@ExtendWith(SeleniumJupiter.class)
class FrontendTest {
    private WebDriver driver;

    @FindBy(css = ".input")
    private WebElement input;

    @FindBy(id = "today")
    private WebElement today;

    @FindBy(id = "forecast")
    private WebElement forecast;

    @FindBy(id = "history")
    private WebElement history;

    @FindBy(className = "card-title")
    private WebElement cardTitle;

    @BeforeEach
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    // @Disabled("Frontend")
    void today() throws InterruptedException {
        driver.get("http://localhost:5173/");

        driver.manage().window().setSize(new Dimension(1550, 1001));
        PageFactory.initElements(driver, this);

        input.click();
        input.clear();
        input.sendKeys("Oslo");

        today.click();
        Thread.sleep(3000);
        assertThat(cardTitle.getText()).isEqualTo("Spikersuppa, Oslo, Norway");
    }

    @Test
    // @Disabled("Frontend")
    void forecast() throws InterruptedException {
        driver.get("http://localhost:5173/");

        driver.manage().window().setSize(new Dimension(1550, 1001));
        PageFactory.initElements(driver, this);

        input.click();
        input.clear();
        input.sendKeys("Oslo");

        forecast.click();
        Thread.sleep(3000);
        assertThat(cardTitle.getText()).isEqualTo("Spikersuppa, Oslo, Norway");
    }

    @Test
    // @Disabled("Frontend")
    void history() throws InterruptedException {
        driver.get("http://localhost:5173/");

        driver.manage().window().setSize(new Dimension(1550, 1001));
        PageFactory.initElements(driver, this);

        input.click();
        input.clear();
        input.sendKeys("Oslo");

        history.click();
        Thread.sleep(3000);
        assertThat(cardTitle.getText()).isEqualTo("Spikersuppa, Oslo, Norway");
    }
}
