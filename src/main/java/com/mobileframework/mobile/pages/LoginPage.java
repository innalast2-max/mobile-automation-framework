package com.mobileframework.mobile.pages;

import com.codeborne.selenide.appium.SelenideAppiumElement;
import com.mobileframework.models.Credentials;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.appium.ScreenObject.screen;
import static com.codeborne.selenide.appium.SelenideAppium.$;

public class LoginPage extends BasePage {

    @iOSXCUITFindBy(iOSNsPredicate = "type == 'XCUIElementTypeTextField'")
    private SelenideAppiumElement usernameField;

    @iOSXCUITFindBy(iOSNsPredicate = "type == 'XCUIElementTypeSecureTextField'")
    private SelenideAppiumElement passwordField;

    @iOSXCUITFindBy(iOSNsPredicate = "type == 'XCUIElementTypeButton' AND name == 'Login'")
    private SelenideAppiumElement loginButton;

    // TODO: broken on iOS — keyboard overlaps Login button (see Sprint 2 backlog).
    public ProductsPage loginAs(Credentials credentials) {
        usernameField.setValue(credentials.username());
        passwordField.setValue(credentials.password());
        hideKeyboard();
        loginButton.click();
        return screen(ProductsPage.class);
    }

    public LoginPage shouldBeOpened() {
        loginButton.shouldBe(visible);
        return this;
    }

    // TODO: platform-specific locator, revisit when Android lands.
    public ProductsPage loginAsListedUser(String username) {
        $(AppiumBy.iOSNsPredicateString(
                "type == 'XCUIElementTypeButton' AND name == '%s'".formatted(username)
        )).click();
        loginButton.click();
        return screen(ProductsPage.class);
    }
}
