package com.mobileframework.api.models;

import io.restassured.http.ContentType;

public record CreateUserRequest(String name, String job) {
}
