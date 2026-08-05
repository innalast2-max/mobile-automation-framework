package com.mobileframework.driver;

import com.mobileframework.config.ConfigLoader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;

public class AndroidDriver implements Driver {
    private io.appium.java_client.android.AndroidDriver appiumDriver;

    @Override
    public void start() {
        var config = ConfigLoader.getInstance();
        var options = new UiAutomator2Options()
                .setAppPackage(config.getRequiredProperty("android.appPackage"))
                .setAppActivity(config.getRequiredProperty("android.appActivity"));
        try {
            appiumDriver = new io.appium.java_client.android.AndroidDriver(
                    URI.create(config.getProperty("appium.server.url")).toURL(), options);
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Invalid Appium server URL", e);
        }
        // TODO: remove when explicit waits land in BasePage
        appiumDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Override
    public void stop() {
        if (appiumDriver != null) {
            appiumDriver.quit();
            appiumDriver = null;
        }
    }

    @Override
    public String getPlatformName() {
        return "Android";
    }

    @Override
    public AppiumDriver getAppiumDriver() {
        return appiumDriver;
    }
}
