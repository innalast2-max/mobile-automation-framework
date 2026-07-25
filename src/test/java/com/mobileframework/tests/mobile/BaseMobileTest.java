package com.mobileframework.tests.mobile;

import com.mobileframework.config.ConfigLoader;
import com.mobileframework.driver.Driver;
import com.mobileframework.driver.DriverFactory;
import com.mobileframework.driver.DriverManager;
import com.mobileframework.driver.Platform;
import io.appium.java_client.AppiumDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseMobileTest {

    @BeforeMethod
    public void setUp() {
        var platform = Platform.valueOf(
                ConfigLoader.getInstance().getProperty("platform"));
        Driver driver = DriverFactory.createDriver(platform);
        driver.start();
        DriverManager.setDriver(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        var driver = DriverManager.getDriver();
        if (driver != null) driver.stop();
        DriverManager.removeDriver();
    }

    protected AppiumDriver driver() {
        var d = DriverManager.getDriver();
        if (d == null) throw new IllegalStateException(
                "Driver not initialized — driver() must be called inside a test method");
        return d.getAppiumDriver();
    }
}
