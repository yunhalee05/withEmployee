package com.yunhalee.withEmployee.Repository;

import com.yunhalee.withEmployee.entity.Company;
import com.yunhalee.withEmployee.entity.Team;
import com.yunhalee.withEmployee.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public
interface TeamRepository extends CrudRepository<Team, Integer> {

    @Query(value = "SELECT DISTINCT t FROM Team t INNER JOIN FETCH t.company c LEFT JOIN FETCH t.users u")
    List<Team> findAllTeams();

    List<Team> findByUsers(User user);

    List<Team> findByCompany(Company company);
}
