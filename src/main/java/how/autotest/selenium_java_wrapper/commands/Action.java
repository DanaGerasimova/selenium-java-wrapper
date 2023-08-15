package how.autotest.selenium_java_wrapper.commands;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.util.function.Function;

public class Action {
    public static Function<WebDriver, Boolean> type(By locator, String text) {
        return new Function<WebDriver, Boolean>() {

            @Override
            public Boolean apply(WebDriver driver) {
                driver.findElement(locator).sendKeys(text);
                return true;
            }

            @Override
            public String toString() {
                return "command: \n\ntype text '"+ text + "' into located " + locator;
            }
        };
    }

    public static Function<WebDriver, Boolean> pressEnter(By locator) {
        return new Function<WebDriver, Boolean>() {

            @Override
            public Boolean apply(WebDriver driver) {
                driver.findElement(locator).sendKeys(Keys.ENTER);
                return true;
            }

            @Override
            public String toString() {
                return "command: \n\npress Enter into located " + locator;
            }
        };
    }

    public static Function<WebDriver, Boolean> clickOnItemHeader(By listLocator, By elementLocator) {
        return new Function<WebDriver, Boolean>() {

            @Override
            public Boolean apply(WebDriver driver) {
                driver.findElements(listLocator).get(0).findElement(elementLocator).click();
                return true;
            }

            @Override
            public String toString() {
                return "command: \n\nclick on first element located " + elementLocator
                        + " in list located " + listLocator;
            }

        };
    }
}