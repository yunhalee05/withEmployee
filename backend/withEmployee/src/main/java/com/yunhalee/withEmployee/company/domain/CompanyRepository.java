package com.yunhalee.withEmployee.company.domain;

import com.yunhalee.withEmployee.team.domain.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    @Query(value = "SELECT DISTINCT c FROM Company c LEFT JOIN FETCH c.ceo e LEFT JOIN FETCH e.companies p LEFT JOIN FETCH c.teams t  LEFT JOIN FETCH t.users u")
    List<Company> findAllCompanies();


    @Query(value = "SELECT DISTINCT c FROM Company c LEFT JOIN FETCH c.ceo e LEFT JOIN FETCH e.companies p LEFT JOIN FETCH c.teams t  LEFT JOIN FETCH t.users u",
            countQuery = "SELECT count(DISTINCT c) FROM Company c")
    Page<Company> findAllCompanies(Pageable pageable);

    Company findByTeams(Team team);

    @Override
    @Query(value = "SELECT DISTINCT c FROM Company c LEFT JOIN FETCH c.ceo e LEFT JOIN FETCH c.teams t LEFT JOIN FETCH t.users u LEFT JOIN FETCH u.role WHERE c.id=:integer")
    Optional<Company> findById(Integer integer);

    @Query(value = "SELECT DISTINCT c FROM Company c LEFT JOIN FETCH c.ceo e LEFT JOIN FETCH c.teams t LEFT JOIN FETCH t.users u WHERE e.id=:id")
    List<Company> findByUserId(Integer id);

    Company findByName(String name);

    @Query(value = "SELECT DISTINCT c FROM Company c LEFT JOIN FETCH c.ceo e LEFT JOIN FETCH e.companies p LEFT JOIN FETCH c.teams t  LEFT JOIN FETCH t.users u ORDER BY RAND()")
    List<Company> findByRandom();

    @Query(value = "SELECT DISTINCT c FROM Company c LEFT JOIN FETCH c.ceo e LEFT JOIN FETCH e.companies p LEFT JOIN FETCH c.teams t  LEFT JOIN FETCH t.users u ORDER BY RAND()",
            countQuery = "SELECT count(DISTINCT c) FROM Company c ")
    Page<Company> findByRandom(Pageable pageable);

    @Query(value = "SELECT DISTINCT c FROM Company c LEFT JOIN FETCH c.ceo e LEFT JOIN FETCH c.teams t LEFT JOIN FETCH t.users u WHERE c.name LIKE %:keyword% OR e.name LIKE %:keyword%")
    List<Company> findByKeyword(String keyword);


}
