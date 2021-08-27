package com.yunhalee.withEmployee;

import com.yunhalee.withEmployee.Repository.UserRepository;
import com.yunhalee.withEmployee.entity.Role;
import com.yunhalee.withEmployee.entity.Team;
import com.yunhalee.withEmployee.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateMember(){
        Role rolemember = entityManager.find(Role.class, 5);
        User member = new User("CEO5", "CEO5@example.com","123456");

        member.setRole(rolemember);

        User savedMember = repo.save(member);
        assertThat(savedMember.getId()).isNotNull();

    }

    @Test
    public void testListAllMember(){
        Iterable<User> listAll = repo.findAll();

        listAll.forEach(m-> System.out.println(m));
    }

    @Test
    public void testGetMemberById(){
        User member = repo.findById(1).get();
        System.out.println(member);
        assertThat(member.getId()).isNotNull();
    }

    @Test
    public void testUpdateMember(){
        User member = repo.findById(3).get();

        member.setName("member1");
        repo.save(member);

        System.out.println(member);
    }

    @Test
    public void testUpdateMemberRole(){
        User member = repo.findById(5).get();

        Role role = entityManager.find(Role.class, 3);
        member.setRole(role);
        repo.save(member);

        System.out.println(member);
    }

    @Test
    public void testDeleteMember(){
        Integer memberId = 10;

        repo.deleteById(memberId);
    }

    @Test
    public void testAddTeam(){
        User member = repo.findById(34).get();
        Team team = entityManager.find(Team.class, 1);
        member.addTeam(team);

        User savedmember = repo.save(member);

        System.out.println(savedmember);
    }

    @Test
    public void testListMemberByTeam(){
        Team team = entityManager.find(Team.class, 1);

        List<User> members=repo.findByTeams(team);

        for (User member : members) {
            System.out.println(member);
        }

        assertThat(members.size()).isNotNull();
    }

    @Test
    public void testListMemberByRole(){
        Role role = entityManager.find(Role.class, 3);

        List<User> members= repo.findByRole(role);

        members.forEach(m-> System.out.println(m));

        assertThat(members.size()).isNotNull();

    }

    @Test
    public void testListAllUserWithTeams(){
        List<User> users = repo.findAllUsers();
        System.out.println(users);
    }

    @Test
    public void testListUserByEmail(){
        String email = "admin@example.com";
        User user = repo.findByEmail(email);

        System.out.println(user);
    }

    @Test
    public void testUserDeleteTeam(){
        User user = repo.findById(29).get();
        System.out.println(user.getTeams());
        Set<Team> teams = user.getTeams().stream().filter(t->!t.getId().equals(8)).collect(Collectors.toSet());

        System.out.println(teams);

    }

}
