package com.kodilla.jpa.task;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String status;

    @OneToMany(targetEntity = Person.class, mappedBy = "task")
    private List<Person> persons = new ArrayList<>();

    @OneToMany(targetEntity = Subtask.class, mappedBy = "tasks")
    private List<Subtask> subtasks = new ArrayList<>();

    public Task() {

    }

    public Task(Long id, String name, String status) {
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

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public List<Person> getPersons() {
        return persons;
    }

}