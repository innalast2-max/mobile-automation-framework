package com.mobileframework.tests;

import com.mobileframework.config.ConfigLoader;
import com.mobileframework.driver.*;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class DriverTest {

    @Test
    public void createDriverReturnsAndroidDriverForAndroidPlatform() {
        Driver driver = DriverFactory.createDriver(Platform.ANDROID);
        assertTrue(driver instanceof AndroidDriver, "Factory should create AndroidDriver for ANDROID");
        assertEquals(driver.getPlatformName(), "Android");
    }

    @Test
    public void createDriverReturnsIOSDriverForIOSPlatform() {
        Driver driver = DriverFactory.createDriver(Platform.IOS);
        assertTrue(driver instanceof IOSDriver, "Factory should create IOSDriver for IOS");
        assertEquals(driver.getPlatformName(), "IOS");
    }

    @Test
    public void getInstanceReturnsSameInstance() {
        ConfigLoader first = ConfigLoader.getInstance();
        ConfigLoader second = ConfigLoader.getInstance();

        assertSame(first, second, "getInstance must return the same instance");
    }

    @Test
    public void configLoaderReturnsPlatformFromFile() {
        String platform = ConfigLoader.getInstance().getProperty("platform");

        assertEquals(platform, "IOS", "Platform should be loaded from config.properties");
    }

    @Test
    public void driverLifecycleWorksWithConfigPlatform() {

        String platformName = ConfigLoader.getInstance().getProperty("platform");
        Platform platform = Platform.valueOf(platformName);
        Driver driver = DriverFactory.createDriver(platform);
        DriverManager.setDriver(driver);

        assertSame(DriverManager.getDriver(), driver, "Manager should return the driver we set");

        DriverManager.removeDriver();
        assertNull(DriverManager.getDriver(), "After remove, driver should be null");
    }
}
