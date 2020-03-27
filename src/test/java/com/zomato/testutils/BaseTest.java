package com.zomato.testutils;

import java.io.IOException;
import org.testng.annotations.BeforeTest;
import com.zomato.utils.CommonUtils;
import io.restassured.RestAssured;

public class BaseTest {

	@BeforeTest
	public void init() throws IOException {

		RestAssured.baseURI = CommonUtils.getProperty("baseUri");
	}

}
