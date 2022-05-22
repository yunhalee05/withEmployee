package com.yunhalee.withEmployee.company.domain;

import com.yunhalee.withEmployee.user.domain.User;
import com.yunhalee.withEmployee.user.domain.UserRepository;
import com.yunhalee.withEmployee.user.domain.UserTest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CompanyRepositoryTest {

    public static final int COMPANY_PER_PAGE = 3;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    UserRepository userRepository;

    private Company firstCompany;
    private Company secondCompany;
    private Company thirdCompany;
    private Company fourthCompany;
    private Company fifthCompany;
    private User ceo;


    @Before
    public void setUp() {
        ceo = userRepository.save(UserTest.CEO);
        firstCompany = save(CompanyTest.FIRST_COMPANY.getName(), CompanyTest.FIRST_COMPANY.getDescription(), ceo);
        secondCompany = save(CompanyTest.SECOND_COMPANY.getName(), CompanyTest.SECOND_COMPANY.getDescription(), ceo);
        thirdCompany = save(CompanyTest.THIRD_COMPANY.getName(), CompanyTest.THIRD_COMPANY.getDescription(), ceo);
        fourthCompany = save(CompanyTest.FOURTH_COMPANY.getName(), CompanyTest.FOURTH_COMPANY.getDescription(), ceo);
        fifthCompany = save(CompanyTest.FIFTH_COMPANY.getName(), CompanyTest.FIFTH_COMPANY.getDescription(), ceo);

    }

    private Company save(String name, String description, User ceo) {
        return companyRepository.save(Company.builder()
            .name(name)
            .description(description)
            .ceo(ceo).build());
    }

    @Test
    public void find_all_companies_by_page_and_sort() {
        Pageable pageable = PageRequest.of(0, COMPANY_PER_PAGE, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Company> page = companyRepository.findCompanies(pageable);
        assertThat(page.getContent().equals(Arrays.asList(fifthCompany, fourthCompany, thirdCompany))).isTrue();
    }

    @Test
    public void find_by_companyId() {
        Company company = companyRepository.findByCompanyId(firstCompany.getId()).get();
        assertThat(company).isEqualTo(firstCompany);
    }

    @Test
    public void find_by_ceoId() {
        List<Company> companies = companyRepository.findByUserId(ceo.getId());
        assertThat(companies.equals(Arrays.asList(firstCompany, secondCompany, thirdCompany, fourthCompany, fifthCompany))).isTrue();
    }

    @Test
    public void find_random_companies() {
        Pageable pageable = PageRequest.of(0, COMPANY_PER_PAGE);
        Page<Company> page = companyRepository.findByRandom(pageable);
        assertThat(page.getContent().size()).isEqualTo(COMPANY_PER_PAGE);
    }

    @Test
    public void find_companies_with_keyword() {
        String keyword = "company";
        List<Company> companies = companyRepository.findByKeyword(keyword);
        for (Company company : companies) {
            assertThat(company.getName().toLowerCase(Locale.ROOT)).contains(keyword);
        }
    }


}
