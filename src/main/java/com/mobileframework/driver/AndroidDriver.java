package com.mobileframework.driver;

import io.appium.java_client.AppiumDriver;

public class AndroidDriver implements Driver {
    private io.appium.java_client.android.AndroidDriver appiumDriver;

    @Override
    public void start() {

        throw new UnsupportedOperationException(
                "Android driver is not implemented yet. Use platform=IOS.");
    }

    @Override
    public void stop() {
        System.out.println("Android driver stopped");
    }

    @Override
    public String getPlatformName() {
        return "Android";
    }

    @Override
    public AppiumDriver getAppiumDriver() { return appiumDriver; }
}
