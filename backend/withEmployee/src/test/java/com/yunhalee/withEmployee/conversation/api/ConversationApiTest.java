package com.yunhalee.withEmployee.conversation.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.yunhalee.withEmployee.ApiTest;
import com.yunhalee.withEmployee.company.domain.CompanyTest;
import com.yunhalee.withEmployee.conversation.domain.ConversationTest;
import com.yunhalee.withEmployee.conversation.dto.ConversationResponse;
import com.yunhalee.withEmployee.conversation.dto.ConversationResponses;
import com.yunhalee.withEmployee.user.domain.Role;
import com.yunhalee.withEmployee.user.domain.User;
import com.yunhalee.withEmployee.user.domain.UserTest;
import com.yunhalee.withEmployee.user.dto.CeoResponse;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class ConversationApiTest extends ApiTest {

    private static final String CONVERSATION_REQUEST = "{\"text\":\"firstConversation\",\"imageUrl\":\"message/firstConversation.jpeg\",\"userEmails\":[\"testMember@example.com\",\"testCEO@example.com\"],\"teamMember\":\"true\",\"sameCompany\":\"false\",\"otherCompany\":\"false\"}";


    private ConversationResponses conversationResponses;
    private ConversationResponse conversationResponse;


    @BeforeEach
    void setUp() {
        User ceo = User.builder()
            .id(2)
            .name("testCEO")
            .email("testCEO@example.com")
            .password("123456")
            .imageUrl("testUploadFolder/testCEOImage.png")
            .description("This is testCEO")
            .phoneNumber("01000000000")
            .role(Role.CEO).build();
        ceo.addCompany(CompanyTest.FIRST_COMPANY);
        ceo.addCompany(CompanyTest.SECOND_COMPANY);
        conversationResponse = ConversationResponse.of(ConversationTest.FIRST_CONVERSATION, Arrays.asList(CeoResponse.of(UserTest.MEMBER), CeoResponse.of(ceo)));
        conversationResponses = ConversationResponses.of(Arrays.asList(
            conversationResponse,
            ConversationResponse.of(ConversationTest.SECOND_CONVERSATION, Arrays.asList(CeoResponse.of(UserTest.MEMBER), CeoResponse.of(UserTest.LEADER)))
        ));
    }

    @Test
    void find_all_conversation_by_user_id() throws Exception {
        when(conversationService.listAll(any())).thenReturn(conversationResponses);
        this.mockMvc.perform(get("/api/conversations")
            .param("userId", "1")
            .header(HttpHeaders.AUTHORIZATION, "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("conversations-get-all-by-user-id", conversationResponsesFields()));
    }

    @Test
    void create() throws Exception {
        when(conversationService.create(any())).thenReturn(conversationResponse);
        this.mockMvc.perform(post("/api/conversations")
            .contentType(MediaTypes.HAL_JSON)
            .characterEncoding("utf-8")
            .content(CONVERSATION_REQUEST)
            .header(HttpHeaders.AUTHORIZATION, "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("conversation-create", conversationResponseFields()));
    }

    @Test
    void delete() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/conversations/{id}", 1)
            .header(HttpHeaders.AUTHORIZATION, "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andDo(document("conversation-delete"));
    }




    public static ResponseFieldsSnippet conversationResponsesFields() {
        return responseFields(
            fieldWithPath("conversations").description("user's conversations"),
            fieldWithPath("conversations.[].id").description("conversation's id"),
            fieldWithPath("conversations.[].text").description("conversation's last text"),
            fieldWithPath("conversations.[].imageUrl").description("conversation's last imageUrl"),
            fieldWithPath("conversations.[].users").description("company members"),
            fieldWithPath("conversations.[].users.[].id").description("member's id"),
            fieldWithPath("conversations.[].users.[].name").description("member's name"),
            fieldWithPath("conversations.[].users.[].email").description("member's email"),
            fieldWithPath("conversations.[].users.[].imageUrl").description("member's imageUrl"),
            fieldWithPath("conversations.[].users.[].companies").description("companies which this member runs"),
            fieldWithPath("conversations.[].teamMember").description("this member is in the same team with user"),
            fieldWithPath("conversations.[].sameCompany").description("this member is in the same company with user"),
            fieldWithPath("conversations.[].otherCompany").description("this member is in the other company with user")
        );
    }

    public static ResponseFieldsSnippet conversationResponseFields() {
        return responseFields(
            fieldWithPath("id").description("conversation's id"),
            fieldWithPath("text").description("conversation's last text"),
            fieldWithPath("imageUrl").description("conversation's last imageUrl"),
            fieldWithPath("users").description("company members"),
            fieldWithPath("users.[].id").description("member's id"),
            fieldWithPath("users.[].name").description("member's name"),
            fieldWithPath("users.[].email").description("member's email"),
            fieldWithPath("users.[].imageUrl").description("member's imageUrl"),
            fieldWithPath("users.[].companies").description("companies which this member runs"),
            fieldWithPath("teamMember").description("this member is in the same team with user"),
            fieldWithPath("sameCompany").description("this member is in the same company with user"),
            fieldWithPath("otherCompany").description("this member is in the other company with user")
        );
    }
}
