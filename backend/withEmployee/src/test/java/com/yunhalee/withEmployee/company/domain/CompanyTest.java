package com.yunhalee.withEmployee.company.domain;

import com.yunhalee.withEmployee.user.domain.UserTest;

public class CompanyTest {

    public static final Company FIRST_COMPANY = new Company(1,
        "firstCompany",
        "This is first company",
        UserTest.CEO);

    public static final Company SECOND_COMPANY = new Company(2,
        "secondCompany",
        "This is second company",
        UserTest.CEO);

    public static final Company THIRD_COMPANY = new Company(3,
        "thirdCompany",
        "This is third company",
        UserTest.CEO);

    public static final Company FOURTH_COMPANY = new Company(4,
        "fourthCompany",
        "This is fourth company",
        UserTest.CEO);

    public static final Company FIFTH_COMPANY = new Company(5,
        "fifthCompany",
        "This is fifth company",
        UserTest.CEO);

}
