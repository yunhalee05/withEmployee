package com.yunhalee.withEmployee.Repository;

import com.yunhalee.withEmployee.entity.Role;
import com.yunhalee.withEmployee.entity.Team;
import com.yunhalee.withEmployee.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByName(String name);

    List<User> findByTeams(Team team);

    List<User> findByRole(Role role);
}
