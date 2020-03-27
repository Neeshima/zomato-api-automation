package com.zomato.test.common;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.zomato.testutils.BaseTest;
import com.zomato.utils.Constants;
import com.zomato.utils.RestUtils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class CuisineSearchTest extends BaseTest {

	String userKey = Constants.USER_KEY;

	// get all restaurants in New Delhi with Arabian Cuisine
	@Test
	public void testCuisineSearch() {

		// get lat/long values for 'New Delhi
		System.out.println(
				"-------------------------------- Getting Latitude/Longitude values --------------------------------");
		String cityName = "New Delhi";
		// set the basepath
		RestAssured.basePath = "/api/v2.1/locations";
		// form query parameters
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("query", cityName);
		Response locResponse = RestUtils.get(queryParams, userKey);
		System.out.println("Response from LocationsApi : " + locResponse.getBody().asString());
		System.out.println("Response status code : " + locResponse.statusCode());

		JSONObject jsonObj = new JSONObject(locResponse.getBody().asString());
		JSONObject locObj = jsonObj.getJSONArray("location_suggestions").getJSONObject(0);
		String latitude = Double.toString(locObj.getDouble("latitude"));
		String longitude = Double.toString(locObj.getDouble("longitude"));

		// get the cuisine matching with 'Arabian'
		System.out.println("-------------------------------- Getting All Cuisines --------------------------------");
		// set the basepath
		RestAssured.basePath = "/api/v2.1/cuisines";
		// form query parameters
		queryParams = new HashMap<String, String>();
		queryParams.put("lat", latitude);
		queryParams.put("lon", longitude);

		// REST call
		Response response = RestUtils.get(queryParams, userKey);
		System.out.println("Response from GetCuisinesApi : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());

		int cuisineId = -1;
		JSONObject cuisineObj = new JSONObject(response.getBody().asString());
		JSONArray cuisines = cuisineObj.getJSONArray("cuisines");
		for (int i = 0; i < cuisines.length(); i++) {
			JSONObject cuisine = cuisines.getJSONObject(i).getJSONObject("cuisine");
			if (cuisine.getString("cuisine_name").equalsIgnoreCase("Arabian")) {
				cuisineId = cuisine.getInt("cuisine_id");
			}
		}

		// search for restaurants with Arabian cuisine in given lat/long
		System.out
				.println("-------------------------------- Searching for restaurants --------------------------------");
		if (cuisineId != -1) {

			// set the basepath
			RestAssured.basePath = "/api/v2.1/search";
			// form query parameters
			queryParams = new HashMap<String, String>();
			queryParams.put("lat", latitude);
			queryParams.put("lon", longitude);
			queryParams.put("cuisines", Integer.toString(cuisineId));
			Response searchResponse = RestUtils.get(queryParams, userKey);
			System.out.println("Response from Search Api : " + searchResponse.getBody().asString());
			System.out.println("Response status code : " + searchResponse.statusCode());

			JSONObject searchObj = new JSONObject(searchResponse.getBody().asString());
			System.out.println(
					"No of restaurants with Arabian cuisine in New Delhi : " + searchObj.getInt("results_found"));

		} else {
			Assert.fail("Cuisine not found for city!!!");
		}

	}

}
