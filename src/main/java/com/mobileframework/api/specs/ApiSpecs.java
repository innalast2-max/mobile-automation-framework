package com.mobileframework.api.specs;

import com.mobileframework.config.ConfigLoader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.lessThan;

public final class ApiSpecs {
    private ApiSpecs() { }

    public static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigLoader.getInstance().getProperty("api.base.url"))
                .setContentType(ContentType.JSON)
                .addHeader("x-api-key", ConfigLoader.getInstance().getProperty("api.key"))
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification successSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(3000L))
                .build();
    }

    public static ResponseSpecification createdSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .build();
    }

}
