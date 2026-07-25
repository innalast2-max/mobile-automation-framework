package com.mobileframework.driver;

import io.appium.java_client.AppiumDriver;

public interface Driver {
    void start();
    void stop();
    String getPlatformName();
    AppiumDriver getAppiumDriver();
}
