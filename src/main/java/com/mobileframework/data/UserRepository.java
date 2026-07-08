package com.mobileframework.data;

import com.mobileframework.models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {

    private final Map<String, User> usersByEmail = new HashMap<>();

    public void addUser(User user) {
        usersByEmail.put(user.getEmail(), user);
    }

    public User findByEmail(String email) {
        return usersByEmail.get(email);
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
}
