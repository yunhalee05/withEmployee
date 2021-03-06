package com.yunhalee.withEmployee.user.domain;

import com.yunhalee.withEmployee.company.domain.Company;
import com.yunhalee.withEmployee.conversation.domain.Conversation;
import com.yunhalee.withEmployee.team.domain.Team;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.util.StringUtils;

@Entity
@Table(name = "user", indexes = @Index(name = "idx_name", columnList = "name"))
@NoArgsConstructor
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
        name = "member_team",
        joinColumns = @JoinColumn(name = "member_id"),
        inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private Set<Team> teams = new HashSet<>();

    @OneToMany(mappedBy = "ceo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Company> companies = new HashSet<>();

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

    public static User of(String name, String email, String password, String description,
        String phoneNumber, Role role) {
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

    public void subtractTeam(Team team) {
        this.teams = this.teams.stream()
            .filter(t -> !t.equals(team))
            .collect(Collectors.toSet());
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
        return this.companies.stream()
            .map(Company::getName)
            .collect(Collectors.toList());
    }

    public List<String> getTeamNames() {
        return this.teams.stream()
            .map(Team::getName)
            .collect(Collectors.toList());
    }

    public void changeImageURL(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void update(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.description = user.getDescription();
        this.phoneNumber = user.getPhoneNumber();
        updatePassword(user.getPassword());
    }

    private void updatePassword(String updatedPassword) {
        if (!updatedPassword.equals("")) {
            this.password = updatedPassword;
        }
    }

    public void addCompany(Company company) {
        this.companies.add(company);
    }

    public boolean isUser(Integer id) {
        return this.id == id;
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
