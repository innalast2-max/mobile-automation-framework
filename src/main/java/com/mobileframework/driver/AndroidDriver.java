package com.mobileframework.driver;

public class AndroidDriver implements Driver {
    @Override
    public void start() {
        System.out.println("Android driver started");
    }

    @Override
    public void stop() {
        System.out.println("Android driver stopped");
    }

    @Override
    public String getPlatformName() {
        return "Android Driver";
    }
}
