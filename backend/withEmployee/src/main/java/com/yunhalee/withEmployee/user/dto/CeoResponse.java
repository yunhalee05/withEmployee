package com.yunhalee.withEmployee.user.dto;

import com.yunhalee.withEmployee.user.domain.User;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CeoResponse {

    private Integer id;
    private String name;
    private String email;
    private String imageUrl;
    private List<String> companies;

    private CeoResponse(User ceo) {
        this.id = ceo.getId();
        this.name = ceo.getName();
        this.email = ceo.getEmail();
        this.imageUrl = ceo.getImageUrl();
        this.companies = ceo.getCompanyNames();
    }

    public static CeoResponse of(User ceo) {
        return new CeoResponse(ceo);
    }
}
