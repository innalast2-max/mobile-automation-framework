package com.mobileframework.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SingleUserResponse(UserData data, Support support) { }

