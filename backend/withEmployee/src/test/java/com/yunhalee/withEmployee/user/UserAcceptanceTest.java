package com.yunhalee.withEmployee.user;

import com.yunhalee.withEmployee.AcceptanceTest;
import com.yunhalee.withEmployee.user.dto.UserRequest;
import com.yunhalee.withEmployee.user.dto.UserResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.io.File;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("사용자 관련 기능 인수테스트")
public class UserAcceptanceTest extends AcceptanceTest {

    public static final String NAME = "testUser";
    public static final String NEW_NAME = "TestUpdateUser";
    public static final String EMAIL = "acceptanceTestExample@example.com";
    public static final String NEW_EMAIL = "acceptanceTestUpdateExample@example.com";
    public static final String CEO_EMAIL = "acceptanceCeoTestExample@example.com";
    public static final String PASSWORD = "123456";
    public static final String NEW_PASSWORD = "12345678";
    public static final String DESCRIPTION = "testDescription";
    public static final String NEW_DESCRIPTION = "testUpdateDescription";
    public static final String PHONE_NUMBER = "01000000000";
    public static final String NEW_PHONE_NUMBER = "01000000002";
    public static final boolean IS_CEO = false;
    public final File imageFile = new File(getClass().getClassLoader().getResource("test.jpeg").getPath());
    public final File updateImageFile = new File(getClass().getClassLoader().getResource("test2.jpeg").getPath());
    public final File requestFile = new File(getClass().getClassLoader().getResource("userRequest.txt").getPath());
    public final File updateRequestFile = new File(getClass().getClassLoader().getResource("updateUserRequest.txt").getPath());

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
        ExtractableResponse<Response> updateResponse = update_user_request(createResponse, NEW_NAME, NEW_EMAIL, NEW_PASSWORD, NEW_DESCRIPTION, NEW_PHONE_NUMBER, IS_CEO, updateImageFile, updateRequestFile);
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
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    public static ExtractableResponse<Response> find_user_request(ExtractableResponse<Response> response) {
        String uri = response.header("Location");
        return RestAssured
            .given().log().all()
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().get(uri)
            .then().log().all()
            .extract();
    }

    public static void check_user_found(ExtractableResponse<Response> response) {
        UserResponse userResponse = response.as(UserResponse.class);
        assertThat(userResponse.getId()).isNotNull();
        assertThat(userResponse.getEmail()).isEqualTo(EMAIL);
        assertThat(userResponse.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(userResponse.getImageUrl().contains(userResponse.getId() + ".")).isTrue();
        assertThat(userResponse.getPhoneNumber()).isEqualTo(PHONE_NUMBER);
        assertThat(userResponse.getRole()).isNotBlank();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static ExtractableResponse<Response> update_user_request(ExtractableResponse<Response> response, String name, String email, String password, String description, String phoneNumber, boolean isCEO, File updateImageFile, File updateRequestFile) {
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
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static ExtractableResponse<Response> find_users_request(Integer page) {
        return RestAssured
            .given().log().all()
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/users?page=" + page)
            .then().log().all()
            .extract();
    }

    public static void check_users_found(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }


}
