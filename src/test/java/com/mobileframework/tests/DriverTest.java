package com.mobileframework.tests;

import com.mobileframework.driver.*;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DriverTest {

    @Test
    public void createDriverReturnsAndroidDriverForAndroidPlatform() {
        Driver driver = DriverFactory.createDriver(Platform.ANDROID);
        assertTrue(driver instanceof AndroidDriver, "Factory should create AndroidDriver for ANDROID");
        assertEquals(driver.getPlatformName(), "Android Driver");
    }

    @Test
    public void createDriverReturnsIOSDriverForAndroidPlatform() {
        Driver driver = DriverFactory.createDriver(Platform.IOS);
        assertTrue(driver instanceof IOSDriver, "Factory should create IOSDriver for IOS");
        assertEquals(driver.getPlatformName(), "IOS Driver");
    }
}
