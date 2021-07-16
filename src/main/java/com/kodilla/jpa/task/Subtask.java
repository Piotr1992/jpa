package com.kodilla.jpa.task;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Subtask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String status;

    @ManyToOne
    private Task tasks;

    @OneToMany(targetEntity = Person.class, mappedBy = "subtasks")
    private List<Person> persons = new ArrayList<>();

    public Subtask() {

    }

    public Subtask(Long id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public Task getTasks() {
        return tasks;
    }

    public List<Person> getPersons() {
        return persons;
    }

}