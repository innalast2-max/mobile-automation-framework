package com.mobileframework.tests.mobile;

import com.mobileframework.mobile.pages.MoreMenuPage;
import org.testng.annotations.Test;

import static com.codeborne.selenide.appium.ScreenObject.screen;

public class LoginTest extends BaseMobileTest {


    @Test
    public void userCanLoginWithValidCredentials() {
        screen(MoreMenuPage.class)
                .openLogin()
                .shouldBeOpened()
                .loginAsPredefinedUser()
                .shouldBeOpened();
    }
}
