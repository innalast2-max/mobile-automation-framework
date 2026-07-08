package com.mobileframework.tests;

import com.mobileframework.data.UserRepository;
import com.mobileframework.models.User;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UserRepositoryTests {

    @Test
    public void addedUserCanBeFoundByEmail() {
        // Arrange
        UserRepository repository = new UserRepository();
        User user = new User("Inna", "inna.yu@gmail.com", 35);

        // Act
        repository.addUser(user);
        User found = repository.findByEmail("inna.yu@gmail.com");

        // Assert
        assertEquals(found, user, "Found user should be the one we added");
    }

    @Test
    public void isEmailNotExist() {
        UserRepository repository = new UserRepository();
        assertFalse(repository.isEmailExist("ghost@test.com"),
                "Email should not exist in empty repository");
    }

    @Test
    public void foundUserEqualsNewUserWithSameEmail() {
        UserRepository repository = new UserRepository();
        User user1 = new User("Inna", "inna.yu@gmail.com", 35);
        User user2 = new User("Inna", "inna.yu@gmail.com", 35);

        repository.addUser(user1);
        repository.addUser(user2);
        User found = repository.findByEmail("inna.yu@gmail.com");

        assertEquals(found, user1, "Users with the same email should be equal");
        assertEquals(repository.countAllUsers(), 1, "Users count should be 1");
    }
}
