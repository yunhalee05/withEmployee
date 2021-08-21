package com.yunhalee.withEmployee;

import com.yunhalee.withEmployee.Repository.CompanyRepository;
import com.yunhalee.withEmployee.entity.Company;
import com.yunhalee.withEmployee.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

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
        Company company = repo.findById(9).get();

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
        Iterable<Company> companies = repo.findAll();

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

}
