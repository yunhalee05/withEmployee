package com.yunhalee.withEmployee.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;

    @Column(name = "name", unique = true, nullable = false, length = 10)
    private String name;

    @Column(name = "description", nullable = false, length = 40)
    private String description;

    @OneToMany(mappedBy = "role", cascade = CascadeType.PERSIST)
    private Set<User> users = new HashSet<>();

    public Role(){
        super();
    }

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                '}';
    }
}
