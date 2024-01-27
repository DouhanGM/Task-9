package ru.itmentor.spring.boot_security.demo.entity;


import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_role", unique = true)
    private String nameRole;

    @ManyToMany(mappedBy = "roles")
    private List<Person> persons;

    public Role() {
    }

    public Role(Long id, String nameRole, List<Person> persons) {
        this.id = id;
        this.nameRole = nameRole;
        this.persons = persons;
    }

    public Role(String nameRole, List<Person> persons) {
        this.nameRole = nameRole;
        this.persons = persons;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameRole() {
        return nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    @Override
    public String getAuthority() {
        return getNameRole();
    }
}
