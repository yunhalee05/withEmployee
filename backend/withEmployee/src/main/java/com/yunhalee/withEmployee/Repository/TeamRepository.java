package com.yunhalee.withEmployee.Repository;

import com.yunhalee.withEmployee.entity.Company;
import com.yunhalee.withEmployee.entity.Team;
import com.yunhalee.withEmployee.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public
interface TeamRepository extends CrudRepository<Team, Integer> {
    List<Team> findByUsers(User user);

    List<Team> findByCompany(Company company);
}
