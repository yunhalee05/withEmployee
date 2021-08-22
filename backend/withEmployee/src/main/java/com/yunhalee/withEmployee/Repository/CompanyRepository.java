package com.yunhalee.withEmployee.Repository;

import com.yunhalee.withEmployee.entity.Company;
import com.yunhalee.withEmployee.entity.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Integer> {

    @Query(value = "SELECT DISTINCT c FROM Company c LEFT JOIN FETCH c.ceo e LEFT JOIN FETCH c.teams t  LEFT JOIN FETCH t.users u")
    List<Company> findAllCompanies();

    Company findByTeams(Team team);

    @Override
    @Query(value = "SELECT DISTINCT c FROM Company c LEFT JOIN FETCH c.ceo e LEFT JOIN FETCH c.teams t LEFT JOIN FETCH t.users u LEFT JOIN FETCH u.role WHERE c.id=:integer")
    Optional<Company> findById(Integer integer);

    Company findByName(String name);
}
