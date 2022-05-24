package com.yunhalee.withEmployee.company.domain;

import com.yunhalee.withEmployee.common.domain.BaseTimeEntity;
import com.yunhalee.withEmployee.team.domain.Team;
import com.yunhalee.withEmployee.user.domain.User;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "company")
@Getter
@NoArgsConstructor
public class Company extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Integer id;

    @Column(name = "name", unique = true, nullable = false, length = 40)
    private String name;

    @Column(name = "description", nullable = false, length = 150)
    private String description;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Team> teams = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User ceo;

    @Builder
    public Company(Integer id, @NonNull String name, @NonNull String description, User ceo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ceo = ceo;
    }

    public static Company of(String name, String description, User ceo) {
        return Company.builder()
            .name(name)
            .description(description)
            .ceo(ceo).build();
    }

    public String getCeoName() {
        return this.ceo.getName();
    }

    public Integer getCeoId() {
        return this.ceo.getId();
    }

    public Set<User> getMembers() {
        return this.teams.stream()
            .flatMap(team -> team.getUsers().stream())
            .collect(Collectors.toSet());
    }

    public boolean isCompany(Integer id) {
        return this.id.equals(id);
    }

    public void update(Company company) {
        if (company.getCeo() != null) {
            this.ceo = company.getCeo();
        }
        this.name = company.getName();
        this.description = company.getDescription();
    }

    @Transient
    public Integer getTotalNumber() {
        return this.teams.stream()
            .mapToInt(team -> team.getTotalNumber())
            .sum();
    }


}
