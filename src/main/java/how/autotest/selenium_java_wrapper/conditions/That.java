package how.autotest.selenium_java_wrapper.conditions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.function.Function;

public class That {
    public static Function<WebDriver, Boolean> countOfLocated(By locator, Integer expected) {
        return new Function<WebDriver, Boolean>() {

            int actual;

            @Override
            public Boolean apply(WebDriver driver) {
                actual = driver.findElements(locator).size();
                return actual >= expected;
            }

            @Override
            public String toString() {
                return "located " + locator + "\n expected count >= " + expected + "\nbut \n actual count = " + actual + "\n\n";
            }
        };
    }

    public static Function<WebDriver, Boolean> isEmptyStringValue(By locator) {
        return new Function<WebDriver, Boolean>() {

            String actual;

            @Override
            public Boolean apply(WebDriver driver) {
                actual = driver.findElement(locator).getAttribute("value");
                return actual.isEmpty();
            }

            @Override
            public String toString() {
                return "located " + locator + "\n expected equal to empty string \nbut \n actual string = " + actual + "\n\n";
            }
        };
    }

    public static Function<WebDriver, Boolean> hasText(By locator, String expected) {
        return new Function<WebDriver, Boolean>() {

            String actual;

            @Override
            public Boolean apply(WebDriver driver) {
                actual = driver.findElements(locator).get(0).getText();
                return actual.contains(expected);
            }

            @Override
            public String toString() {
                return "located " + locator + "\n expected contains " + expected + "\nbut \n actual string = " + actual + "\n\n";
            }
        };
    }

    public static Function<WebDriver, Boolean> hasTitle(String expected) {
        return new Function<WebDriver, Boolean>() {

            String actual;

            @Override
            public Boolean apply(WebDriver driver) {
                actual = driver.getTitle();
                return actual.contains(expected);
            }

            @Override
            public String toString() {
                return "title \n expected contains " + expected + "\nbut \n actual title = " + actual + "\n\n";
            }
        };
    }
}