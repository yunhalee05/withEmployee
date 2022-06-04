package com.yunhalee.withEmployee;

import com.yunhalee.withEmployee.util.DatabaseCleanup;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.io.File;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource(locations = "/config/application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

    public final File imageFile = new File(getClass().getClassLoader().getResource("test.jpeg").getPath());
    public final File updateImageFile = new File(getClass().getClassLoader().getResource("test2.jpeg").getPath());
    public final File requestFile = new File(getClass().getClassLoader().getResource("userRequest.txt").getPath());
    public final File updateRequestFile = new File(getClass().getClassLoader().getResource("updateUserRequest.txt").getPath());
    public final File ceoRequestFile = new File(getClass().getClassLoader().getResource("ceoUserRequest.txt").getPath());

    @LocalServerPort
    int port;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @BeforeEach
    public void setUp() {
        if (RestAssured.port == RestAssured.UNDEFINED_PORT) {
            RestAssured.port = port;
            databaseCleanup.afterPropertiesSet();
        }
        databaseCleanup.execute();
    }

    public static ExtractableResponse<Response> create_request(Object request, String uri, String token) {
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post(uri)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> update_Request(Object request, String uri, String token) {
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post(uri)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> update_Request(String uri, String token) {
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().post(uri)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> find_request(String uri, String token) {
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get(uri)
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> delete_request(String uri, String token) {
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().delete(uri)
            .then().log().all()
            .extract();
    }

    public static void check_create_response(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    public static void check_ok_response(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void check_delete_response(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static void check_unauthorized_response(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }


}