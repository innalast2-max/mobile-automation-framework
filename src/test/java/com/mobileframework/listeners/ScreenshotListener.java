package com.mobileframework.listeners;

import com.mobileframework.driver.Driver;
import com.mobileframework.driver.DriverManager;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Hooks into {@code afterInvocation} rather than {@code ITestListener.onTestFailure}.
 * By the time {@code onTestFailure} runs, {@code AllureTestNg} (also an {@code ITestListener})
 * may already have called {@code stopTest}/{@code writeTest} for the same test — the two
 * {@code ITestListener}s load via ServiceLoader in unspecified order, so an attachment added
 * after that point can silently vanish. {@code afterInvocation} runs before any
 * {@code ITestListener} and before the {@code @AfterMethod} that tears down the driver
 * ({@code BaseMobileTest#tearDown}), so both the Allure case and the driver session are
 * still open here.
 */
public class ScreenshotListener implements IInvokedMethodListener {

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (!method.isTestMethod()) return;
        if (testResult.getStatus() != ITestResult.FAILURE) return;

        Driver driver = DriverManager.getDriver();
        if (driver == null) return;

        AppiumDriver appiumDriver = driver.getAppiumDriver();

        try {
            byte[] screenshot = appiumDriver.getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Screenshot on failure", "image/png",
                    new ByteArrayInputStream(screenshot), ".png");
        } catch (Exception e) {
            attachFailure("Screenshot capture failed", e);
        }

        try {
            String pageSource = appiumDriver.getPageSource();
            Allure.addAttachment("Page source on failure", "text/xml", pageSource, ".xml");
        } catch (Exception e) {
            attachFailure("Page source capture failed", e);
        }
    }

    private void attachFailure(String name, Exception e) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        Allure.addAttachment(name, "text/plain", writer.toString(), ".txt");
    }
}
