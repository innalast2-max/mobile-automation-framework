package com.mobileframework.driver;

import com.mobileframework.config.ConfigLoader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

import java.net.MalformedURLException;
import java.net.URI;
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
        try {
            appiumDriver = new io.appium.java_client.ios.IOSDriver(
                    URI.create(config.getProperty("appium.server.url")).toURL(), options);
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Invalid Appium server URL", e);
        }
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
        return "IOS";
    }

    @Override
    public AppiumDriver getAppiumDriver() { return appiumDriver; }
}
