package com.yunhalee.withEmployee.team;

import static com.yunhalee.withEmployee.security.AuthAcceptanceTest.check_login_success;
import static com.yunhalee.withEmployee.security.AuthAcceptanceTest.login_request;
import static com.yunhalee.withEmployee.user.UserAcceptanceTest.CEO_EMAIL;
import static com.yunhalee.withEmployee.user.UserAcceptanceTest.EMAIL;
import static com.yunhalee.withEmployee.user.UserAcceptanceTest.PASSWORD;
import static com.yunhalee.withEmployee.user.UserAcceptanceTest.check_user_created;
import static com.yunhalee.withEmployee.user.UserAcceptanceTest.create_user_request;
import static com.yunhalee.withEmployee.company.CompanyAcceptanceTest.*;

import com.yunhalee.withEmployee.AcceptanceTest;
import com.yunhalee.withEmployee.company.dto.CompanyListResponse;
import com.yunhalee.withEmployee.team.dto.TeamRequest;
import com.yunhalee.withEmployee.team.dto.TeamResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.io.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import static org.assertj.core.api.Assertions.assertThat;


public class TeamAcceptanceTest extends AcceptanceTest {

    public final File imageFile = new File(getClass().getClassLoader().getResource("test.jpeg").getPath());
    public final File ceoRequestFile = new File(getClass().getClassLoader().getResource("ceoUserRequest.txt").getPath());
    public final File requestFile = new File(getClass().getClassLoader().getResource("userRequest.txt").getPath());
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
        check_team_created(updateResponse);

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
        ExtractableResponse<Response> subtractMemberResponse = subtract_team_member_request(createResponse,userId, ceoToken);
        // then
        check_team_member_subtracted(subtractMemberResponse);

        // when
        ExtractableResponse<Response> deleteResponse = delete_team_request(createResponse,ceoToken);
        // then
        check_team_deleted(deleteResponse);

    }

    public static ExtractableResponse<Response> create_team_request(String name, Integer companyId, String token) {
        TeamRequest request = new TeamRequest(name, companyId);
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/teams")
            .then().log().all()
            .extract();
    }

    public static void check_team_created(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static ExtractableResponse<Response> find_team_request(ExtractableResponse<Response> response, String token) {
        Integer teamId = (response.as(TeamResponse.class)).getId();
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/teams/" + teamId )
            .then().log().all()
            .extract();
    }

    public static void check_team_found(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static ExtractableResponse<Response> find_teams_request(Integer page, String token) {
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/teams?page=" + page)
            .then().log().all()
            .extract();
    }

    public static void check_teams_found(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static ExtractableResponse<Response> find_team_by_user_request(Integer userId, String token) {
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/users/" + userId + "/teams")
            .then().log().all()
            .extract();
    }

    public static void check_team_found_by_user(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static ExtractableResponse<Response> update_team_request(ExtractableResponse<Response> response, String name, Integer companyId, String token) {
        Integer teamId = (response.as(TeamResponse.class)).getId();
        TeamRequest request = new TeamRequest(name, companyId);
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/teams/" + teamId)
            .then().log().all()
            .extract();
    }

    public static void check_team_updated(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static ExtractableResponse<Response> add_team_member_request(ExtractableResponse<Response> response, String email, String token) {
        Integer teamId = (response.as(TeamResponse.class)).getId();
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/teams/" + teamId + "?email=" + email)
            .then().log().all()
            .extract();
    }

    public static void check_team_member_added(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static ExtractableResponse<Response> subtract_team_member_request(ExtractableResponse<Response> response, Integer userId, String token) {
        Integer teamId = (response.as(TeamResponse.class)).getId();
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
//            .body(request)
            .when().post("/teams/" + teamId + "?userId=" + userId)
            .then().log().all()
            .extract();
    }

    public static void check_team_member_subtracted(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }



    public static ExtractableResponse<Response> delete_team_request(ExtractableResponse<Response> response, String token) {
        Integer id = (response.as(TeamResponse.class)).getId();
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().delete("/teams/" + id)
            .then().log().all()
            .extract();
    }

    public static void check_team_deleted(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }






}
