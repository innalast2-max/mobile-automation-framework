package com.mobileframework.driver;

public final class DriverFactory {

    private DriverFactory() {
    }

    public static Driver createDriver(Platform platform) {
        return switch (platform) {
            case ANDROID -> new AndroidDriver();
            case IOS -> new IOSDriver();
        };
    }
}
