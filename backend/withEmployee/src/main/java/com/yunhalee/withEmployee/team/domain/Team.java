package com.yunhalee.withEmployee.team.domain;

import com.yunhalee.withEmployee.company.domain.Company;
import com.yunhalee.withEmployee.user.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "team")
@Getter
//@Setter
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

    public static Team of(String name, Company company) {
        return new Team(name, company);
    }

    public void changeName(String name){
        this.name = name;
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
