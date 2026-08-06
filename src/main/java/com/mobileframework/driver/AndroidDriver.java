package com.mobileframework.driver;

import com.mobileframework.config.ConfigLoader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.time.Duration;

public class AndroidDriver implements Driver {
    private io.appium.java_client.android.AndroidDriver appiumDriver;

    @Override
    public void start() {
        var config = ConfigLoader.getInstance();
        var options = new UiAutomator2Options()
                .setAppPackage(config.getRequiredProperty("android.appPackage"))
                .setAppActivity(config.getRequiredProperty("android.appActivity"));
        appiumDriver = new io.appium.java_client.android.AndroidDriver(Driver.serverUrl(), options);
        // TODO: remove when explicit waits land in BasePage
        appiumDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
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
