package com.yunhalee.withEmployee.dto;

import com.yunhalee.withEmployee.entity.Company;
import com.yunhalee.withEmployee.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CompanyListByPageDTO {


    private Long totalElement;

    private Integer totalPage;

    private List<CompanyDTO> companies;

    public CompanyListByPageDTO(Long totalElement, Integer totalPage, List<Company> companies) {
        this.totalElement = totalElement;
        this.totalPage = totalPage;
        this.companies = CompanyDTO.CompanyList(companies);
    }


    @Getter
    static class CompanyDTO {

        private Integer id;

        private String name;

        private String description;

        private Integer totalNumber;

        private String ceo;


        static List<CompanyDTO> CompanyList(List<Company> companies) {
            List<CompanyDTO> companyDTOS = new ArrayList<>();
            companies.forEach(company -> companyDTOS.add(new CompanyDTO(company)));
            return companyDTOS;
        }

        public CompanyDTO(Company company) {
            this.id = company.getId();
            this.name = company.getName();
            this.description = company.getDescription();
            this.totalNumber = company.getTotalNumber();
            this.ceo = company.getCeo().getName();
        }
    }
}
