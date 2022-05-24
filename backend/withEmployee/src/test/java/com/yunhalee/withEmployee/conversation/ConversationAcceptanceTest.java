package com.yunhalee.withEmployee.conversation;

import static com.yunhalee.withEmployee.security.AuthAcceptanceTest.check_login_success;
import static com.yunhalee.withEmployee.security.AuthAcceptanceTest.login_request;
import static com.yunhalee.withEmployee.user.UserAcceptanceTest.CEO_EMAIL;
import static com.yunhalee.withEmployee.user.UserAcceptanceTest.EMAIL;
import static com.yunhalee.withEmployee.user.UserAcceptanceTest.PASSWORD;
import static com.yunhalee.withEmployee.user.UserAcceptanceTest.check_user_created;
import static com.yunhalee.withEmployee.user.UserAcceptanceTest.create_user_request;
import static org.assertj.core.api.Assertions.assertThat;

import com.yunhalee.withEmployee.AcceptanceTest;
import com.yunhalee.withEmployee.conversation.dto.ConversationRequest;
import com.yunhalee.withEmployee.conversation.dto.ConversationResponse;
import com.yunhalee.withEmployee.security.jwt.UserTokenResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.io.File;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ConversationAcceptanceTest extends AcceptanceTest {

    public static final String CONVERSATION_TEXT = "testConversation";
    public static final String CONVERSATION_IMAGE_URL = "testConversation/image.jpeg";
    public static final boolean IS_TEAM_COMPANY = true;
    public static final boolean IS_SAME_COMPANY = false;
    public static final boolean IS_OTHER_COMPANY = false;

    private Integer userId;
    private String userToken;
    private String ceoEmail;
    private String userEmail;

    @BeforeEach
    public void setUp() {
        super.setUp();
        ExtractableResponse<Response> createCeoResponse = create_user_request(imageFile, ceoRequestFile);
        check_user_created(createCeoResponse);

        ExtractableResponse<Response> loginCeoResponse = login_request(CEO_EMAIL, PASSWORD);
        check_login_success(loginCeoResponse);
        ceoEmail = (loginCeoResponse.as(UserTokenResponse.class)).getUser().getEmail();

        ExtractableResponse<Response> createResponse = create_user_request(imageFile, requestFile);
        check_user_created(createResponse);

        ExtractableResponse<Response> loginResponse = login_request(EMAIL, PASSWORD);
        check_login_success(loginResponse);
        userId = Integer.parseInt(createResponse.header("Location").split("/")[2]);
        userToken = loginResponse.jsonPath().getString("token");
        userEmail = (loginResponse.as(UserTokenResponse.class)).getUser().getEmail();
    }

    @Test
    public void manage_conversation() {
        // when
        ExtractableResponse<Response> createResponse = create_conversation_request(CONVERSATION_TEXT, CONVERSATION_IMAGE_URL, IS_TEAM_COMPANY, IS_OTHER_COMPANY, IS_SAME_COMPANY, userToken, userEmail, ceoEmail);
        // then
        check_conversation_created(createResponse);

        // when
        ExtractableResponse<Response> findResponse = find_conversation_request(userId, userToken);
        // then
        check_conversation_found(findResponse);

        // when
        ExtractableResponse<Response> deleteResponse = delete_conversation_request(createResponse, userToken);
        // then
        check_conversation_deleted(deleteResponse);
    }

    public static ExtractableResponse<Response> create_conversation_request(String text, String imageUrl, boolean isTeamCompany, boolean isSameCompany, boolean isOtherCompany, String token, String... userEmails) {
        ConversationRequest request = new ConversationRequest(text, imageUrl, isTeamCompany, isSameCompany, isOtherCompany, Arrays.asList(userEmails));
        return create_request(request, "/conversations", token);
    }

    public static void check_conversation_created(ExtractableResponse<Response> response) {
        check_ok_response(response);
    }

    public static ExtractableResponse<Response> find_conversation_request(Integer userId, String token) {
        return find_request("/conversations?userId=" + userId, token);
    }

    public static void check_conversation_found(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static ExtractableResponse<Response> delete_conversation_request(ExtractableResponse<Response> response, String token) {
        Integer id = (response.as(ConversationResponse.class)).getId();
        return delete_request("/conversations/" + id, token);
    }


    public static void check_conversation_deleted(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }


}
