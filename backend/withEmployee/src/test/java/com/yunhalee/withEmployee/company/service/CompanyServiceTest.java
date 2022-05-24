package com.yunhalee.withEmployee.company.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.yunhalee.withEmployee.MockBeans;
import com.yunhalee.withEmployee.common.exception.exceptions.AuthException;
import com.yunhalee.withEmployee.company.domain.Company;
import com.yunhalee.withEmployee.company.domain.CompanyTest;
import com.yunhalee.withEmployee.company.dto.CompanyListResponse;
import com.yunhalee.withEmployee.company.dto.CompanyRequest;
import com.yunhalee.withEmployee.company.exception.CompanyNameAlreadyInUseException;
import com.yunhalee.withEmployee.company.exception.CompanyNameEmptyException;
import com.yunhalee.withEmployee.security.jwt.LoginUser;
import com.yunhalee.withEmployee.user.domain.User;
import com.yunhalee.withEmployee.user.domain.UserTest;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class CompanyServiceTest extends MockBeans {

    private static final String COMPANY_NAME_IS_ALREADY_IN_USE_EXCEPTION = "This company name is already in use.";
    private static final String NAME_IS_EMPTY_EXCEPTION = "Name could not be empty.";
    private static final String USER_NOT_AUTHORIZED_EXCEPTION = "User don't have authorization.";

    private final Integer ID = 1;
    private final String NAME = "testCompany";
    private final String DESCRIPTION = "This is TestCompany";

    @InjectMocks
    CompanyService companyService = new CompanyService(companyRepository, userService);

    private Company company;
    private CompanyRequest request;

    @BeforeEach
    void setUp() {
        company = Company.builder()
            .id(ID)
            .name(NAME)
            .description(DESCRIPTION)
            .ceo(UserTest.CEO).build();

        request = new CompanyRequest(NAME,
            DESCRIPTION,
            UserTest.CEO.getId());
    }

    @Test
    void create_company() {
        // when
        when(companyRepository.existsByName(anyString())).thenReturn(false);
        when(companyRepository.save(any())).thenReturn(company);
        when(userService.findUserById(anyInt())).thenReturn(UserTest.CEO);
        CompanyListResponse response = companyService.create(request);

        //then
        checkEquals(response);
    }

    @Test
    void create_company_with_empty_name_is_invalid() {
        CompanyRequest companyRequest = new CompanyRequest("", DESCRIPTION, UserTest.CEO.getId());
        assertThatThrownBy(() -> companyService.create(companyRequest))
            .isInstanceOf(CompanyNameEmptyException.class)
            .hasMessageContaining(NAME_IS_EMPTY_EXCEPTION);
    }

    @Test
    void create_company_with_already_in_use_name_is_invalid() {
        when(companyRepository.existsByName(anyString())).thenReturn(true);
        assertThatThrownBy(() -> companyService.create(request))
            .isInstanceOf(CompanyNameAlreadyInUseException.class)
            .hasMessageContaining(COMPANY_NAME_IS_ALREADY_IN_USE_EXCEPTION);
    }

    @Test
    void update_company() {
        // given
        CompanyRequest companyRequest = new CompanyRequest(CompanyTest.SECOND_COMPANY.getName(),
            CompanyTest.SECOND_COMPANY.getDescription(),
            UserTest.SECOND_CEO.getId());

        // when
        when(companyRepository.existsByName(anyString())).thenReturn(false);
        when(companyRepository.findByCompanyId(any())).thenReturn(Optional.of(company));
        when(userService.findUserById(anyInt())).thenReturn(UserTest.SECOND_CEO);
        CompanyListResponse response = companyService.update(LoginUser.of(UserTest.SECOND_CEO), ID, companyRequest);

        //then
        checkEquals(response, companyRequest, UserTest.SECOND_CEO);
    }

    @DisplayName("대표자나 관리자가 아닌 다른 사용자가 수정을 요청할 경우 권한없음 예외를 발생시킨다.")
    @Test
    void update_with_not_ceo_level_user() {
        // given
        CompanyRequest companyRequest = new CompanyRequest(CompanyTest.SECOND_COMPANY.getName(),
            CompanyTest.SECOND_COMPANY.getDescription(),
            UserTest.SECOND_CEO.getId());

        // when
        assertThatThrownBy(() -> companyService.update(LoginUser.of(UserTest.CEO), ID, companyRequest))
            .isInstanceOf(AuthException.class)
            .hasMessageContaining(USER_NOT_AUTHORIZED_EXCEPTION);
    }


    @Test
    void update_company_with_already_existing_name_is_invalid() {
        // given
        CompanyRequest companyRequest = new CompanyRequest(CompanyTest.SECOND_COMPANY.getName(),
            CompanyTest.SECOND_COMPANY.getDescription(),
            UserTest.SECOND_CEO.getId());
        // when
        when(companyRepository.existsByName(anyString())).thenReturn(true);
        when(companyRepository.findByName(anyString()))
            .thenReturn(Optional.of(CompanyTest.SECOND_COMPANY));
        assertThatThrownBy(() -> companyService.update(LoginUser.of(UserTest.SECOND_CEO), ID, companyRequest))
            .isInstanceOf(CompanyNameAlreadyInUseException.class)
            .hasMessageContaining(COMPANY_NAME_IS_ALREADY_IN_USE_EXCEPTION);
    }


    private void checkEquals(CompanyListResponse response) {
        assertThat(response.getId()).isEqualTo(company.getId());
        assertThat(response.getName()).isEqualTo(company.getName());
        assertThat(response.getDescription()).isEqualTo(company.getDescription());
        assertThat(response.getCeo().getId()).isEqualTo(UserTest.CEO.getId());
    }

    private void checkEquals(CompanyListResponse response, CompanyRequest request, User ceo) {
        assertThat(response.getId()).isEqualTo(ID);
        assertThat(response.getName()).isEqualTo(request.getName());
        assertThat(response.getDescription()).isEqualTo(request.getDescription());
        assertThat(response.getCeo().getId()).isEqualTo(ceo.getId());
        assertThat(response.getCeo().getName()).isEqualTo(ceo.getName());
        assertThat(response.getCeo().getEmail()).isEqualTo(ceo.getEmail());
        assertThat(response.getCeo().getImageUrl()).isEqualTo(ceo.getImageUrl());
        assertThat(response.getCeo().getCompanies().size()).isEqualTo(1);
        assertThat(response.getCeo().getCompanies().get(0)).isEqualTo(request.getName());
    }


}