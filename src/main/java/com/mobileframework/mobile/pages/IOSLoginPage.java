package com.mobileframework.mobile.pages;

import com.codeborne.selenide.appium.SelenideAppiumElement;
import com.mobileframework.models.Credentials;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import java.util.List;

import static com.codeborne.selenide.appium.ScreenObject.screen;

public class IOSLoginPage extends LoginPage {

    // App bug, not a locator gap: this screen never dismisses its own keyboard (no
    // Return/tap-outside/swipe handler wired up), and the keyboard window physically
    // covers the Login button while shown, so typing into text fields dead-ends here.
    // These listed demo-username buttons sit above the keyboard's screen region and fill
    // both username+password without ever opening it. Only covers the app's fixed demo
    // accounts — credentials.password() is unused on this path.
    @iOSXCUITFindBy(iOSNsPredicate = "type == 'XCUIElementTypeButton' AND name CONTAINS '@'")
    private List<SelenideAppiumElement> listedUsernameButtons;

    @Override
    public ProductsPage loginAs(Credentials credentials) {
        listedUsernameButtons.stream()
                .filter(button -> credentials.username().equals(button.getText()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "No listed username button found for " + credentials.username()))
                .click();
        loginButton.click();
        return screen(ProductsPage.class);
    }
}
