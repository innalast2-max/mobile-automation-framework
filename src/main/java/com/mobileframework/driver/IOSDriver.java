package com.mobileframework.driver;

public class IOSDriver implements Driver {

    @Override
    public void start() {
        System.out.println("IOS driver started");
    }

    @Override
    public void stop() {
        System.out.println("IOS driver stopped");
    }

    @Override
    public String getPlatformName() {
        return "IOS";
    }
}
