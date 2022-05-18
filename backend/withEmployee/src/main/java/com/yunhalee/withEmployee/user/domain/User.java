package com.yunhalee.withEmployee.user.domain;

import com.yunhalee.withEmployee.company.domain.Company;
import com.yunhalee.withEmployee.conversation.domain.Conversation;
import com.yunhalee.withEmployee.team.domain.Team;
import java.util.Collections;
import java.util.Objects;
import lombok.Builder;
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
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "description")
    private String description;

    @Column(name = "image_name")
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

    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private Set<Conversation> conversations = new HashSet<>();

    public User() {
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Builder
    public User(Integer id, String name, String email, String password, String description,
        String imageName, String imageUrl, String phoneNumber, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.description = description;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public static User of(String name, String email, String password, String description, String phoneNumber, Role role) {
        return User.builder()
            .name(name)
            .email(email)
            .password(password)
            .description(description)
            .phoneNumber(phoneNumber)
            .role(role).build();
    }

    public void addTeam(Team team) {
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
        return Collections.unmodifiableSet(teams);
    }

    public Set<Company> getCompanies() {
        return Collections.unmodifiableSet(companies);
    }

    @Transient
    public List<String> getCompanyNames() {
        List<String> companies = new ArrayList<>();
        this.companies.forEach(company -> companies.add(company.getName()));
        return companies;
    }

    public List<String> getTeamNames() {
        List<String> teams = new ArrayList<>();

        this.teams.forEach(team -> teams.add(team.getName()));
        return teams;
    }

    public void changeImageURL(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name)
            && Objects.equals(email, user.email) && Objects
            .equals(password, user.password) && Objects.equals(description, user.description)
            && Objects.equals(imageUrl, user.imageUrl) && Objects
            .equals(phoneNumber, user.phoneNumber) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, description, imageUrl, phoneNumber, role);
    }
}
