package com.zomato.utils;

import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RestUtils {

	public static Response get(String userKey) {

		Response response = RestAssured.given().header("user-key", userKey).when().get();
		return response;
	}

	public static Response get(Map<String, String> queryParams, String userKey) {

		Response response = RestAssured.given().header("user-key", userKey).queryParams(queryParams).when().get();
		return response;
	}

	public static Response post(String body, String userKey) {

		Response response = RestAssured.given().header("user-key", userKey).when().body(body).post();
		return response;
	}

	public static Response post(Map<String, String> formParams, String userKey) {

		Response response = RestAssured.given().header("user-key", userKey).when().formParams(formParams).post();
		return response;
	}

}
