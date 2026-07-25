package com.mobileframework.mobile.pages;

import com.codeborne.selenide.appium.SelenideAppiumElement;
import com.mobileframework.models.Credentials;
import com.mobileframework.models.User;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.appium.ScreenObject.screen;

public class LoginPage extends BasePage {

    @iOSXCUITFindBy(iOSNsPredicate = "type == 'XCUIElementTypeTextField'")
    private SelenideAppiumElement usernameField;

    @iOSXCUITFindBy(iOSNsPredicate = "type == 'XCUIElementTypeSecureTextField'")
    private SelenideAppiumElement passwordField;

    @iOSXCUITFindBy(iOSNsPredicate = "type == 'XCUIElementTypeButton' AND name == 'Login'")
    private SelenideAppiumElement loginButton;

    @iOSXCUITFindBy(iOSNsPredicate = "type == 'XCUIElementTypeButton' AND name == 'Return'")
    private SelenideAppiumElement keyboardReturnKey;

    @iOSXCUITFindBy(iOSNsPredicate =
            "type == 'XCUIElementTypeButton' AND name == 'bob@example.com'")
    private SelenideAppiumElement predefinedUserButton;

    public ProductsPage loginAs(Credentials credentials) {
        usernameField.setValue(credentials.username());
        passwordField.setValue(credentials.password());
        hideKeyboard();
        loginButton.click();
        return screen(ProductsPage.class);
    }

    public ProductsPage loginAsPredefinedUser() {
        predefinedUserButton.click();
        loginButton.click();
        return screen(ProductsPage.class);
    }

    public LoginPage shouldBeOpened() {
        loginButton.shouldBe(visible);
        return this;
    }

    private void dismissKeyboard() {
        if (keyboardReturnKey.exists()) {
            keyboardReturnKey.click();
        }
    }
}
