package com.yunhalee.withEmployee.user;

import com.yunhalee.withEmployee.AcceptanceTest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.io.File;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@DisplayName("사용자 관련 기능 인수테스트")
public class UserAcceptanceTest extends AcceptanceTest {

    public static final String EMAIL = "acceptanceTestExample@example.com";
    public static final String CEO_EMAIL = "acceptanceCeoTestExample@example.com";
    public static final String PASSWORD = "123456";

    @Test
    void manage_user() {
        // when
        ExtractableResponse<Response> createResponse = create_user_request(imageFile, requestFile);
        // then
        check_user_created(createResponse);

        // when
        ExtractableResponse<Response> findResponse = find_user_request(createResponse);
        // then
        check_user_found(findResponse);

        // when
        ExtractableResponse<Response> findAllResponse = find_users_request(1);
        // then
        check_users_found(findAllResponse);

        // when
        ExtractableResponse<Response> updateResponse = update_user_request(createResponse, updateImageFile, updateRequestFile);
        // then
        check_user_updated(updateResponse);
    }

    public static ExtractableResponse<Response> create_user_request(File imageFile, File requestFile) {
        return RestAssured
            .given().log().all()
            .contentType("multipart/form-data")
            .multiPart("multipartFile", imageFile, "image/jpeg")
            .multiPart("userRequest", requestFile, "application/json")
            .when().post("/users")
            .then().log().all()
            .extract();
    }

    public static void check_user_created(ExtractableResponse<Response> response) {
        check_create_response(response);
    }

    public static ExtractableResponse<Response> find_user_request(ExtractableResponse<Response> response) {
        String uri = response.header("Location");
        return find_request(uri, "");
    }

    public static void check_user_found(ExtractableResponse<Response> response) {
        check_ok_response(response);
    }

    public static ExtractableResponse<Response> update_user_request(ExtractableResponse<Response> response, File updateImageFile, File updateRequestFile) {
        String uri = response.header("Location");
        return RestAssured
            .given().log().all()
            .contentType("multipart/form-data")
            .multiPart("multipartFile", updateImageFile, "image/jpeg")
            .multiPart("userRequest", updateRequestFile, "application/json")
            .when().post(uri)
            .then().log().all()
            .extract();
    }

    public static void check_user_updated(ExtractableResponse<Response> response) {
        check_ok_response(response);
    }

    public static ExtractableResponse<Response> find_users_request(Integer page) {
        return find_request("/users?page=" + page, "");
    }

    public static void check_users_found(ExtractableResponse<Response> response) {
        check_ok_response(response);
    }


}
