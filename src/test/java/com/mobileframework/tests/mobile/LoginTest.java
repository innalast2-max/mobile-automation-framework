package com.mobileframework.tests.mobile;

import com.mobileframework.mobile.pages.LoginPage;
import com.mobileframework.mobile.pages.MoreMenuPage;
import com.mobileframework.mobile.pages.ProductsPage;
import org.testng.annotations.Test;

import static com.codeborne.selenide.appium.ScreenObject.screen;
import static com.mobileframework.tests.data.TestCredentials.valid;

public class LoginTest extends BaseMobileTest {


    @Test
    public void userCanLoginWithValidCredentials() {
        // Arrange
        LoginPage loginPage = screen(MoreMenuPage.class).openLogin();
        loginPage.shouldBeOpened();

        // Act
        ProductsPage productsPage = loginPage.loginAs(valid());

        // Assert
        productsPage.shouldBeOpened();
    }
}
