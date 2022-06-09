package com.yunhalee.withEmployee.message;

import static com.yunhalee.withEmployee.conversation.ConversationAcceptanceTest.CONVERSATION_IMAGE_URL;
import static com.yunhalee.withEmployee.conversation.ConversationAcceptanceTest.CONVERSATION_TEXT;
import static com.yunhalee.withEmployee.conversation.ConversationAcceptanceTest.IS_OTHER_COMPANY;
import static com.yunhalee.withEmployee.conversation.ConversationAcceptanceTest.IS_SAME_COMPANY;
import static com.yunhalee.withEmployee.conversation.ConversationAcceptanceTest.IS_TEAM_COMPANY;
import static com.yunhalee.withEmployee.conversation.ConversationAcceptanceTest.check_conversation_created;
import static com.yunhalee.withEmployee.conversation.ConversationAcceptanceTest.create_conversation_request;
import static com.yunhalee.withEmployee.security.AuthAcceptanceTest.check_login_success;
import static com.yunhalee.withEmployee.security.AuthAcceptanceTest.login_request;
import static com.yunhalee.withEmployee.user.UserAcceptanceTest.CEO_EMAIL;
import static com.yunhalee.withEmployee.user.UserAcceptanceTest.EMAIL;
import static com.yunhalee.withEmployee.user.UserAcceptanceTest.PASSWORD;
import static com.yunhalee.withEmployee.user.UserAcceptanceTest.check_user_created;
import static com.yunhalee.withEmployee.user.UserAcceptanceTest.create_user_request;

import com.yunhalee.withEmployee.AcceptanceTest;
import com.yunhalee.withEmployee.conversation.dto.ConversationResponse;
import com.yunhalee.withEmployee.message.dto.MessageRequest;
import com.yunhalee.withEmployee.message.dto.MessageResponse;
import com.yunhalee.withEmployee.security.jwt.UserTokenResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.io.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class MessageAcceptanceTest extends AcceptanceTest {

    private static final String MESSAGE_CONTENT = "testMessage";
    private static final String MESSAGE_IMAGE_URL = "testMessage/image.jpeg";

    private Integer userId;
    private String userToken;
    private Integer conversationId;

    @BeforeEach
    public void setUp() {
        super.setUp();
        ExtractableResponse<Response> createCeoResponse = create_user_request(imageFile, ceoRequestFile);
        check_user_created(createCeoResponse);

        ExtractableResponse<Response> loginCeoResponse = login_request(CEO_EMAIL, PASSWORD);
        check_login_success(loginCeoResponse);
        String ceoEmail = (loginCeoResponse.as(UserTokenResponse.class)).getUser().getEmail();

        ExtractableResponse<Response> createResponse = create_user_request(imageFile, requestFile);
        check_user_created(createResponse);

        ExtractableResponse<Response> loginResponse = login_request(EMAIL, PASSWORD);
        check_login_success(loginResponse);
        userId = Integer.parseInt(createResponse.header("Location").split("/")[2]);
        userToken = loginResponse.jsonPath().getString("token");
        String userEmail = (loginResponse.as(UserTokenResponse.class)).getUser().getEmail();

        ExtractableResponse<Response> createConversationResponse = create_conversation_request(CONVERSATION_TEXT, CONVERSATION_IMAGE_URL, IS_TEAM_COMPANY, IS_OTHER_COMPANY, IS_SAME_COMPANY, userToken, userEmail, ceoEmail);
        check_conversation_created(createConversationResponse);
        conversationId = (createConversationResponse.as(ConversationResponse.class)).getId();
    }


    @Test
    public void manage_message() {
        // when
        ExtractableResponse<Response> createResponse = create_message_request(MESSAGE_CONTENT, MESSAGE_IMAGE_URL, conversationId, userId, userToken);
        // then
        check_message_created(createResponse);

        // when
        ExtractableResponse<Response> createImageResponse = create_message_image_request(imageFile, userToken);
        // then
        check_message_image_created(createImageResponse);

        // when
        ExtractableResponse<Response> findResponse = find_messages_by_conversation_request(conversationId, userToken);
        // then
        check_messages_by_conversation_found(findResponse);

        // when
        ExtractableResponse<Response> deleteResponse = delete_message_request(createResponse, userToken);
        // then
        check_messages_deleted(deleteResponse);
    }

    private ExtractableResponse<Response> create_message_request(String content, String imageUrl, Integer conversationId, Integer userId, String token) {
        MessageRequest request = new MessageRequest(content, imageUrl, conversationId, userId);
        return create_request(request, "/messages", token);
    }


    public static void check_message_created(ExtractableResponse<Response> response) {
        check_ok_response(response);
    }

    private ExtractableResponse<Response> create_message_image_request(File imageFile, String token) {
        return RestAssured
            .given().log().all()
            .header("Authorization", "Bearer " + token)
            .contentType("multipart/form-data")
            .multiPart("multipartFile", imageFile, "image/jpeg")
            .when().post("/api/messages/image")
            .then().log().all()
            .extract();
    }

    public static void check_message_image_created(ExtractableResponse<Response> response) {
        check_ok_response(response);
    }

    private ExtractableResponse<Response> find_messages_by_conversation_request(
        Integer conversationId, String token) {
        return find_request("/messages?conversationId=" + conversationId, token);
    }


    private void check_messages_by_conversation_found(ExtractableResponse<Response> response) {
        check_ok_response(response);
    }

    private ExtractableResponse<Response> delete_message_request(ExtractableResponse<Response> response, String token) {
        Integer id = (response.as(MessageResponse.class)).getId();
        return delete_request("/messages/" + id, token);
    }

    public static void check_messages_deleted(ExtractableResponse<Response> response) {
        check_delete_response(response);
    }


}
