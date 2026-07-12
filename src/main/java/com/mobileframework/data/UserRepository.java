package com.mobileframework.data;

import com.mobileframework.exceptions.UserNotFoundException;
import com.mobileframework.models.User;

import java.util.*;
import java.util.stream.Collectors;

public class UserRepository {

    private static final int ADULT_AGE = 18;
    private final Map<String, User> usersByEmail = new HashMap<>();

    public void addUser(User user) {
        usersByEmail.put(user.email(), user);
    }

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(usersByEmail.get(email));
    }

    public boolean isEmailExist(String email) {
        return usersByEmail.containsKey(email);
    }

    public List<User> getAllUsers() {
        return usersByEmail.values().stream().toList();
    }

    public int countAllUsers() {
        return usersByEmail.size();
    }

    public List<User> findUsersOlderThan(int age) {
        return usersByEmail.values().stream()
                .filter(user -> user.age() >= age)
                .toList();
    }

    public List<String> getAllEmails() {
        return usersByEmail.values().stream()
                .map(User::email)
                .toList();
    }

    public List<User> getUsersSortedByName() {
        return usersByEmail.values().stream()
                .sorted(Comparator.comparing(User::name))
                .toList();
    }

    public Map<Integer, List<User>> groupUsersByAge() {
        return usersByEmail.values().stream()
                .collect(Collectors.groupingBy(User::age));
    }

    public long countAdults() {
        return usersByEmail.values().stream()
                .filter(user -> user.age() >= ADULT_AGE)
                .count();
    }

    public User getUserByEmail(String email) {
        return findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }

}
