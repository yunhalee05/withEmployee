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
        this.id = id;
        this.name = name;
        this.ceo = ceo;
    }

    public static SimpleCompanyResponse of(Company company) {
        return new SimpleCompanyResponse(company);
    }
}
