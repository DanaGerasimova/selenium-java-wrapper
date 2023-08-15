package how.autotest.selenium_java_wrapper;

import how.autotest.selenium_java_wrapper.commands.Action;
import how.autotest.selenium_java_wrapper.conditions.That;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class GoogleTest {

    WebDriver driver;
    WebDriverWait wait_;

    public <V> V assert_(Function<? super WebDriver, V> isTrue) {
        return wait_.until(isTrue);
    }

    public <V> V do_(Function<? super WebDriver, V> isTrue) {
        return wait_.until(isTrue);
    }

    @BeforeEach
    void setupDriver() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait_ = VerboseWebDriverWait.withIgnoringFlakyTestsErrors(driver, Duration.ofSeconds(4));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void findsSelenide() { // first examples of command and assertion that use SMARTER waiting
        driver.get("https://google.com/ncr");
        driver.findElement(By.cssSelector("#L2AGLb")).click(); // delete!!!!
        assert_(That.isEmptyStringValue(By.cssSelector("[name=q]")));

        do_(Action.type(By.cssSelector("[name=q]"), "github selenide")); // <- SMARTER
        do_(Action.pressEnter(By.cssSelector("[name=q]")));

        new WebDriverWait(driver, Duration.ofSeconds(4)).until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("#rso>div")));

        assert_(That.countOfLocated(By.cssSelector("#rso>div"), 5)); // <- SMARTER
        assert_(That.hasText(By.cssSelector("#rso>div"),"selenide › selenide"));

        do_(Action.clickOnItemHeader(By.cssSelector("#rso>div"), By.cssSelector("h3")));
        assert_(That.hasTitle("GitHub - selenide/selenide"));
    }

    @Test
    void findsSelenide_() { // pure Selenium Version
        driver.get("https://google.com/ncr");
        assertThat(driver.findElement(By.cssSelector("[name=q]")).getAttribute("value")).isEqualTo("");

        driver.findElement(By.cssSelector("[name=q]")).sendKeys("github selenide");
        driver.findElement(By.cssSelector("[name=q]")).sendKeys(Keys.ENTER);

        new WebDriverWait(driver, Duration.ofSeconds(4)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#rso>div")));
        assertThat(driver.findElements(By.cssSelector("#rso>div")).size()).isGreaterThanOrEqualTo(5);
        assertThat(driver.findElements(By.cssSelector("#rso>div")).get(0).getText()).contains("selenide › selenide");

        driver.findElements(By.cssSelector("#rso>div")).get(0).findElement(By.cssSelector("h3")).click();
        assertThat(driver.getTitle()).contains("GitHub - selenide/selenide");
    }
}