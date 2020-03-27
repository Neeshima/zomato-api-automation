package com.zomato.test.common;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.zomato.utils.Constants;
import com.zomato.utils.RestUtils;
import com.zomato.testutils.BaseTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class CategoriesTest extends BaseTest {

	String userKey = Constants.USER_KEY;

	@Test
	public void testCategories_Smoke() {

		System.out.println("-------------------------------- Get All Categories --------------------------------");
		// set the basepath
		RestAssured.basePath = "/api/v2.1/categories";

		// REST call
		Response response = RestUtils.get(userKey);
		System.out.println("Response from GetCategoriesApi : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());

		// asserting http status code
		Assert.assertEquals(response.statusCode(), 200, "Unexpected status code!!!");
	}

	@Test
	public void testCategories_Keys() {

		System.out.println("-------------------------------- Get All Categories --------------------------------");
		// set the basepath
		RestAssured.basePath = "/api/v2.1/categories";

		// REST call
		Response response = RestUtils.get(userKey);
		System.out.println("Response from GetCategoriesApi : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());

		// asserting http status code
		Assert.assertEquals(response.statusCode(), 200, "Unexpected status code!!!");

		JSONObject jsonObj = new JSONObject(response.getBody().asString());
		JSONArray categories = jsonObj.getJSONArray("categories");
		for (int i = 0; i < categories.length(); i++) {
			JSONObject category = categories.getJSONObject(i).getJSONObject("categories");
			Assert.assertNotNull(category.getInt("id"), "id should not be null");
			Assert.assertNotNull(category.getString("name"), "name should not be null");
		}

	}

	@Test
	public void testCategories_InvalidUserKey() {

		System.out.println("-------------------------------- Get All Categories --------------------------------");
		// set the basepath
		RestAssured.basePath = "/api/v2.1/categories";

		// REST call
		Response response = RestUtils.get(Constants.INVALID_USER_KEY);
		System.out.println("Response from GetCategoriesApi : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());

		// asserting http status code
		Assert.assertEquals(response.statusCode(), 403, "Unexpected status code!!!");

	}

}
