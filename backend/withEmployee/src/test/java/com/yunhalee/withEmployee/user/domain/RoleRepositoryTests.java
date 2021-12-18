package com.yunhalee.withEmployee.user.domain;

import com.yunhalee.withEmployee.user.domain.RoleRepository;
import com.yunhalee.withEmployee.user.domain.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {

    @Autowired
    private RoleRepository repo;

    @Test
    public  void testCreateFirstRole(){
        Role roleMember = new Role("CEO", "Manage Company");
        Role savedRole = repo.save(roleMember);

        assertThat(savedRole.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetRoleByName(){
        String name = "Member";
        Role role = repo.findByName(name);

        System.out.println(role);
    }

}
