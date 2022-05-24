package com.yunhalee.withEmployee.user.domain;

import com.yunhalee.withEmployee.conversation.domain.Conversation;
import com.yunhalee.withEmployee.team.domain.Team;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByName(String name);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query(value = "SELECT u FROM User u LEFT JOIN FETCH u.teams t LEFT JOIN FETCH t.company c")
    List<User> findAllUsers();

    @Query(value = "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.companies c LEFT JOIN FETCH u.teams t ",
        countQuery = "SELECT count(DISTINCT u) FROM User u")
    Page<User> findAllUsers(Pageable pageable);

    List<User> findByTeams(Team team);

    List<User> findByRole(Role role);
}
