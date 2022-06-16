package com.yunhalee.withEmployee.company.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.yunhalee.withEmployee.ApiTest;
import com.yunhalee.withEmployee.company.domain.Company;
import com.yunhalee.withEmployee.company.domain.CompanyTest;
import com.yunhalee.withEmployee.company.dto.CompanyListResponse;
import com.yunhalee.withEmployee.company.dto.CompanyListResponses;
import com.yunhalee.withEmployee.company.dto.CompanyResponse;
import com.yunhalee.withEmployee.company.dto.SimpleCompanyResponse;
import com.yunhalee.withEmployee.team.domain.TeamTest;
import com.yunhalee.withEmployee.team.dto.SimpleTeamResponse;
import com.yunhalee.withEmployee.user.domain.Role;
import com.yunhalee.withEmployee.user.domain.User;
import com.yunhalee.withEmployee.user.domain.UserTest;
import com.yunhalee.withEmployee.user.dto.CeoResponse;
import com.yunhalee.withEmployee.user.dto.MemberResponse;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class CompanyApiTest extends ApiTest {

    private static final String COMPANY_REQUEST = "{\"name\": \"firstCompany\",\"description\": \"This is first company\",\"ceoId\": \"2\"}";

    private CompanyListResponses companyListResponses;
    private CompanyListResponse companyListResponse;
    private CompanyResponse companyResponse;

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
        companyListResponse = CompanyListResponse.of(CompanyTest.FIRST_COMPANY, CeoResponse.of(ceo));
        companyListResponses = CompanyListResponses.of(
            2L,
            1,
            Arrays.asList(companyListResponse,
                CompanyListResponse.of(CompanyTest.SECOND_COMPANY, CeoResponse.of(ceo))));
        companyResponse = CompanyResponse.of(CompanyTest.FIRST_COMPANY,
            Arrays.asList(SimpleTeamResponse.of(TeamTest.FIRST_TEAM, SimpleCompanyResponse.of(CompanyTest.FIRST_COMPANY)), SimpleTeamResponse.of(TeamTest.SECOND_TEAM, SimpleCompanyResponse.of(CompanyTest.FIRST_COMPANY))),
            CeoResponse.of(ceo),
            Arrays.asList(MemberResponse.of(UserTest.MEMBER), MemberResponse.of(ceo)));
    }

    @Test
    void find_all_users_by_page_and_sort() throws Exception {
        when(companyService.getCompaniesByPage(any(), any())).thenReturn(companyListResponses);
        this.mockMvc.perform(get("/api/companies")
            .param("page", "1")
            .param("sort", "createdAt")
            .header(HttpHeaders.AUTHORIZATION, "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("companies-get-all-by-page-sort", companyListResponsesFields()));
    }

    @Test
    void find_companies_by_random() throws Exception {
        when(companyService.companyRecommendation()).thenReturn(companyListResponses);
        this.mockMvc.perform(get("/api/companies/recommendation")
            .header(HttpHeaders.AUTHORIZATION, "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("companies-get-all-recommendation", companyListResponsesFields()));
    }

    @Test
    void find_companies_by_keyword() throws Exception {
        when(companyService.searchCompany(any())).thenReturn(companyListResponses);
        this.mockMvc.perform(get("/api/companies")
            .param("keyword", "Company")
            .header(HttpHeaders.AUTHORIZATION, "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("companies-get-all-by-keyword", companyListResponsesFields()));
    }

    @Test
    void find_companies_by_ceo_id() throws Exception {
        when(companyService.findByCeoId(any())).thenReturn(companyListResponses);
        this.mockMvc.perform(get("/api/users/{ceoId}/companies", 1)
            .header(HttpHeaders.AUTHORIZATION, "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("companies-get-all-by-ceo-id", companyListResponsesFields()));
    }

    @Test
    void find_company_by_id() throws Exception {
        when(companyService.companyResponse(any())).thenReturn(companyResponse);
        this.mockMvc.perform(get("/api/companies/{id}", 1)
            .header(HttpHeaders.AUTHORIZATION, "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("companies-get-by-id", companyResponseFields()));
    }

    @Test
    void create() throws Exception {
        when(companyService.create(any())).thenReturn(companyListResponse);
        this.mockMvc.perform(post("/api/companies")
            .contentType(MediaTypes.HAL_JSON)
            .characterEncoding("utf-8")
            .content(COMPANY_REQUEST)
            .header(HttpHeaders.AUTHORIZATION, "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("company-create", companyListResponseFields()));
    }

    @Test
    void update() throws Exception {
        when(companyService.update(any(), any(), any())).thenReturn(companyListResponse);
        this.mockMvc.perform(post("/api/companies/{id}", 1)
            .contentType(MediaTypes.HAL_JSON)
            .characterEncoding("utf-8")
            .content(COMPANY_REQUEST)
            .header(HttpHeaders.AUTHORIZATION, "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("company-update", companyListResponseFields()));
    }

    @Test
    void delete() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/companies/{id}", 1)
            .header(HttpHeaders.AUTHORIZATION, "Bearer token")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andDo(document("company-delete"));
    }



    public static ResponseFieldsSnippet companyListResponsesFields() {
        return responseFields(
            fieldWithPath("totalElement").description("total number of companies"),
            fieldWithPath("totalPage").description("total page of companies"),
            fieldWithPath("companies").description("found company's information"),
            fieldWithPath("companies.[].id").description("company id"),
            fieldWithPath("companies.[].name").description("company name"),
            fieldWithPath("companies.[].description").description("company description"),
            fieldWithPath("companies.[].totalNumber").description("the number of company's members"),
            fieldWithPath("companies.[].ceo").description("company ceo"),
            fieldWithPath("companies.[].ceo.id").description("company ceo's id"),
            fieldWithPath("companies.[].ceo.name").description("company ceo's name"),
            fieldWithPath("companies.[].ceo.email").description("company ceo's email"),
            fieldWithPath("companies.[].ceo.imageUrl").description("company ceo's imageUrl"),
            fieldWithPath("companies.[].ceo.companies").description("companies which this company ceo runs")
        );
    }

    public static ResponseFieldsSnippet companyListResponseFields() {
        return responseFields(
            fieldWithPath("id").description("company id"),
            fieldWithPath("name").description("company name"),
            fieldWithPath("description").description("company description"),
            fieldWithPath("totalNumber").description("the number of company's members"),
            fieldWithPath("ceo").description("company ceo"),
            fieldWithPath("ceo.id").description("company ceo's id"),
            fieldWithPath("ceo.name").description("company ceo's name"),
            fieldWithPath("ceo.email").description("company ceo's email"),
            fieldWithPath("ceo.imageUrl").description("company ceo's imageUrl"),
            fieldWithPath("ceo.companies").description("companies which this company ceo runs")
        );
    }

    public static ResponseFieldsSnippet companyResponseFields() {
        return responseFields(
            fieldWithPath("id").description("company id"),
            fieldWithPath("name").description("company name"),
            fieldWithPath("description").description("company description"),
            fieldWithPath("teams").description("company's team"),
            fieldWithPath("teams.[].id").description("team's id"),
            fieldWithPath("teams.[].name").description("team's name"),
            fieldWithPath("teams.[].totalNumber").description("the number of team members"),
            fieldWithPath("teams.[].company").description("the company where the team belongs"),
            fieldWithPath("teams.[].company.id").description("company id"),
            fieldWithPath("teams.[].company.name").description("company name"),
            fieldWithPath("teams.[].company.ceo").description("company ceo"),
            fieldWithPath("ceo").description("company ceo"),
            fieldWithPath("ceo.id").description("company ceo's id"),
            fieldWithPath("ceo.name").description("company ceo's name"),
            fieldWithPath("ceo.email").description("company ceo's email"),
            fieldWithPath("ceo.imageUrl").description("company ceo's imageUrl"),
            fieldWithPath("ceo.companies").description("companies which this company ceo runs"),
            fieldWithPath("members").description("company members"),
            fieldWithPath("members.[].id").description("member's id"),
            fieldWithPath("members.[].name").description("member's name"),
            fieldWithPath("members.[].email").description("member's email"),
            fieldWithPath("members.[].role").description("member's role")
        );
    }
}
