package com.yunhalee.withEmployee.Repository;

import com.yunhalee.withEmployee.entity.Company;
import com.yunhalee.withEmployee.entity.Member;
import com.yunhalee.withEmployee.entity.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public
interface TeamRepository extends CrudRepository<Team, Integer> {
    List<Team> findByMembers(Member member);

    List<Team> findByCompany(Company company);
}
