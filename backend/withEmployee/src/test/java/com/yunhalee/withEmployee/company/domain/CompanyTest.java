package com.yunhalee.withEmployee.company.domain;

import com.yunhalee.withEmployee.user.domain.User;
import com.yunhalee.withEmployee.user.domain.UserTest;

public class CompanyTest {

    public static final Company FIRST_COMPANY = create(1, "firstCompany", "This is first company");
    public static final Company SECOND_COMPANY = create(2, "secondCompany", "This is second company");
    public static final Company THIRD_COMPANY = create(3, "thirdCompany", "This is third company");
    public static final Company FOURTH_COMPANY = create(4, "fourthCompany", "This is fourth company");
    public static final Company FIFTH_COMPANY = create(5, "fifthCompany", "This is fifth company");

    private static Company create(Integer id, String name, String description) {
        return Company.builder()
            .id(id)
            .name(name)
            .description(description)
            .ceo(UserTest.CEO).build();
    }

}
