package how.autotest.selenium_java_wrapper;

import com.google.common.collect.ImmutableList;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

public class VerboseWebDriverWait extends WebDriverWait {
    WebDriver driver;

    public VerboseWebDriverWait(WebDriver driver, Duration timeout) {
        super(driver, timeout);
        this.driver = driver; // we store to use it inside overridden timeoutException
        // ... to take screenshot, store it, and add its path to message
    }

    public static WebDriverWait withIgnoringFlakyTestsErrors(WebDriver driver, Duration timeout) {
        return (WebDriverWait) new VerboseWebDriverWait(driver, timeout).ignoreAll(ImmutableList.of(
                StaleElementReferenceException.class,
                InvalidElementStateException.class,
                JavascriptException.class,
                MoveTargetOutOfBoundsException.class,
                NotFoundException.class

        ));
    }

    @Override
    protected RuntimeException timeoutException(String message, Throwable lastException) {
        String path = "C:\\Users\\ASUS\\IdeaProjects\\selenium-java-wrapper\\screenshots\\"
                + UUID.randomUUID() + ".png";
        File file = ((TakesScreenshot)this.driver).getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(file, new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var messageWithCauseAndScreenshot = message
                + "\n"
                + (lastException == null ? "" : "\nCaused by:\n" + lastException.getMessage())
                + " with screenshot " + path;
        return super.timeoutException(messageWithCauseAndScreenshot, lastException);
    }
}