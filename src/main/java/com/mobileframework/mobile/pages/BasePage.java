package com.mobileframework.mobile.pages;

import com.codeborne.selenide.WebDriverRunner;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.HasOnScreenKeyboard;

import java.util.List;
import java.util.Map;

public abstract class BasePage {

    protected void hideKeyboard() {
        var driver = (AppiumDriver) WebDriverRunner.getWebDriver();

        if (!(driver instanceof HasOnScreenKeyboard keyboardAware) || !keyboardAware.isKeyboardShown()) {
            return;
        }

        driver.executeScript("mobile: hideKeyboard", Map.of("keys", List.of("Return")));
    }
}
