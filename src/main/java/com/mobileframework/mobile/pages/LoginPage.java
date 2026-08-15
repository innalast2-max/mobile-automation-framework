package com.mobileframework.mobile.pages;

import com.codeborne.selenide.appium.SelenideAppiumElement;
import com.mobileframework.models.Credentials;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import static com.codeborne.selenide.Condition.visible;

public abstract class LoginPage extends BasePage {

    @iOSXCUITFindBy(iOSNsPredicate = "type == 'XCUIElementTypeButton' AND name == 'Login'")
    @AndroidFindBy(id = "loginBtn")
    protected SelenideAppiumElement loginButton;

    // Workaround, root cause NOT verified: assumed the screen never dismisses its own
// keyboard and it covers the Login button. Not confirmed in Inspector — see backlog.
// These listed demo-username buttons fill both fields without opening the keyboard,
// so credentials.password() is unused on this path — a negative test with a wrong
// password is therefore impossible on iOS until this is resolved.
    public abstract ProductsPage loginAs(Credentials credentials);

    public LoginPage shouldBeOpened() {
        loginButton.shouldBe(visible);
        return this;
    }
}
