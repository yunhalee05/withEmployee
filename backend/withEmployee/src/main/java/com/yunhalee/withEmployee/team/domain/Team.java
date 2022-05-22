package com.yunhalee.withEmployee.team.domain;

import com.yunhalee.withEmployee.company.domain.Company;
import com.yunhalee.withEmployee.user.domain.User;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "team")
@Getter
//@Setter
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Integer id;

    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @ManyToMany(mappedBy = "teams")
    private Set<User> users= new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    public Team(String name) {
        this.name = name;
    }

    private Team(String name, Company company) {
        this.name = name;
        this.company = company;
    }

    public Team(Integer id, String name, Company company) {
        this.id = id;
        this.name = name;
        this.company = company;
    }

    public static Team of(String name, Company company) {
        return new Team(name, company);
    }

    public void changeName(String name){
        this.name = name;
    }

    public void addMember(User user) {
        this.users.add(user);
        user.addTeam(this);
    }

    public void subtractMember(User user) {
        this.users = this.users.stream()
            .filter(u -> !u.equals(user))
            .collect(Collectors.toSet());
        user.subtractTeam(this);
    }


    @Transient
    public Integer getTotalNumber(){
        return this.users.size();
    }

    public String getCompanyName() {
        return this.company.getName();
    }

    public Integer getCompanyId() {
        return this.company.getId();
    }

    public boolean isId(Integer id){
        return this.id.equals(id);
    }

    @Override
    public String toString() {
        return "Team{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}
