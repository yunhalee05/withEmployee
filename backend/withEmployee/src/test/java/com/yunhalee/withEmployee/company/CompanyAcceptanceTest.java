package com.yunhalee.withEmployee.company;

import static com.yunhalee.withEmployee.security.AuthAcceptanceTest.check_login_success;
import static com.yunhalee.withEmployee.security.AuthAcceptanceTest.login_request;
import static com.yunhalee.withEmployee.user.UserAcceptanceTest.CEO_EMAIL;
import static com.yunhalee.withEmployee.user.UserAcceptanceTest.EMAIL;
import static com.yunhalee.withEmployee.user.UserAcceptanceTest.PASSWORD;
import static com.yunhalee.withEmployee.user.UserAcceptanceTest.check_user_created;
import static com.yunhalee.withEmployee.user.UserAcceptanceTest.create_user_request;
import static org.assertj.core.api.Assertions.assertThat;

import com.yunhalee.withEmployee.AcceptanceTest;
import com.yunhalee.withEmployee.company.dto.CompanyListResponse;
import com.yunhalee.withEmployee.company.dto.CompanyRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;


public class CompanyAcceptanceTest extends AcceptanceTest {

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

        ExtractableResponse<Response> loginResponse = login_request(CEO_EMAIL, PASSWORD);
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

    public static ExtractableResponse<Response> create_company_request(String name, String description, Integer ceoId, String token) {
        CompanyRequest request = new CompanyRequest(name, description, ceoId);
        return create_request(request, "/companies", token);
    }

    public static void check_company_created(ExtractableResponse<Response> response) {
        check_ok_response(response);
    }

    public static ExtractableResponse<Response> find_company_request(ExtractableResponse<Response> response) {
        Integer id = (response.as(CompanyListResponse.class)).getId();
        return find_request("/companies/" + id, "");
    }

    public static void check_company_found(ExtractableResponse<Response> response) {
        check_ok_response(response);
    }

    public static ExtractableResponse<Response> find_companies_by_ceo_request(Integer ceoId) {
        return find_request("users/" + ceoId + "/companies", "");
    }

    public static void check_companies_by_ceo_found(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static ExtractableResponse<Response> find_companies_keyword_request(String keyword) {
        return find_request("/companies?keyword=" + keyword, "");
    }

    public static void check_companies_by_keyword_found(ExtractableResponse<Response> response) {
        check_ok_response(response);
    }

    public static ExtractableResponse<Response> find_companies_by_random_request() {
        return find_request("/companies/recommendation", "");
    }

    public static void check_company_by_random_found(ExtractableResponse<Response> response) {
        check_ok_response(response);
    }

    public static ExtractableResponse<Response> find_companies_request() {
        Integer page = 1;
        String sort = "createdAt";
        return find_request("/companies?page=" + page + "&sort=" + sort, "");
    }

    public static void check_companies_found(ExtractableResponse<Response> response) {
        check_ok_response(response);
    }

    public static ExtractableResponse<Response> update_company_request(ExtractableResponse<Response> response, String name, String description, Integer ceoId, String token) {
        Integer id = (response.as(CompanyListResponse.class)).getId();
        CompanyRequest request = new CompanyRequest(name, description, ceoId);
        return update_Request(request, "/companies/" + id, token);
    }

    public static void check_company_updated(ExtractableResponse<Response> response) {
        check_ok_response(response);
    }

    public static ExtractableResponse<Response> delete_company_request(ExtractableResponse<Response> response, String token) {
        Integer id = (response.as(CompanyListResponse.class)).getId();
        return delete_request("/companies/" + id, token);
    }

    public static void check_company_deleted(ExtractableResponse<Response> response) {
        check_delete_response(response);
    }


    @Test
    void create_company_by_member_role_is_invalid() {
        // given
        fail_case_set_up();

        // when
        ExtractableResponse<Response> createResponse = create_company_request(COMPANY_NAME,
            COMPANY_DESCRIPTION,
            userId, token);
        // then
        check_company_create_fail(createResponse);
    }


    public static void check_company_create_fail(ExtractableResponse<Response> response) {
        check_unauthorized_response(response);
    }


}
