package com.mobileframework.driver;

import com.mobileframework.config.ConfigLoader;
import io.appium.java_client.AppiumDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public interface Driver {
    void start();
    String getPlatformName();
    AppiumDriver getAppiumDriver();

    default void stop() {
        var driver = getAppiumDriver();
        if (driver != null) {
            driver.quit();
        }
    }

    static URL serverUrl() {
        try {
            return URI.create(ConfigLoader.getInstance().getProperty("appium.server.url")).toURL();
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Invalid Appium server URL", e);
        }
    }
}
