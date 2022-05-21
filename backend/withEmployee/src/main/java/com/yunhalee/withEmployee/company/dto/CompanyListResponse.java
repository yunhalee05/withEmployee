package com.yunhalee.withEmployee.company.dto;

import com.yunhalee.withEmployee.company.domain.Company;
import com.yunhalee.withEmployee.user.dto.CeoResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CompanyListResponse {

    private Integer id;
    private String name;
    private String description;
    private Integer totalNumber;
    private CeoResponse ceo;

    private CompanyListResponse(Company company, CeoResponse ceo) {
        this.id = company.getId();
        this.name = company.getName();
        this.description = company.getDescription();
        this.totalNumber = company.getTotalNumber();
        this.ceo = ceo;
    }

    public static CompanyListResponse of(Company company, CeoResponse ceo){
        return new CompanyListResponse(company, ceo);
    }
}
