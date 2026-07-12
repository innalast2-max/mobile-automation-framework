package com.mobileframework.tests;

import com.mobileframework.data.UserRepository;
import com.mobileframework.exceptions.UserNotFoundException;
import com.mobileframework.models.User;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class UserRepositoryTests {

    @Test
    public void addedUserCanBeFoundByEmail() {
        // Arrange
        UserRepository repository = new UserRepository();
        User user = User.builder().name("Inna").email("inna.yu@gmail.com").age(35).build();

        // Act
        repository.addUser(user);
        Optional<User> found = repository.findByEmail("inna.yu@gmail.com");

        // Assert
        assertTrue(found.isPresent(), "User should be found");
        assertEquals(found.get(), user, "Found user should be the one we added");
    }

    @Test
    public void findByEmailReturnsEmptyForUnknownEmail() {
        UserRepository repository = new UserRepository();

        assertTrue(repository.findByEmail("ghost@test.com").isEmpty(),
                "Unknown email should return empty Optional");
    }

    @Test
    public void foundUserEqualsNewUserWithSameEmail() {
        UserRepository repository = new UserRepository();
        User user1 = User.builder().name("Inna").email("inna.yu@gmail.com").age(35).build();
        User user2 = User.builder().name("Inna").email("inna.yu@gmail.com").age(35).build();

        repository.addUser(user1);
        repository.addUser(user2);
        Optional<User> found = repository.findByEmail("inna.yu@gmail.com");

        assertEquals(found.get(), user1, "Users with the same email should be equal");
        assertEquals(repository.countAllUsers(), 1, "Users count should be 1");
    }

    @Test
    public void findUsersOlderThanReturnsOnlyMatchingUsers() {
        // Arrange — юзери навколо межі 30
        UserRepository repository = new UserRepository();
        User young = User.builder().name("Yehor").email("yehor@test.com").age(25).build();// молодший — має відсіятись
        User exact = User.builder().name("Inna").email("inna@test.com").age(30).build();   // рівно 30 — межа!
        User older = User.builder().name("Olha").email("olha@test.com").age(40).build();   // старший — має потрапити

        repository.addUser(young);
        repository.addUser(exact);
        repository.addUser(older);

        // Act
        List<User> result = repository.findUsersOlderThan(30);

        // Assert
        assertEquals(result.size(), 2, "Should return users with age >= 30");
        assertTrue(result.contains(exact), "User with age exactly 30 should be included");
        assertTrue(result.contains(older), "User older than 30 should be included");
        assertFalse(result.contains(young), "User younger than 30 should be excluded");
    }

    @Test
    public void findAllEmails() {
        UserRepository repository = new UserRepository();
        User user1 = User.builder().name("Yehor").email("yehor@test.com").age(25).build();
        User user2 = User.builder().name("Inna").email("inna@test.com").age(30).build();

        repository.addUser(user1);
        repository.addUser(user2);
        List<String> emails = repository.getAllEmails();

        assertEquals(emails.size(), 2, "Should return 2 emails");
        assertTrue(emails.contains("yehor@test.com"), "Should return  email 'yehor@test.com'");
        assertTrue(emails.contains("inna@test.com"), "Should return  email 'inna@test.com'");
    }

    @Test
    public void groupUsersByAgeGroupsCorrectly() {
        UserRepository repository = new UserRepository();
        User user1 = User.builder().name("Yehor").email("yehor@test.com").age(25).build();
        User user2 = User.builder().name("Inna").email("inna@test.com").age(35).build();
        User user3 = User.builder().name("Roman").email("roman@test.com").age(35).build();

        assertTrue(repository.findUsersOlderThan(35).isEmpty(), "Should return no users with age >= 35");

        repository.addUser(user1);
        repository.addUser(user2);
        repository.addUser(user3);

        assertEquals(repository.groupUsersByAge().get(35).size(), 2, "Should be 2 users in age group 35");
        assertEquals(repository.groupUsersByAge().get(25).size(), 1, "Should be 1 user in age group 25");
    }

    @Test
    public void isEmailExistReturnsTrueForAddedUserAndFalseForUnknown() {
        User user = User.builder().name("Inna").email("inna@test.com").age(35).build();

        UserRepository repository = new UserRepository();
        repository.addUser(user);

        assertTrue(repository.isEmailExist("inna@test.com"),
                "Email of added user should exist");
        assertFalse(repository.isEmailExist("ghost@test.com"),
                "Unknown email should not exist");
    }

    @Test
    public void getUserByEmailThrowsForUnknownEmail() {
        UserRepository repository = new UserRepository();

        UserNotFoundException ex = expectThrows(UserNotFoundException.class,
                () -> repository.getUserByEmail("ghost@test.com"));

        assertTrue(ex.getMessage().contains("ghost@test.com"),
                "Exception message should contain the email");
    }

    @Test
    public void getUserByEmailReturnsAddedUser() {
        UserRepository repository = new UserRepository();
        User user = User.builder().name("Inna").email("inna@test.com").age(35).build();
        repository.addUser(user);

        assertEquals(repository.getUserByEmail("inna@test.com"), user,
                "Should return the added user without throwing");
    }
}
