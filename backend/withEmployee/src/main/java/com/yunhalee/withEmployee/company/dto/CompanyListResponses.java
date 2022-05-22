package com.yunhalee.withEmployee.company.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CompanyListResponses {

    private Long totalElement;
    private Integer totalPage;
    private List<CompanyListResponse> companies;

    public CompanyListResponses(Long totalElement, Integer totalPage, List<CompanyListResponse> companies) {
        this.totalElement = totalElement;
        this.totalPage = totalPage;
        this.companies = companies;
    }

    public static CompanyListResponses of(Long totalElement, Integer totalPage, List<CompanyListResponse> companies) {
        return new CompanyListResponses(totalElement, totalPage, companies);
    }
}
