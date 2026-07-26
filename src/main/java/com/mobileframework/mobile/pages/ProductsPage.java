package com.mobileframework.mobile.pages;

import com.codeborne.selenide.appium.SelenideAppiumElement;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import static com.codeborne.selenide.Condition.visible;

public class ProductsPage extends BasePage {

    @iOSXCUITFindBy(accessibility = "Catalog-screen")
    private SelenideAppiumElement catalogScreen;

    public ProductsPage shouldBeOpened() {
        catalogScreen.shouldBe(visible);
        return this;
    }
}
