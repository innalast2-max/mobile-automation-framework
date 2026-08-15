package com.mobileframework.mobile.pages;

import com.codeborne.selenide.appium.SelenideAppiumElement;
import com.mobileframework.models.Credentials;
import io.appium.java_client.pagefactory.AndroidFindBy;

import static com.codeborne.selenide.appium.ScreenObject.screen;

public class AndroidLoginPage extends LoginPage {

    @AndroidFindBy(id = "nameET")
    private SelenideAppiumElement usernameField;

    @AndroidFindBy(id = "passwordET")
    private SelenideAppiumElement passwordField;

    @Override
    public ProductsPage loginAs(Credentials credentials) {
        usernameField.setValue(credentials.username());
        passwordField.setValue(credentials.password());
        hideKeyboard();
        loginButton.click();
        return screen(ProductsPage.class);
    }
}
