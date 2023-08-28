package how.autotest.selenium_java_wrapper;

import com.google.common.collect.ImmutableList;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

public class VerboseWebDriverWait extends WebDriverWait {
    WebDriver driver;

    public VerboseWebDriverWait(WebDriver driver, Duration timeout) {
        super(driver, timeout);
        this.driver = driver;
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
        Path path = Path.of(System.getProperty("user.dir") + "/screenshots/");
        byte[] file = ((TakesScreenshot)this.driver).getScreenshotAs(OutputType.BYTES);
        Path tmpFilePath;

        try {
            tmpFilePath = Files.createTempFile(path, String.valueOf(UUID.randomUUID()), ".png");
            Files.write(tmpFilePath, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Optional<String> result = Optional.empty();
        Optional<Path> screenshotResult = Optional.empty();

        if (true){
            result = Optional.of(lastException.getMessage());
        }

        if (true){
            screenshotResult = Optional.of(tmpFilePath);
        }

        String finalResult;
        String finalScreenshotResult;

        if(result.isPresent()){
            finalResult = result.get();
        }else finalResult = "";

        if(screenshotResult.isPresent()){
            finalScreenshotResult = String.valueOf(screenshotResult.get());
        }else finalScreenshotResult = "";

        var messageWithCauseAndScreenshot = message
                + "\n"
                + finalResult + "\n"
                + " with screenshot " + "\n"
                + finalScreenshotResult;
        return super.timeoutException(messageWithCauseAndScreenshot, lastException);
    }
}