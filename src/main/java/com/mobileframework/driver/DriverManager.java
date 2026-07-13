package com.mobileframework.driver;

public final class DriverManager {

    private static final ThreadLocal<Driver> DRIVER = new ThreadLocal<>();

    private DriverManager() { }

    public static void setDriver(Driver driver) {
        DRIVER.set(driver);
    }

    public static Driver getDriver() {
        return DRIVER.get();
    }

    public static void removeDriver() {
        DRIVER.remove();
    }

}
