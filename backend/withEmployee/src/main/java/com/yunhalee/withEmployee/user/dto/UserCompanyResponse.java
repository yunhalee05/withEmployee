package com.yunhalee.withEmployee.user.dto;


import com.yunhalee.withEmployee.company.domain.Company;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCompanyResponse {

    private Integer id;
    private String name;

    private UserCompanyResponse(Company company) {
        this.id = company.getId();
        this.name = company.getName();
    }

    public static UserCompanyResponse of(Company company) {
        return new UserCompanyResponse(company);
    }
}
