package com.mobileframework.driver;

import com.mobileframework.config.ConfigLoader;

public enum Platform {
    ANDROID,
    IOS;

    public static Platform current() {
        return valueOf(ConfigLoader.getInstance().getRequiredProperty("platform"));
    }
}

