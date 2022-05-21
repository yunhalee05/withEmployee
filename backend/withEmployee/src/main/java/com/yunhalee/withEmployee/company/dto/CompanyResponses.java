package com.yunhalee.withEmployee.company.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CompanyResponses {

    private Long totalElement;
    private Integer totalPage;
    private List<CompanyResponse> companies;

    private CompanyResponses(Long totalElement, Integer totalPage, List<CompanyResponse> companies) {
        this.totalElement = totalElement;
        this.totalPage = totalPage;
        this.companies = companies;
    }

    public static CompanyResponses of(Long totalElement, Integer totalPage, List<CompanyResponse> companies) {
        return new CompanyResponses(totalElement, totalPage, companies);
    }
}
