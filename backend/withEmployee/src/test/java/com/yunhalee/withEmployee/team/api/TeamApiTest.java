package com.yunhalee.withEmployee.team.api;

import static com.yunhalee.withEmployee.user.api.UserApiTest.simpleUserResponseFields;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.yunhalee.withEmployee.ApiTest;
import com.yunhalee.withEmployee.company.dto.SimpleCompanyResponse;
import com.yunhalee.withEmployee.team.domain.TeamTest;
import com.yunhalee.withEmployee.team.dto.SimpleTeamResponse;
import com.yunhalee.withEmployee.team.dto.SimpleTeamResponses;
import com.yunhalee.withEmployee.team.dto.TeamResponse;
import com.yunhalee.withEmployee.team.dto.TeamResponses;
import com.yunhalee.withEmployee.user.domain.UserTest;
import com.yunhalee.withEmployee.user.dto.SimpleUserResponse;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class TeamApiTest extends ApiTest {

    private static final TeamResponses TEAM_RESPONSES = TeamResponses.of(
        2L,
        1,
        Arrays.asList(TeamResponse.of(
            TeamTest.FIRST_TEAM, Arrays.asList(
                SimpleUserResponse.of(UserTest.MEMBER),
                SimpleUserResponse.of(UserTest.CEO))),
            TeamResponse.of(TeamTest.SECOND_TEAM, Arrays.asList(
                SimpleUserResponse.of(UserTest.LEADER),
                SimpleUserResponse.of(UserTest.SECOND_CEO)))));

    private static final TeamResponse TEAM_RESPONSE = TeamResponse.of(TeamTest.FIRST_TEAM, Arrays.asList(
            SimpleUserResponse.of(UserTest.MEMBER),
            SimpleUserResponse.of(UserTest.CEO)));

    private static final SimpleTeamResponses SIMPLE_TEAM_RESPONSES = SimpleTeamResponses.of(Arrays.asList(
            SimpleTeamResponse.of(TeamTest.FIRST_TEAM, SimpleCompanyResponse.of(TeamTest.FIRST_TEAM.getCompany())),
            SimpleTeamResponse.of(TeamTest.SECOND_TEAM, SimpleCompanyResponse.of(TeamTest.SECOND_TEAM.getCompany()))));

    private static final String TEAM_REQUEST = "{\"name\": \"testFirstTeam\",\"companyId\": \"1\"}";


    @Test
    void find_all_users_by_page() throws Exception {
        when(teamService.listAll(any())).thenReturn(TEAM_RESPONSES);
        this.mockMvc.perform(get("/api/teams")
            .param("page", "1")
            .header(HttpHeaders.AUTHORIZATION, "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("team-get-all", teamResponsesFields()));
    }


    @Test
    void find_team_by_id() throws Exception {
        when(teamService.getById(any(), any())).thenReturn(TEAM_RESPONSE);
        this.mockMvc.perform(get("/api/teams/{id}", 1L)
            .header(HttpHeaders.AUTHORIZATION, "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("team-get-by-id", teamResponseFields()));
    }

    @Test
    void find_teams_by_user_id() throws Exception {
        when(teamService.getByUserId(any())).thenReturn(SIMPLE_TEAM_RESPONSES);
        this.mockMvc.perform(get("/api/users/{userId}/teams", 1L)
            .header(HttpHeaders.AUTHORIZATION, "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("team-get-all-by-user-id", simpleTeamResponsesFields()));
    }

    @Test
    void create() throws Exception {
        when(teamService.create(any())).thenReturn(TEAM_RESPONSE);
        this.mockMvc.perform(post("/api/teams")
            .contentType(MediaTypes.HAL_JSON)
            .characterEncoding("utf-8")
            .content(TEAM_REQUEST)
            .header(HttpHeaders.AUTHORIZATION, "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("team-create", teamResponseFields()));
    }

    @Test
    void update() throws Exception {
        when(teamService.update(any(), any(), any())).thenReturn(TEAM_RESPONSE);
        this.mockMvc.perform(post("/api/teams/{id}", 1L)
            .contentType(MediaTypes.HAL_JSON)
            .characterEncoding("utf-8")
            .content(TEAM_REQUEST)
            .header(HttpHeaders.AUTHORIZATION, "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("team-update", teamResponseFields()));
    }

    @Test
    void delete() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/teams/{id}", 1L)
            .header(HttpHeaders.AUTHORIZATION, "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andDo(document("team-delete"));
    }

    @Test
    void add_member() throws Exception {
        when(teamService.addMember(any(), any(), any()))
            .thenReturn(SimpleUserResponse.of(UserTest.MEMBER));
        this.mockMvc.perform(post("/api/teams/{id}", 1L)
            .param("email", "testMember@example.com")
            .header(HttpHeaders.AUTHORIZATION, "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("team-add-member", simpleUserResponseFields()));
    }

    @Test
    void subtract_member() throws Exception {
        when(teamService.addMember(any(), any(), any()))
            .thenReturn(SimpleUserResponse.of(UserTest.MEMBER));
        this.mockMvc.perform(post("/api/teams/{id}", 1L)
            .param("userId", "1")
            .header(HttpHeaders.AUTHORIZATION, "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andDo(document("team-subtract-member"));
    }

    public static ResponseFieldsSnippet teamResponsesFields() {
        return responseFields(
            fieldWithPath("totalElement").description("total number of users"),
            fieldWithPath("totalPage").description("total page of users"),
            fieldWithPath("teams").description("found team's information"),
            fieldWithPath("teams.[].id").description("team id"),
            fieldWithPath("teams.[].name").description("team name"),
            fieldWithPath("teams.[].company").description("the company where the team belongs"),
            fieldWithPath("teams.[].companyId").description("the company's id"),
            fieldWithPath("teams.[].ceo").description("the company's ceo id"),
            fieldWithPath("teams.[].users").description("team members"),
            fieldWithPath("teams.[].users.[].id").description("member id"),
            fieldWithPath("teams.[].users.[].name").description("member name"),
            fieldWithPath("teams.[].users.[].email").description("member email"),
            fieldWithPath("teams.[].users.[].description").description("member description"),
            fieldWithPath("teams.[].users.[].phoneNumber").description("member phoneNumber"),
            fieldWithPath("teams.[].users.[].imageUrl").description("member image url"),
            fieldWithPath("teams.[].users.[].role").description("member role")
        );
    }

    public static ResponseFieldsSnippet simpleTeamResponsesFields() {
        return responseFields(
            fieldWithPath("teams").description("found team's information"),
            fieldWithPath("teams.[].id").description("team id"),
            fieldWithPath("teams.[].name").description("team name"),
            fieldWithPath("teams.[].totalNumber").description("the number of team members"),
            fieldWithPath("teams.[].company").description("the company where team belongs"),
            fieldWithPath("teams.[].company.id").description("company id"),
            fieldWithPath("teams.[].company.name").description("company name"),
            fieldWithPath("teams.[].company.ceo").description("company ceo's name")
        );
    }

    public static ResponseFieldsSnippet teamResponseFields() {
        return responseFields(
            fieldWithPath("id").description("team id"),
            fieldWithPath("name").description("team name"),
            fieldWithPath("company").description("the company where the team belongs"),
            fieldWithPath("companyId").description("the company's id"),
            fieldWithPath("ceo").description("the company's ceo id"),
            fieldWithPath("users").description("team members"),
            fieldWithPath("users.[].id").description("member id"),
            fieldWithPath("users.[].name").description("member name"),
            fieldWithPath("users.[].email").description("member email"),
            fieldWithPath("users.[].description").description("member description"),
            fieldWithPath("users.[].phoneNumber").description("member phoneNumber"),
            fieldWithPath("users.[].imageUrl").description("member image url"),
            fieldWithPath("users.[].role").description("member role")
        );
    }

}
