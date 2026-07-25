package com.mobileframework.mobile.pages;

import com.codeborne.selenide.WebDriverRunner;
import io.appium.java_client.AppiumDriver;

import java.util.List;
import java.util.Map;

public abstract class BasePage {

    protected void hideKeyboard() {
        var driver = (AppiumDriver) WebDriverRunner.getWebDriver();
        try {
            driver.executeScript("mobile: hideKeyboard", Map.of("keys", List.of("Done", "return", "Return")));
        } catch (Exception ignored) {
        }
    }
}
