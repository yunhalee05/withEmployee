package com.yunhalee.withEmployee.company.dto;


import com.yunhalee.withEmployee.company.domain.Company;
import com.yunhalee.withEmployee.user.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompanyListDTO {

    private Integer id;

    private String name;

    private String description;

    private Integer totalNumber;

    private CompanyCeo ceo;




    public CompanyListDTO(Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.description = company.getDescription();
        this.totalNumber = company.getTotalNumber();
        this.ceo = new CompanyCeo(company.getCeo());
    }


    @Getter
    static class CompanyCeo{
        private Integer id;
        private String name;
        private String email;
        private String imageUrl;
        private List<String> companies;

        public CompanyCeo(User ceo){
            this.id = ceo.getId();
            this.name = ceo.getName();
            this.email = ceo.getEmail();
            this.imageUrl = ceo.getImageUrl();
            this.companies = ceo.getCompanyNames();
        }
    }
}