package com.yunhalee.withEmployee.Repository;

import com.yunhalee.withEmployee.entity.Member;
import com.yunhalee.withEmployee.entity.Role;
import com.yunhalee.withEmployee.entity.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends CrudRepository<Member, Integer> {
    Member findByName(String name);

    List<Member> findByTeams(Team team);

    List<Member> findByRole(Role role);
}
