package com.mobileframework.tests.data;

import com.mobileframework.config.ConfigLoader;
import com.mobileframework.models.Credentials;

public class TestCredentials {
    private static final ConfigLoader CONFIG = ConfigLoader.getInstance();

    private TestCredentials() {}

    public static Credentials valid() {
        return new Credentials(
                CONFIG.getRequiredProperty("login.valid.username"),
                CONFIG.getRequiredProperty("login.valid.password"));
    }

    public static Credentials wrongPassword() {
        return new Credentials(CONFIG.getRequiredProperty("login.valid.username"), "wrong-pass");
    }
}
