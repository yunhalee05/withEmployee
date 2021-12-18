package com.yunhalee.withEmployee.user.domain;

import com.yunhalee.withEmployee.company.domain.Company;
import com.yunhalee.withEmployee.conversation.domain.Conversation;
import com.yunhalee.withEmployee.team.domain.Team;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="user")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Integer id;

    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "description")
    private String description;

    @Column(name="image_name")
    private String imageName;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "phone_Number")
    private String phoneNumber;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "member_team",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private Set<Team> teams = new HashSet<>();

    @OneToMany(mappedBy = "ceo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Company> companies = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="role_id")
    private Role role;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private Set<Conversation> conversations = new HashSet<>();


    public User(){
        super();
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }

    public void addTeam(Team team){
        this.teams.add(team);
    }

    @Transient
    public String getRoleName(){
        return this.role.getName();
    }

    @Transient
    public List<String> getCompanyNames(){
        List<String> companies = new ArrayList<>();
        this.companies.forEach(company -> companies.add(company.getName()));
        return companies;
    }

    public List<String> getTeamNames(){
        List<String> teams = new ArrayList<>();

        this.teams.forEach(team -> teams.add(team.getName()));
        return teams;
    }

}
