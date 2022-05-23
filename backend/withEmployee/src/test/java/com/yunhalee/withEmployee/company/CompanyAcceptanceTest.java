package com.yunhalee.withEmployee.company;

import static com.yunhalee.withEmployee.user.UserAcceptanceTest.EMAIL;
import static com.yunhalee.withEmployee.user.UserAcceptanceTest.PASSWORD;
import static com.yunhalee.withEmployee.user.UserAcceptanceTest.check_user_created;
import static com.yunhalee.withEmployee.user.UserAcceptanceTest.create_user_request;
import static com.yunhalee.withEmployee.security.AuthAcceptanceTest.*;

import com.yunhalee.withEmployee.AcceptanceTest;
import com.yunhalee.withEmployee.company.dto.CompanyListResponse;
import com.yunhalee.withEmployee.company.dto.CompanyRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.io.File;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import static org.assertj.core.api.Assertions.assertThat;


public class CompanyAcceptanceTest extends AcceptanceTest {

    public final File imageFile = new File(getClass().getClassLoader().getResource("test.jpeg").getPath());
    public final File ceoRequestFile = new File(getClass().getClassLoader().getResource("ceoUserRequest.txt").getPath());
    public final File requestFile = new File(getClass().getClassLoader().getResource("userRequest.txt").getPath());


    public static final String COMPANY_NAME = "testCompany";
    public static final String NEW_COMPANY_NAME = "testUpdateCompany";
    public static final String COMPANY_DESCRIPTION = "testCompanyDescription";
    public static final String NEW_COMPANY_DESCRIPTION = "testUpdateCompanyDescription";
    public static final String KEYWORD = "test";
    private Integer userId;
    private String token;

    void success_case_set_up() {
        ExtractableResponse<Response> createResponse = create_user_request(imageFile, ceoRequestFile);
        check_user_created(createResponse);
        userId = Integer.parseInt(createResponse.header("Location").split("/")[2]);

        ExtractableResponse<Response> loginResponse = login_request(EMAIL, PASSWORD);
        check_login_success(loginResponse);
        token = loginResponse.jsonPath().getString("token");
    }

    void fail_case_set_up() {
        ExtractableResponse<Response> createResponse = create_user_request(imageFile, requestFile);
        check_user_created(createResponse);
        userId = Integer.parseInt(createResponse.header("Location").split("/")[2]);

        ExtractableResponse<Response> loginResponse = login_request(EMAIL, PASSWORD);
        check_login_success(loginResponse);
        token = loginResponse.jsonPath().getString("token");
    }

    @Test
    void manage_company() {
        // given
        success_case_set_up();

        // when
        ExtractableResponse<Response> createResponse = create_company_request(COMPANY_NAME, COMPANY_DESCRIPTION, userId, token);
        // then
        check_company_created(createResponse);

        // when
        ExtractableResponse<Response> findResponse = find_company_request(createResponse);
        // then
        check_company_found(findResponse);

        // when
        ExtractableResponse<Response> findByCeoResponse = find_companies_by_ceo_request(userId);
        // then
        check_companies_by_ceo_found(findByCeoResponse);

        // when
        ExtractableResponse<Response> findByKeywordResponse = find_companies_keyword_request(KEYWORD);
        // then
        check_companies_by_keyword_found(findByKeywordResponse);

        // when
        ExtractableResponse<Response> findByRandomResponse = find_companies_by_random_request();
        // then
        check_company_by_random_found(findByRandomResponse);

        // when
        ExtractableResponse<Response> findAllResponse = find_companies_request();
        // then
        check_companies_found(findAllResponse);

        // when
        ExtractableResponse<Response> updateResponse = update_company_request(createResponse, NEW_COMPANY_NAME, NEW_COMPANY_DESCRIPTION, userId, token);
        // then
        check_company_updated(updateResponse);

        // when
        ExtractableResponse<Response> deleteResponse = delete_company_request(updateResponse, token);
        // then
        check_company_deleted(deleteResponse);

    }

    public static ExtractableResponse<Response> create_company_request(String name, String description, Integer ceoId, String token){
        CompanyRequest request = new CompanyRequest(name, description, ceoId);
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/companies")
            .then().log().all()
            .extract();
    }

    public static void check_company_created(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static ExtractableResponse<Response> find_company_request(ExtractableResponse<Response> response){
        Integer id = (response.as(CompanyListResponse.class)).getId();
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/companies/" + id)
            .then().log().all()
            .extract();
    }

    public static void check_company_found(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static ExtractableResponse<Response> find_companies_by_ceo_request(Integer ceoId){
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("users/" + ceoId + "/companies")
            .then().log().all()
            .extract();
    }

    public static void check_companies_by_ceo_found(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static ExtractableResponse<Response> find_companies_keyword_request(String keyword){
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/companies?keyword=" + keyword)
            .then().log().all()
            .extract();
    }

    public static void check_companies_by_keyword_found(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static ExtractableResponse<Response> find_companies_by_random_request(){
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/companies/recommendation")
            .then().log().all()
            .extract();
    }

    public static void check_company_by_random_found(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static ExtractableResponse<Response> find_companies_request(){
        Integer page = 1;
        String sort = "createdAt";
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/companies?page=" + page + "&sort=" + sort)
            .then().log().all()
            .extract();
    }

    public static void check_companies_found(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static ExtractableResponse<Response> update_company_request(ExtractableResponse<Response> response, String name, String description, Integer ceoId, String token){
        Integer id = (response.as(CompanyListResponse.class)).getId();
        CompanyRequest request = new CompanyRequest(name, description, ceoId);
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/companies/" + id)
            .then().log().all()
            .extract();
    }

    public static void check_company_updated(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static ExtractableResponse<Response> delete_company_request(ExtractableResponse<Response> response, String token){
        Integer id = (response.as(CompanyListResponse.class)).getId();
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().delete("/companies/" + id)
            .then().log().all()
            .extract();
    }

    public static void check_company_deleted(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }


    @Test
    void create_company_by_member_role_is_invalid () {
        // given
        fail_case_set_up();

        // when
        ExtractableResponse<Response> createResponse = create_company_request(COMPANY_NAME, COMPANY_DESCRIPTION,
            userId, token);
        // then
        check_company_create_fail(createResponse);
    }


    public static void check_company_create_fail(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }


}
