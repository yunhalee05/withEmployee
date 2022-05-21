package com.yunhalee.withEmployee.company.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.yunhalee.withEmployee.MockBeans;
import com.yunhalee.withEmployee.company.domain.Company;
import com.yunhalee.withEmployee.company.domain.CompanyTest;
import com.yunhalee.withEmployee.company.dto.CompanyRequest;
import com.yunhalee.withEmployee.company.dto.CompanyResponse;
import com.yunhalee.withEmployee.company.exception.CompanyNameAlreadyInUseException;
import com.yunhalee.withEmployee.user.domain.User;
import com.yunhalee.withEmployee.user.domain.UserTest;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class CompanyServiceTest extends MockBeans {

    private static final String COMPANY_NAME_IS_ALREADY_IN_USE_EXCEPTION = "This company name is already in use.";
    private static final String NAME_IS_EMPTY_EXCEPTION = "Name could not be empty.";

    private final Integer ID = 1;
    private final String NAME = "testCompany";
    private final String DESCRIPTION = "This is TestCompany";

    @InjectMocks
    CompanyService companyService = new CompanyService(companyRepository, userService);

    private Company company;
    private CompanyRequest request;

    @BeforeEach
    void setUp() {
        company = new Company(ID,
            NAME,
            DESCRIPTION,
            UserTest.CEO);

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
        CompanyResponse response = companyService.create(request);

        //then
        checkEquals(response);
    }

    @Test
    void create_company_with_empty_name_is_invalid() {
        CompanyRequest companyRequest = new CompanyRequest("", DESCRIPTION, UserTest.CEO.getId());
        assertThatThrownBy(() -> companyService.create(companyRequest))
            .isInstanceOf(CompanyNameAlreadyInUseException.class)
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
        CompanyResponse response = companyService.update(ID, companyRequest);

        //then
        checkEquals(response, companyRequest, UserTest.SECOND_CEO);
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
        assertThatThrownBy(() -> companyService.update(ID, companyRequest))
            .isInstanceOf(CompanyNameAlreadyInUseException.class)
            .hasMessageContaining(COMPANY_NAME_IS_ALREADY_IN_USE_EXCEPTION);
    }


    private void checkEquals(CompanyResponse response) {
        assertThat(response.getId()).isEqualTo(company.getId());
        assertThat(response.getName()).isEqualTo(company.getName());
        assertThat(response.getDescription()).isEqualTo(company.getDescription());
        assertThat(response.getCeo().getId()).isEqualTo(UserTest.CEO.getId());
    }

    private void checkEquals(CompanyResponse response, CompanyRequest request, User ceo) {
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