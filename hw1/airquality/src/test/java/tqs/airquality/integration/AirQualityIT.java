package tqs.airquality.integration;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import static org.springframework.boot.test.context.SpringBootTest.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AirQualityIT {

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
            .body("location", hasSize(7));
    }

    @Test
    void whenGetCacheStats_thenStatus200() {
        String baseUri = "http://localhost:" + port + "/api/";
        String uri;

        // make 2 calls for the same city to populate cache
        uri = baseUri + "London/today";
        given()
        .when()
            .get(uri)
        .then()
            .statusCode(200);

        given()
        .when()
            .get(uri)
        .then()
            .statusCode(200);

        uri = baseUri + "cache";
        given()
        .when()
            .get(uri)
        .then()
            .statusCode(200)
            .body("total", equalTo(2))
            .body("hits", equalTo(1));
    }
}
