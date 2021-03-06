package com.yunhalee.withEmployee.company.dto;

import com.yunhalee.withEmployee.company.domain.Company;
import com.yunhalee.withEmployee.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class CompanyRequest {

    private String name;
    private String description;
    private Integer ceoId;

    public CompanyRequest(String name, String description, Integer ceoId) {
        this.name = name;
        this.description = description;
        this.ceoId = ceoId;
    }

    public Company toCompany(User ceo) {
        return Company.of(this.name, this.description, ceo);
    }

    public Company toCompany() {
        return Company.builder()
            .name(this.name)
            .description(this.description).build();
    }
}
