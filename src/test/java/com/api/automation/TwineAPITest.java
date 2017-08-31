package com.api.automation;

import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.logging.Logger;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TwineAPITest {

    private static final String URL_PATH = "https://s3.us-east-2.amazonaws.com/twine-public/apis/twine-mail-get.json";
    private static final String ACCEPT = "Accept";
    private static final String APPLICATION_JSON = "application/json";
    private final static Logger LOGGER = Logger.getLogger(TwineAPITest.class.getName());

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




    @Test
    public void testGetAPIPassNumberOfRecords() throws Exception {
        LOGGER.info("testGetAPIPassNumberOfRecords");
        given().
            headers(ACCEPT, APPLICATION_JSON).
        when().
            get(URL_PATH).
        then().
            statusCode(200).assertThat().
        body("emails", Matchers.hasSize(6));
    }

    @Test
    public void testGetAPIFailForUnread() throws Exception {
        LOGGER.info("testGetAPIFailForUnread");
        given().
            headers(ACCEPT, APPLICATION_JSON).
        when().
            get(URL_PATH).
        then().
            statusCode(200).assertThat().
            body("emails.get(0).unread", Matchers.is(false));
    }

    @Test
    public void testGetAPIPassForUnread() throws Exception {
        LOGGER.info("testGetAPIPassForUnread");
        given().
            headers(ACCEPT, APPLICATION_JSON).
        when().
            get(URL_PATH).
        then().
            statusCode(200).assertThat().
            body("emails.get(4).unread", Matchers.is(false));
    }

   //This test will fail as expected Date Expected: 2017-08-01T14:30Z Actual: 2017-08-01T12:15Z
    @Test
    public void testGetAPIFailsForTimeStamp() throws Exception {
        LOGGER.info("testGetAPIFailsForTimeStamp");
        given().
            headers(ACCEPT, APPLICATION_JSON).
        when().
            get(URL_PATH).
        then().
            statusCode(200).assertThat().
            body("emails.get(1).date", equalTo("2017-08-01T14:30Z"));
    }

    @Test
    public void testGetAPIPassForTimeStamp() throws Exception {
        LOGGER.info("testGetAPIPassForTimeStamp");
        given().
            headers(ACCEPT, APPLICATION_JSON).
        when().
            get(URL_PATH).
        then().
            statusCode(200).assertThat().
            body("emails.get(0).date", equalTo("2017-08-01T14:30Z"));
    }

    @Test
    public void testGetAPIFailsForSubject() throws Exception {
        LOGGER.info("testGetAPIFailsForSubject");
        given().
            headers(ACCEPT, APPLICATION_JSON).
        when().
            get(URL_PATH).
        then().
            statusCode(200).assertThat().
            body("emails.get(4).subject", equalTo("Photos"));
    }

    @Test
    public void testGetAPIPassForSubject() throws Exception {
        LOGGER.info("testGetAPIPassForSubject");
        given().
            headers(ACCEPT, APPLICATION_JSON).
        when().
            get(URL_PATH).
        then().
            statusCode(200).assertThat().
            body("emails.get(5).subject", equalTo("History of Lorem Ipsum"));
    }


    @Test
    public void testGetAPIFailsForId() throws Exception {
        LOGGER.info("testGetAPIFailsForId");
        given().
            headers(ACCEPT, APPLICATION_JSON).
        when().
            get(URL_PATH).
        then().
            statusCode(200).assertThat().
            body("emails.get(4).id", equalTo(1));
    }

    @Test
    public void testGetAPIPassForId() throws Exception {
        LOGGER.info("testGetAPIPassForId");
        given().
            headers(ACCEPT, APPLICATION_JSON).
        when().
            get(URL_PATH).
        then().
            statusCode(200).assertThat().
            body("emails.get(5).id", equalTo(5));
    }



    @Test
    public void testGetAPIFailsForBody() throws Exception {
        LOGGER.info("testGetAPIFailsForBody");
        given().
            headers(ACCEPT, APPLICATION_JSON).
        when().
            get(URL_PATH).
        then().
            statusCode(200).assertThat().
            body("emails.get(3).body", equalTo("Ornare integer tincidunt quis ut nam, lobortis dignissim, montes eros"));
    }

    @Test
    public void testGetAPIPassForBody() throws Exception {
        LOGGER.info("testGetAPIPassForBody");
        given().
            headers(ACCEPT, APPLICATION_JSON).
        when().
            get(URL_PATH).
        then().
            statusCode(200).assertThat().
            body("emails.get(4).body", equalTo("Ornare integer tincidunt quis ut nam, lobortis dignissim, montes eros"));
    }


    @Test
    public void testGetAPIPassesForToList() throws Exception {
        LOGGER.info("testGetAPIPassesForToList");
        given().
            headers(ACCEPT, APPLICATION_JSON).
        when().
            get(URL_PATH).
        then().
            statusCode(200).assertThat().
            body("emails.get(4).to", Matchers.hasItems("a@gmail.com","b@gmail.com","c.gmail.com"));
    }

    @Test
    public void testGetAPIFailsForToList() throws Exception {
        LOGGER.info("testGetAPIFailsForToList");
        given().
            headers(ACCEPT, APPLICATION_JSON).
        when().
            get(URL_PATH).
        then().
            statusCode(200).assertThat().
            body("emails.get(4).to", Matchers.hasItems("a@gmail.com","b@gmail.com","c@gmail.com"));
    }


    @Test
    public void testPutAPIFails() throws Exception {
        LOGGER.info("testPutAPIFails");
        given().
            headers(ACCEPT, APPLICATION_JSON).
        when().
            put(URL_PATH).
        then().
            statusCode(200);
    }

}
