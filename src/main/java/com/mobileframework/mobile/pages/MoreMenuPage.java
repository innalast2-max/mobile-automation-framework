package com.mobileframework.mobile.pages;

import com.codeborne.selenide.appium.SelenideAppiumElement;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import static com.codeborne.selenide.appium.ScreenObject.screen;

public class MoreMenuPage extends BasePage {
    @iOSXCUITFindBy(accessibility = "More-tab-item")
    private SelenideAppiumElement moreTab;

    @iOSXCUITFindBy(accessibility = "LogOut-menu-item")
    private SelenideAppiumElement loginMenuItem;

    public LoginPage openLogin() {
        moreTab.click();
        loginMenuItem.click();
        return screen(LoginPage.class);
    }
}
