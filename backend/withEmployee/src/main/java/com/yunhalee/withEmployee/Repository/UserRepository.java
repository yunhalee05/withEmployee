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

    User findByEmail(String email);

    @Query(value = "SELECT u FROM User u INNER JOIN FETCH u.role r LEFT JOIN FETCH u.teams t LEFT JOIN FETCH t.company c")
    List<User> findAllUsers();

    List<User> findByTeams(Team team);

    List<User> findByRole(Role role);
}
