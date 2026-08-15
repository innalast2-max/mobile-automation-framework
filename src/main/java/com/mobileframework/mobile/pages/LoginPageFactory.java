package com.mobileframework.mobile.pages;

import com.mobileframework.driver.Platform;

import static com.codeborne.selenide.appium.ScreenObject.screen;

public final class LoginPageFactory {

    private LoginPageFactory() {
    }

    public static LoginPage create() {
        return switch (Platform.current()) {
            case ANDROID -> screen(AndroidLoginPage.class);
            case IOS -> screen(IOSLoginPage.class);
        };
    }
}
