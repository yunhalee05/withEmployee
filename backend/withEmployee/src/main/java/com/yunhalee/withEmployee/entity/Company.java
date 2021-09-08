package com.yunhalee.withEmployee.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
