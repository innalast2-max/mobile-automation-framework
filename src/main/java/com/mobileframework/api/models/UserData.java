package com.mobileframework.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserData(int id, String email,
                       @JsonProperty("first_name") String firstName,
                       @JsonProperty("last_name") String lastName,
                       String avatar) {}
