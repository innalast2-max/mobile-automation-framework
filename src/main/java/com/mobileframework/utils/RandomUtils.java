package com.mobileframework.utils;

import java.util.List;
import java.util.Random;

public final class RandomUtils {
    private static final Random RANDOM = new Random();

    private RandomUtils() {
    }

    public static <T> T pickRandom(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Cannot pick random element from empty or null list");
        }
        return list.get(RANDOM.nextInt(list.size()));
    }
}