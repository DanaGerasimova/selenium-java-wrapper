package how.autotest.selenium_java_wrapper.conditions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.function.Function;

public class That {
    public static Function<WebDriver, Boolean> countOfLocated(By locator, Integer expected) {
        return new Function<>() {

            int actual;

            @Override
            public Boolean apply(WebDriver driver) {
                actual = driver.findElements(locator).size();
                return actual >= expected;
            }

            @Override
            public String toString() {
                return "located " + locator + "\n expected count >= " + expected + "\nbut \n actual count = "
                        + actual + "\n\n";
            }
        };
    }

    public static Function<WebDriver, Boolean> valueOfLocated(By locator, String expected) {
        return new Function<>() {

            String actual;

            @Override
            public Boolean apply(WebDriver driver) {
                actual = driver.findElement(locator).getAttribute("value");
                return actual.equals(expected);
            }

            @Override
            public String toString() {
                return "located " + locator + "\n expected value attribute = " + expected
                        + "\nbut \n actual value attribute = " + actual + "\n\n";
            }
        };
    }

    public static Function<WebDriver, Boolean> partialTextOfLocated(By locator, String expected) {
        return new Function<>() {

            String actual;

            @Override
            public Boolean apply(WebDriver driver) {
                actual = driver.findElement(locator).getText();
                return actual.contains(expected);
            }

            @Override
            public String toString() {
                return "located " + locator + "\n expected partial text " + expected
                        + "\nbut \n actual text = " + actual + "\n\n";
            }
        };
    }

    public static Function<WebDriver, Boolean> titleContains(String expected) {
        return new Function<>() {

            String actual;

            @Override
            public Boolean apply(WebDriver driver) {
                actual = driver.getTitle();
                return actual.contains(expected);
            }

            @Override
            public String toString() {
                return "page \n expected title contains " + expected
                        + "\nbut \n actual title = " + actual + "\n\n";
            }
        };
    }
}