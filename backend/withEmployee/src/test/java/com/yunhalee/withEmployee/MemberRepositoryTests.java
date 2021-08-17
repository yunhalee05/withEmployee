package com.yunhalee.withEmployee;

import com.yunhalee.withEmployee.Repository.MemberRepository;
import com.yunhalee.withEmployee.entity.Member;
import com.yunhalee.withEmployee.entity.Role;
import com.yunhalee.withEmployee.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateMember(){
        Role rolemember = entityManager.find(Role.class, 3);
        Member member = new Member("delete", "memberdelete@example.com","12345");

        member.setRole(rolemember);

        Member savedMember = repo.save(member);
        assertThat(savedMember.getId()).isNotNull();

    }

    @Test
    public void testListAllMember(){
        Iterable<Member> listAll = repo.findAll();

        listAll.forEach(m-> System.out.println(m));
    }

    @Test
    public void testGetMemberById(){
        Member member = repo.findById(1).get();
        System.out.println(member);
        assertThat(member.getId()).isNotNull();
    }

    @Test
    public void testUpdateMember(){
        Member member = repo.findById(3).get();

        member.setName("member1");
        repo.save(member);

        System.out.println(member);
    }

    @Test
    public void testUpdateMemberRole(){
        Member member = repo.findById(5).get();

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
        Member member = repo.findById(10).get();
        Team team = entityManager.find(Team.class, 2);
        member.addTeam(team);

        Member savedmember = repo.save(member);

        System.out.println(savedmember);
    }

    @Test
    public void testListMemberByTeam(){
        Team team = entityManager.find(Team.class, 1);

        List<Member> members=repo.findByTeams(team);

        for (Member member : members) {
            System.out.println(member);
        }

        assertThat(members.size()).isNotNull();
    }

    @Test
    public void testListMemberByRole(){
        Role role = entityManager.find(Role.class, 3);

        List<Member> members= repo.findByRole(role);

        members.forEach(m-> System.out.println(m));

        assertThat(members.size()).isNotNull();

    }

}
