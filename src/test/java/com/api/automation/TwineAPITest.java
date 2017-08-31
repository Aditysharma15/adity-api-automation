package com.api.automation;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.logging.Logger;

import static io.restassured.RestAssured.given;

public class TwineAPITest {

    protected static final String URL_PATH = "https://s3.us-east-2.amazonaws.com/twine-public/apis/twine-mail-get.json";
    protected static final String ACCEPT = "Accept";
    protected static final String APPLICATION_JSON = "application/json";
    final static Logger LOGGER = Logger.getLogger(TwineAPITest.class.getName());

    @Test
    public void testGetAll() throws Exception {
        LOGGER.info("testGetAll");
        Response response =
                given().
                    headers(ACCEPT, APPLICATION_JSON).
                when().
                    get(URL_PATH).
                then().
                    statusCode(200).extract().response();
        String body = response.getBody().asString();
        LOGGER.info(body);
    }

}
