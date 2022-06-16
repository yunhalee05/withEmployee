package com.yunhalee.withEmployee.message.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.yunhalee.withEmployee.ApiTest;
import com.yunhalee.withEmployee.message.domain.MessageTest;
import com.yunhalee.withEmployee.message.dto.MessageResponse;
import com.yunhalee.withEmployee.message.dto.MessageResponses;
import com.yunhalee.withEmployee.message.dto.MessageUserResponse;
import com.yunhalee.withEmployee.user.domain.UserTest;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class MessageApiTest extends ApiTest {

    private static final String MESSAGE_REQUEST = "{\"content\":\"firstMessage\",\"imageUrl\":\"\",\"conversationId\":\"1\",\"userId\":\"1\"}";
    private static final MockMultipartFile MULTIPART_FILE = new MockMultipartFile("multipartFile",
        "image.png", "image/png", "image data".getBytes());

    @Test
    void find_all_messages_by_conversation_id() throws Exception {
        when(messageService.getMessages(any())).thenReturn(MessageResponses.of(Arrays.asList(
            MessageResponse.of(MessageTest.FIRST_MESSAGE, MessageUserResponse.of(UserTest.MEMBER)),
            MessageResponse.of(MessageTest.SECOND_MESSAGE, MessageUserResponse.of(UserTest.CEO))
        )));
        this.mockMvc.perform(get("/api/messages")
            .param("conversationId", "1")
            .header(HttpHeaders.AUTHORIZATION, "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("messages-get-all-by-conversation-id", messageResponsesFields()));
    }

    @Test
    void create() throws Exception {
        when(messageService.create(any(), any())).thenReturn(MessageResponse.of(MessageTest.FIRST_MESSAGE, MessageUserResponse.of(UserTest.MEMBER)));
        this.mockMvc.perform(post("/api/messages")
            .contentType(MediaTypes.HAL_JSON)
            .characterEncoding("utf-8")
            .content(MESSAGE_REQUEST)
            .header(HttpHeaders.AUTHORIZATION, "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("messages-create", messageResponseFields()));
    }

    @Test
    void delete() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/messages/{id}", 1)
            .header(HttpHeaders.AUTHORIZATION, "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andDo(document("messages-delete"));
    }

    @Test
    void save_image() throws Exception {
        when(messageService.saveImages(any())).thenReturn("message/firstConversation.jpeg");
        this.mockMvc.perform(multipart("/api/messages/image").file(MULTIPART_FILE)
            .accept(MediaType.MULTIPART_FORM_DATA))
            .andExpect(status().isOk())
            .andDo(document("messages-save-image"));
    }

    public static ResponseFieldsSnippet messageResponsesFields() {
        return responseFields(
            fieldWithPath("messages").description("user's conversations"),
            fieldWithPath("messages.[].id").description("message's id"),
            fieldWithPath("messages.[].imageUrl").description("message's imageUrl"),
            fieldWithPath("messages.[].content").description("message's content"),
            fieldWithPath("messages.[].conversationId").description("id of the conversation where the message belongs"),
            fieldWithPath("messages.[].user").description("message's writer"),
            fieldWithPath("messages.[].user.id").description("writer's id"),
            fieldWithPath("messages.[].user.name").description("writer's name"),
            fieldWithPath("messages.[].user.imageUrl").description("writer's imageUrl"),
            fieldWithPath("messages.[].createdAt").description("time when message was created")
        );
    }

    public static ResponseFieldsSnippet messageResponseFields() {
        return responseFields(
            fieldWithPath("id").description("message's id"),
            fieldWithPath("imageUrl").description("message's imageUrl"),
            fieldWithPath("content").description("message's content"),
            fieldWithPath("conversationId").description("id of the conversation where the message belongs"),
            fieldWithPath("user").description("message's writer"),
            fieldWithPath("user.id").description("writer's id"),
            fieldWithPath("user.name").description("writer's name"),
            fieldWithPath("user.imageUrl").description("writer's imageUrl"),
            fieldWithPath("createdAt").description("time when message was created")
        );
    }


}
