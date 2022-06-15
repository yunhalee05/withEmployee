package com.yunhalee.withEmployee.security.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.yunhalee.withEmployee.ApiTest;
import com.yunhalee.withEmployee.security.jwt.JwtUserDetails;
import com.yunhalee.withEmployee.security.jwt.UserTokenResponse;
import com.yunhalee.withEmployee.user.domain.UserTest;
import com.yunhalee.withEmployee.user.dto.SimpleUserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

public class AuthApiTest extends ApiTest {

    private static final UserTokenResponse USER_TOKEN_RESPONSE = UserTokenResponse
        .of(SimpleUserResponse.of(UserTest.MEMBER), "token");

    @Test
    void login_with_email_and_password() throws Exception {
        when(jwtUserDetailsService.loadUserByUsername(any())).thenReturn(new JwtUserDetails(UserTest.MEMBER));
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        when(jwtUserDetailsService.login(any())).thenReturn(USER_TOKEN_RESPONSE);
        String jwtRequest = "{\"username\": \"testMember@example.com\",\"password\": \"123456\"}";
        this.mockMvc.perform(post("/api/login")
            .contentType(MediaTypes.HAL_JSON)
            .characterEncoding("utf-8")
            .content(jwtRequest)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("auth-login-with-email-and-password", userTokenResponseFields()));
    }

    @Test
    void login_with_token() throws Exception {
        when(jwtUserDetailsService.loginWithToken(any())).thenReturn(USER_TOKEN_RESPONSE);
        this.mockMvc.perform(get("/api/login")
            .param("token", "token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("auth-login-with-token", userTokenResponseFields()));
    }

    public static ResponseFieldsSnippet userTokenResponseFields() {
        return responseFields(
            fieldWithPath("user").description("user information response"),
            fieldWithPath("user.id").description("user id"),
            fieldWithPath("user.name").description("updated user name"),
            fieldWithPath("user.email").description("updated user email"),
            fieldWithPath("user.description").description("updated user description"),
            fieldWithPath("user.phoneNumber").description("updated user phoneNumber"),
            fieldWithPath("user.imageUrl").description("updated user image url"),
            fieldWithPath("user.role").description("user role"),
            fieldWithPath("token").description("token")
        );
    }
}
