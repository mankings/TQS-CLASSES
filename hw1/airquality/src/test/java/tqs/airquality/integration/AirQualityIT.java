package tqs.airquality.integration;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import static org.springframework.boot.test.context.SpringBootTest.*;

/*
 * integration testing using rest assured to call the endpoints
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AirQualityIT {

    @LocalServerPort
    private Integer port;

    @Test
    void whenGetAirQualityByCity_thenStatus200() {
        String uri = "http://localhost:" + port + "/api/London/today";
        given()
        .when()
            .get(uri)
        .then()
            .statusCode(200)
            .body("location", equalTo("London"));
    }

    @Test
    void whenGetAirQualityByBadCity_thenStatus404() {
        String uri = "http://localhost:" + port + "/api/qwerty/today";
        given()
        .when()
            .get(uri)
        .then()
            .statusCode(404);
    }

    @Test
    void whenGetAirQualityForecastByCity_thenStatus200() {
        String uri = "http://localhost:" + port + "/api/London/forecast";
        given()
        .when()
            .get(uri)
        .then()
            .statusCode(200)
            .body("location", hasSize(4));
    }

    @Test
    void whenGetAirQualityHistoryByCity_thenStatus200() {
        String uri = "http://localhost:" + port + "/api/London/history";
        given()
        .when()
            .get(uri)
        .then()
            .statusCode(200)
            .body("location", hasSize(6));
    }

    @Test
    void cacheStatsMatchTests() {
        String baseUri = "http://localhost:" + port + "/api/";
        String uri = baseUri + "cache";
        given()
        .when()
            .get(uri)
        .then()
            .statusCode(200)
            .body("total", equalTo(6))
            .body("hits", equalTo(2));
    }
}
