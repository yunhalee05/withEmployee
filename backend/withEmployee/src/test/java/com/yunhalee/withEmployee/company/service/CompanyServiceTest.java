package com.yunhalee.withEmployee.company.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.yunhalee.withEmployee.MockBeans;
import com.yunhalee.withEmployee.company.domain.Company;
import com.yunhalee.withEmployee.company.dto.CompanyRequest;
import com.yunhalee.withEmployee.company.dto.CompanyResponse;
import com.yunhalee.withEmployee.company.exception.CompanyNameAlreadyInUserException;
import com.yunhalee.withEmployee.user.domain.User;
import com.yunhalee.withEmployee.user.domain.UserTest;
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

    private static final String COMPANY_NAME_IS_ALREADY_IN_USER_EXCEPTION = "This company name is already in use.";

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
    void create_company_with_already_in_use_name_is_invalid() {
        when(companyRepository.existsByName(anyString())).thenReturn(true);
        assertThatThrownBy(() -> companyService.create(request))
            .isInstanceOf(CompanyNameAlreadyInUserException.class)
            .hasMessageContaining(COMPANY_NAME_IS_ALREADY_IN_USER_EXCEPTION);
    }


    private void checkEquals(CompanyResponse response)  {
        assertThat(response.getId()).isEqualTo(company.getId());
        assertThat(response.getName()).isEqualTo(company.getName());
        assertThat(response.getDescription()).isEqualTo(company.getDescription());
        assertThat(response.getCeo().getId()).isEqualTo(UserTest.CEO.getId());
    }



}