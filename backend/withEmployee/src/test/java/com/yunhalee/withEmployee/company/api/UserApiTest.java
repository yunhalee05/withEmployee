package com.yunhalee.withEmployee.company.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestPartBody;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.yunhalee.withEmployee.ApiTest;
import com.yunhalee.withEmployee.company.domain.CompanyTest;
import com.yunhalee.withEmployee.security.jwt.UserTokenResponse;
import com.yunhalee.withEmployee.team.domain.TeamTest;
import com.yunhalee.withEmployee.user.domain.UserTest;
import com.yunhalee.withEmployee.user.dto.SimpleUserResponse;
import com.yunhalee.withEmployee.user.dto.UserCompanyResponse;
import com.yunhalee.withEmployee.user.dto.UserResponse;
import com.yunhalee.withEmployee.user.dto.UserResponses;
import com.yunhalee.withEmployee.user.dto.UserTeamResponse;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

public class UserApiTest extends ApiTest {

    private static final MockMultipartFile multipartFile = new MockMultipartFile("multipartFile",
        "image.png", "image/png", "image data".getBytes());
    private static final MockMultipartFile userRequest = new MockMultipartFile(
        "userRequest",
        "",
        "application/json",
        "{\"name\": \"testMember\",\"email\": \"testMember@example.com\",\"password\": \"123456\",\"description\": \"This is testMember\",\"phoneNumber\": \"01000000000\",\"ceo\": \"false\"}"
            .getBytes());
    private static final UserTokenResponse userTokenResponse = UserTokenResponse
        .of(SimpleUserResponse.of(UserTest.MEMBER), "token");


    @Test
    void create() throws Exception {
        this.mockMvc.perform(multipart("/api/users").file(multipartFile).file(userRequest)
            .accept(MediaType.MULTIPART_FORM_DATA))
            .andExpect(status().isCreated())
            .andDo(print())
            .andDo(document("user-create", requestPartBody("userRequest")));
    }

    @Test
    void update() throws Exception {
        when(userService.update(any(), any(), any())).thenReturn(userTokenResponse);
        this.mockMvc.perform(multipart("/api/users/{id}", 1L).file(multipartFile).file(userRequest)
            .header(HttpHeaders.AUTHORIZATION, "Bearer token")
            .accept(MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("user-update",
                responseFields(
                    fieldWithPath("user").description("user information response"),
                    fieldWithPath("user.id").description("user id"),
                    fieldWithPath("user.name").description("updated user name"),
                    fieldWithPath("user.email").description("updated user email"),
                    fieldWithPath("user.description").description("updated user description"),
                    fieldWithPath("user.phoneNumber").description("updated user phoneNumber"),
                    fieldWithPath("user.imageUrl").description("updated user image url"),
                    fieldWithPath("user.role").description("user role"),
                    fieldWithPath("token").description("token")
                )
            ));
    }

    @Test
    void find_all_users_by_page() throws Exception {
        when(userService.getAll(any())).thenReturn(UserResponses.of(
            2L,
            1,
            Arrays.asList(UserResponse.of(UserTest.MEMBER,
                Arrays.asList(UserTeamResponse.of(TeamTest.FIRST_TEAM)),
                Arrays.asList(UserCompanyResponse.of(CompanyTest.FIRST_COMPANY))),
                UserResponse.of(UserTest.CEO,
                    Arrays.asList(UserTeamResponse.of(TeamTest.FIRST_TEAM),
                        UserTeamResponse.of(TeamTest.SECOND_TEAM)),
                    Arrays.asList(UserCompanyResponse.of(CompanyTest.FIRST_COMPANY),
                        UserCompanyResponse.of(CompanyTest.SECOND_COMPANY))))));
        this.mockMvc.perform(get("/api/users")
            .param("page", "1")
            .header(HttpHeaders.AUTHORIZATION, "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("user-get-all",
                responseFields(
                    fieldWithPath("totalElement").description("total number of users"),
                    fieldWithPath("totalPage").description("total page of users"),
                    fieldWithPath("users").description("found user's information"),
                    fieldWithPath("users.[].id").description("user id"),
                    fieldWithPath("users.[].name").description("user name"),
                    fieldWithPath("users.[].email").description("user email"),
                    fieldWithPath("users.[].description").description("user description"),
                    fieldWithPath("users.[].phoneNumber").description("user phoneNumber"),
                    fieldWithPath("users.[].imageUrl").description("user image url"),
                    fieldWithPath("users.[].role").description("user role"),
                    fieldWithPath("users.[].teams").description("teams where user belong"),
                    fieldWithPath("users.[].teams.[].id").description("team id"),
                    fieldWithPath("users.[].teams.[].name").description("team name"),
                    fieldWithPath("users.[].teams.[].company")
                        .description("the company where the team belong"),
                    fieldWithPath("users.[].companies").description("companies where user works"),
                    fieldWithPath("users.[].companies.[].id").description("company id"),
                    fieldWithPath("users.[].companies.[].name").description("company name")
                )));
    }


    @Test
    void find_user() throws Exception {
        when(userService.get(any())).thenReturn(
            UserResponse.of(UserTest.MEMBER,
                Arrays.asList(UserTeamResponse.of(TeamTest.FIRST_TEAM)),
                Arrays.asList(UserCompanyResponse.of(CompanyTest.FIRST_COMPANY))));
        this.mockMvc.perform(get("/api/users/{id}", 1L)
            .header(HttpHeaders.AUTHORIZATION, "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("user-get",
                responseFields(
                    fieldWithPath("id").description("user id"),
                    fieldWithPath("name").description("user name"),
                    fieldWithPath("email").description("user email"),
                    fieldWithPath("description").description("user description"),
                    fieldWithPath("phoneNumber").description("user phoneNumber"),
                    fieldWithPath("imageUrl").description("user image url"),
                    fieldWithPath("role").description("user role"),
                    fieldWithPath("teams").description("teams where user belong"),
                    fieldWithPath("teams.[].id").description("team id"),
                    fieldWithPath("teams.[].name").description("team name"),
                    fieldWithPath("teams.[].company")
                        .description("the company where the team belong"),
                    fieldWithPath("companies").description("companies where user works"),
                    fieldWithPath("companies.[].id").description("company id"),
                    fieldWithPath("companies.[].name").description("company name")
                )));
    }

}
