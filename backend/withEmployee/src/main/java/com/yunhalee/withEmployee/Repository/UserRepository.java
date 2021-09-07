package com.yunhalee.withEmployee.Repository;

import com.yunhalee.withEmployee.entity.Role;
import com.yunhalee.withEmployee.entity.Team;
import com.yunhalee.withEmployee.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByName(String name);

    @Query(value = "SELECT u FROM User u INNER JOIN FETCH u.role r WHERE u.email=:email")
    User findByEmail(String email);

    @Query(value = "SELECT u FROM User u INNER JOIN FETCH u.role r LEFT JOIN FETCH u.teams t LEFT JOIN FETCH t.company c")
    List<User> findAllUsers();

    @Query(value = "SELECT DISTINCT u FROM User u INNER JOIN FETCH u.role r LEFT JOIN FETCH u.teams t LEFT JOIN FETCH t.company c",
            countQuery = "SELECT count(DISTINCT u) FROM User u")
    Page<User> findAllUsers(Pageable pageable);

    List<User> findByTeams(Team team);

    List<User> findByRole(Role role);
}
