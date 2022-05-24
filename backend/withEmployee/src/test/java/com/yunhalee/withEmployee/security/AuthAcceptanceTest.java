package com.yunhalee.withEmployee.security;

import com.yunhalee.withEmployee.AcceptanceTest;

import static com.yunhalee.withEmployee.user.UserAcceptanceTest.*;

import com.yunhalee.withEmployee.security.jwt.JwtRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.io.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("사용자 인증 관련 기능 인수테스트")
public class AuthAcceptanceTest extends AcceptanceTest {

    @BeforeEach
    public void setUp() {
        super.setUp();
        ExtractableResponse<Response> createResponse = create_user_request(imageFile, requestFile);
        check_user_created(createResponse);
    }

    @Test
    void login() {
        // when
        ExtractableResponse<Response> loginResponse = login_request(EMAIL, PASSWORD);
        // then
        check_login_success(loginResponse);

        // when
        String token = loginResponse.jsonPath().getString("token");
        ExtractableResponse<Response> findByTokenResponse = find_user_by_token_request(token);
        // then
        check_user_found_by_token(findByTokenResponse);
    }


    public static ExtractableResponse<Response> login_request(String email, String password) {
        JwtRequest request = new JwtRequest(email, password);
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/login")
            .then().log().all()
            .extract();
    }

    public static void check_login_success(ExtractableResponse<Response> response) {
        check_ok_response(response);
        assertThat(response.jsonPath().getString("token")).isNotBlank();
    }

    public static ExtractableResponse<Response> find_user_by_token_request(String token) {
        return find_request("/login?token=" + token, token);
    }

    public static void check_user_found_by_token(ExtractableResponse<Response> response) {
        check_ok_response(response);
    }

    @Test
    void login_with_invalid_info() {
        // when
        ExtractableResponse<Response> response = login_request("invalidEmail@example.com", "invalidPassword");
        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }


    @Test
    void login_with_invalid_token() {
        // when
        ExtractableResponse<Response> response = find_user_by_token_request("invalidToken");
        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }


}
