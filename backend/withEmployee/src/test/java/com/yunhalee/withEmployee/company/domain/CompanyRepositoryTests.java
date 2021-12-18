package com.yunhalee.withEmployee.company.domain;

import com.yunhalee.withEmployee.company.domain.CompanyRepository;
import com.yunhalee.withEmployee.company.dto.CompanyListDTO;
import com.yunhalee.withEmployee.company.domain.Company;
import com.yunhalee.withEmployee.team.domain.Team;
import com.yunhalee.withEmployee.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CompanyRepositoryTests {

    @Autowired
    private CompanyRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateCompany(){
        Company company = new Company("RG", "Make electronics");

        Company savedcompany = repo.save(company);

        assertThat(savedcompany.getId()).isGreaterThan(0);
    }

    @Test
    public void testUpdateCompany(){
        Company company = repo.findById(3).get();

        company.setDescription("Make phone, tv, electronics");

        repo.save(company);

        System.out.println(company);
    }

    @Test
    public void testDeleteCompany(){
        Company company = repo.findById(11).get();

        repo.delete(company);
    }

    @Test
    public void testFindByTeam(){
        Team team = entityManager.find(Team.class,1);
        Company company = repo.findByTeams(team);

        System.out.println(company);
        assertThat(company.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllCompany(){
        Iterable<Company> companies = repo.findAllCompanies();

        companies.forEach(c-> System.out.println(c));
    }

    @Test
    public void testListAllCompanyWithTeam(){
        List<Company> companyList = repo.findAllCompanies();

        System.out.println(companyList);
    }

    @Test
    public void testListCompanyById(){
        Company company = repo.findById(1).get();
        System.out.println(company);
    }

    @Test
    public void testListCompanyByName(){
        Company company = repo.findByName("Picshare");
        System.out.println(company);
    }

    @Test
    public void testUpdateCompanyUser(){
        User user = entityManager.find(User.class, 27);

        Company company = repo.findById(5).get();

        company.setCeo(user);

        System.out.println(company);

    }

    @Test
    public void testGetCompanyByCeoId(){
        List<Company> company = repo.findByUserId(34);
        System.out.println(company);
    }

    @Test
    public void testGetByRandom(){
        List<Company> companies = repo.findByRandom();
        companies.forEach(company -> System.out.println(company));
    }

    @Test
    public void testListByRandomByPagination(){
        int pageNumber = 0;
        int pageSize = 4;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Company> page = repo.findByRandom(pageable);
        List<Company> companies = page.getContent();

        companies.forEach(company -> System.out.println(company.getCeo()));

        List<CompanyListDTO> companyListDTOS = new ArrayList<>();

        companies.forEach(company -> companyListDTOS.add(new CompanyListDTO(company)));

        companyListDTOS.forEach(companyListDTO -> System.out.println(companyListDTO));

    }

    @Test
    public void testGetByKeyword(){
        String keyword = "ceo";

        List<Company> companies = repo.findByKeyword(keyword);
        companies.forEach(company -> System.out.println(company));
    }

}
