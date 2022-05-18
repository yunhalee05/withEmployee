package com.yunhalee.withEmployee.company.dto;

import com.yunhalee.withEmployee.user.domain.User;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CompanyCeoResponse {

    private Integer id;
    private String name;
    private String email;
    private List<String> companies;

    private CompanyCeoResponse(User ceo) {
        this.id = ceo.getId();
        this.name = ceo.getName();
        this.email = ceo.getEmail();
        this.companies = ceo.getCompanyNames();
    }

    public static CompanyCeoResponse of(User ceo){
        return new CompanyCeoResponse(ceo);
    }
}
