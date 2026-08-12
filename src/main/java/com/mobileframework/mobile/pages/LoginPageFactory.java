package com.mobileframework.mobile.pages;

import com.mobileframework.config.ConfigLoader;
import com.mobileframework.driver.Platform;

public final class LoginPageFactory {

    private LoginPageFactory() {
    }

    public static LoginPage create() {
        Platform platform = Platform.valueOf(ConfigLoader.getInstance().getProperty("platform"));
        return switch (platform) {
            case ANDROID -> new AndroidLoginPage();
            case IOS -> new IOSLoginPage();
        };
    }
}
