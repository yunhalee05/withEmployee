package com.yunhalee.withEmployee.team;

import static com.yunhalee.withEmployee.company.CompanyAcceptanceTest.COMPANY_DESCRIPTION;
import static com.yunhalee.withEmployee.company.CompanyAcceptanceTest.COMPANY_NAME;
import static com.yunhalee.withEmployee.company.CompanyAcceptanceTest.check_company_created;
import static com.yunhalee.withEmployee.company.CompanyAcceptanceTest.create_company_request;
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
import com.yunhalee.withEmployee.team.dto.TeamRequest;
import com.yunhalee.withEmployee.team.dto.TeamResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;


public class TeamAcceptanceTest extends AcceptanceTest {

    public static final String TEAM_NAME = "testTeam";
    public static final String NEW_TEAM_NAME = "testUpdateTeam";

    private Integer ceoId;
    private Integer userId;
    private String ceoToken;
    private String userToken;
    private Integer companyId;

    @BeforeEach
    public void setUp() {
        super.setUp();
        ExtractableResponse<Response> createCeoResponse = create_user_request(imageFile, ceoRequestFile);
        check_user_created(createCeoResponse);
        ceoId = Integer.parseInt(createCeoResponse.header("Location").split("/")[2]);

        ExtractableResponse<Response> loginCeoResponse = login_request(CEO_EMAIL, PASSWORD);
        check_login_success(loginCeoResponse);
        ceoToken = loginCeoResponse.jsonPath().getString("token");

        ExtractableResponse<Response> createResponse = create_user_request(imageFile, requestFile);
        check_user_created(createResponse);
        userId = Integer.parseInt(createResponse.header("Location").split("/")[2]);

        ExtractableResponse<Response> loginResponse = login_request(EMAIL, PASSWORD);
        check_login_success(loginResponse);
        userToken = loginResponse.jsonPath().getString("token");

        ExtractableResponse<Response> companyCreateResponse = create_company_request(COMPANY_NAME, COMPANY_DESCRIPTION, ceoId, ceoToken);
        check_company_created(companyCreateResponse);
        companyId = (companyCreateResponse.as(CompanyListResponse.class)).getId();
    }


    @Test
    void manage_team() {
        // when
        ExtractableResponse<Response> createResponse = create_team_request(TEAM_NAME, companyId, ceoToken);
        // then
        check_team_created(createResponse);

        // when
        ExtractableResponse<Response> updateResponse = update_team_request(createResponse, NEW_TEAM_NAME, companyId, ceoToken);
        // then
        check_team_updated(updateResponse);

        // when
        ExtractableResponse<Response> addMemberResponse = add_team_member_request(createResponse, EMAIL, ceoToken);
        // then
        check_team_member_added(addMemberResponse);

        // when
        ExtractableResponse<Response> findTeamResponse = find_team_request(createResponse, userToken);
        // then
        check_team_found(findTeamResponse);

        // when
        ExtractableResponse<Response> findTeamsResponse = find_teams_request(1, userToken);
        // then
        check_teams_found(findTeamsResponse);

        // when
        ExtractableResponse<Response> findTeamByUserResponse = find_team_by_user_request(userId, userToken);
        // then
        check_team_found_by_user(findTeamByUserResponse);

        // when
        ExtractableResponse<Response> subtractMemberResponse = subtract_team_member_request(createResponse, userId, ceoToken);
        // then
        check_team_member_subtracted(subtractMemberResponse);

        // when
        ExtractableResponse<Response> deleteResponse = delete_team_request(createResponse, ceoToken);
        // then
        check_team_deleted(deleteResponse);

    }

    public static ExtractableResponse<Response> create_team_request(String name, Integer companyId, String token) {
        TeamRequest request = new TeamRequest(name, companyId);
        return create_request(request, "/teams", token);
    }

    public static void check_team_created(ExtractableResponse<Response> response) {
        check_ok_response(response);
    }

    public static ExtractableResponse<Response> find_team_request(ExtractableResponse<Response> response, String token) {
        Integer teamId = (response.as(TeamResponse.class)).getId();
        return find_request("/teams/" + teamId, token);
    }

    public static void check_team_found(ExtractableResponse<Response> response) {
        check_ok_response(response);
    }

    public static ExtractableResponse<Response> find_teams_request(Integer page, String token) {
        return find_request("/teams?page=" + page, token);
    }

    public static void check_teams_found(ExtractableResponse<Response> response) {
        check_ok_response(response);
    }

    public static ExtractableResponse<Response> find_team_by_user_request(Integer userId, String token) {
        return find_request("/users/" + userId + "/teams", token);
    }

    public static void check_team_found_by_user(ExtractableResponse<Response> response) {
        check_ok_response(response);
    }

    public static ExtractableResponse<Response> update_team_request(ExtractableResponse<Response> response, String name, Integer companyId, String token) {
        Integer teamId = (response.as(TeamResponse.class)).getId();
        TeamRequest request = new TeamRequest(name, companyId);
        return update_Request(request, "/teams/" + teamId, token);
    }

    public static void check_team_updated(ExtractableResponse<Response> response) {
        check_ok_response(response);
    }

    public static ExtractableResponse<Response> add_team_member_request(ExtractableResponse<Response> response, String email, String token) {
        Integer teamId = (response.as(TeamResponse.class)).getId();
        return update_Request("/teams/" + teamId + "?email=" + email, token);
    }

    public static void check_team_member_added(ExtractableResponse<Response> response) {
        check_ok_response(response);
    }

    public static ExtractableResponse<Response> subtract_team_member_request(ExtractableResponse<Response> response, Integer userId, String token) {
        Integer teamId = (response.as(TeamResponse.class)).getId();
        return update_Request("/teams/" + teamId + "?userId=" + userId, token);
    }

    public static void check_team_member_subtracted(ExtractableResponse<Response> response) {
        check_delete_response(response);
    }


    public static ExtractableResponse<Response> delete_team_request(ExtractableResponse<Response> response, String token) {
        Integer id = (response.as(TeamResponse.class)).getId();
        return delete_request("/teams/" + id, token);
    }

    public static void check_team_deleted(ExtractableResponse<Response> response) {
        check_delete_response(response);
    }


}
