package com.mobileframework.driver;

public final class DriverFactory {

    private DriverFactory() {
    }

    public static Driver createDriver(Platform platform) {
        return switch (platform) {                    // modern switch (Java 14+)
            case ANDROID -> new AndroidDriver();
            case IOS -> new IOSDriver();
        };
    }
}
