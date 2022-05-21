package com.yunhalee.withEmployee.company.dto;

import com.yunhalee.withEmployee.company.domain.Company;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimpleCompanyResponse {

    private Integer id;
    private String name;
    private String ceo;

    private SimpleCompanyResponse(Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.ceo = company.getCeoName();
    }

    public static SimpleCompanyResponse of(Company company) {
        return new SimpleCompanyResponse(company);
    }
}
