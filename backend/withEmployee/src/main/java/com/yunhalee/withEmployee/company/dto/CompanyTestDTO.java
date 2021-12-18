package com.yunhalee.withEmployee.company.dto;

import java.util.List;

public class CompanyTestDTO {

    private Long totalElement;

    private Integer totalPage;

    private List<CompanyListDTO> companies;

    public CompanyTestDTO(Long totalElement, Integer totalPage, List<CompanyListDTO> companies) {
        this.totalElement = totalElement;
        this.totalPage = totalPage;
        this.companies = companies;
    }
}
