package com.yunhalee.withEmployee.team.domain;

import com.yunhalee.withEmployee.company.domain.Company;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public
interface TeamRepository extends JpaRepository<Team, Integer> {

//    boolean existsByNameAndCompany(String name, Company company);
//
//    Optional<Team> findByNameAndCompany(String name, Company company);

    @Query(value = "SELECT DISTINCT t FROM Team t INNER JOIN FETCH t.company c LEFT JOIN FETCH t.users u")
    List<Team> findAllTeams();

    @Query(value = "SELECT DISTINCT t FROM Team t INNER JOIN FETCH t.company c LEFT JOIN FETCH t.users u",
        countQuery = "SELECT count(DISTINCT t) FROM Team t")
    Page<Team> findAllTeams(Pageable pageable);


    @Query(value = "SELECT DISTINCT t FROM Team t LEFT JOIN FETCH t.users u INNER JOIN FETCH t.company c  WHERE t.id=:id")
    Optional<Team> findByTeamId(Integer id);

    @Query(value = "SELECT DISTINCT t FROM Team t LEFT JOIN FETCH t.users u LEFT JOIN FETCH t.company c WHERE u.id=:id")
    List<Team> findByUserId(Integer id);

}
