package com.mobileframework.driver;

import com.mobileframework.config.ConfigLoader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

import java.time.Duration;

public class IOSDriver implements Driver {

    private io.appium.java_client.ios.IOSDriver appiumDriver;

    @Override
    public void start() {
        var config = ConfigLoader.getInstance();
        var options = new XCUITestOptions()
                .setPlatformVersion(config.getProperty("ios.platformVersion"))
                .setDeviceName(config.getProperty("ios.deviceName"))
                .setBundleId(config.getProperty("ios.bundleId"));
        appiumDriver = new io.appium.java_client.ios.IOSDriver(Driver.serverUrl(), options);
        // TODO: remove when explicit waits land in BasePage
        appiumDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Override
    public String getPlatformName() {
        return "IOS";
    }

    @Override
    public AppiumDriver getAppiumDriver() {
        return appiumDriver;
    }
}
