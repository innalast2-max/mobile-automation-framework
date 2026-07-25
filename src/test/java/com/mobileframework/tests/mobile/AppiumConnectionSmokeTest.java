package com.mobileframework.tests.mobile;

import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class AppiumConnectionSmokeTest extends BaseMobileTest {

    @Test
    public void appiumSessionOpensSettings() {
        assertNotNull(driver().getSessionId(), "Appium session should be created");
    }
}
