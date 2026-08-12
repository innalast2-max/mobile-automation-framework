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

    public abstract ProductsPage loginAs(Credentials credentials);

    public LoginPage shouldBeOpened() {
        loginButton.shouldBe(visible);
        return this;
    }
}
