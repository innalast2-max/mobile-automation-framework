package com.mobileframework.tests;

import com.mobileframework.utils.RandomUtils;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class RandomUtilsTest {
    @Test
    public void pickRandomReturnsTheOnlyElementFromSingleElementList() {
        List<String> single = List.of("Inna");

        String result = RandomUtils.pickRandom(single);

        assertEquals(result, "Inna", "Single-element list should return its only element");
    }

    @Test
    public void pickRandomReturnsElementFromTheList() {
        List<String> names = List.of("Inna", "Yehor", "Olha");

        String result = RandomUtils.pickRandom(names);

        assertTrue(names.contains(result), "Picked element should belong to the source list");
    }

    @Test
    public void pickRandomThrowsForEmptyList() {
        IllegalArgumentException ex = expectThrows(IllegalArgumentException.class,
                () -> RandomUtils.pickRandom(List.of()));
        assertTrue(ex.getMessage().contains("empty"), "Message should explain the problem");
    }
}
