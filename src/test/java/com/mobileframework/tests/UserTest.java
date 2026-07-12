package com.mobileframework.tests;

import com.mobileframework.models.User;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class UserTest {
    @Test
    public void testUser() {
        User testUser = User.builder().name("Inna").email("inna.yu@gmail.com").age(35).build();
        assertEquals(testUser.name(), "Inna");
        assertEquals(testUser.email(), "inna.yu@gmail.com");
        assertEquals(testUser.age(), 35);
        assertTrue(testUser.toString().contains("Inna"),
                "toString should contain the user's name");
    }


}
