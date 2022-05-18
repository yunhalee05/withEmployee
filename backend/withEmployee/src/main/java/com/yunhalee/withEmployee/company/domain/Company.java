package com.yunhalee.withEmployee.company.domain;

import com.yunhalee.withEmployee.common.domain.BaseTimeEntity;
import com.yunhalee.withEmployee.team.domain.Team;
import com.yunhalee.withEmployee.user.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="company")
@Getter
@Setter
public class Company extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="company_id")
    private Integer id;

    @Column(name="name", unique = true, nullable = false, length = 40)
    private String name;

    @Column(name = "description", nullable = false, length = 150)
    private String description;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Team> teams = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User ceo;


    public Company(){
        super();
    }

    public Company(String name, String description) {
        this.name = name;
        this.description = description;
    }

    private Company(String name, String description, User ceo) {
        this.name = name;
        this.description = description;
        this.ceo = ceo;
    }

    public Company(Integer id, String name, String description,
        User ceo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ceo = ceo;
    }

    public static Company of(String name, String description, User ceo)    {
        return new Company(name, description, ceo);
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", CEO=" + ceo +
                '}';
    }

    @Transient
    public Integer getTotalNumber(){
        Integer num = 0;
        for (Team team : this.teams){
            num += team.getUsers().size();
        }

        return num;
    }





}
