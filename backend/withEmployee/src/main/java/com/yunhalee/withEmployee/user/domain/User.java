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
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.util.StringUtils;

@Entity
@Table(name="user")
//@Getter
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

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "member_team",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private Set<Team> teams = new HashSet<>();

    @OneToMany(mappedBy = "ceo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Company> companies = new HashSet<>();

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="role_id")
//    private Role role;

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

    private User(String name, String email, String password, String description, String phoneNumber, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public static User of(String name, String email, String password, String description, String phoneNumber, Role role) {
        return new User(name, email, password, description, phoneNumber, role);
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

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

    public String getImageName() {
        return imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRole() {
        return role.name();
    }

    public String getPassword() {
        return password;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public Set<Company> getCompanies() {
        return companies;
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

    public void changeImageURL(String imageUrl) {
        this.imageUrl = imageUrl;
    }



}
