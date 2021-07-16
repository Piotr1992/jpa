package com.kodilla.jpa.task;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class TaskTestSuite {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    void shouldNPlusOneProblemOccure() {
        //Given
        List<Long> savedTasks = insertExampleData();
        EntityManager em = emf.createEntityManager();

        //When
        System.out.println("****************** BEGIN OF FETCHING *******************");
        System.out.println("*** STEP 1 – query for tasks ***");

        TypedQuery<Task> query = em.createQuery(
                "from Task "
                        + " where id in (" + taskIds(savedTasks) + ")",
                Task.class);

        EntityGraph<Task> eg = em.createEntityGraph(Task.class);
        eg.addAttributeNodes("subtasks");
        query.setHint("javax.persistence.fetchgraph", eg);

        List<Task> tasks =
                query.getResultList();

        for (Task task : tasks) {
            System.out.println("*** STEP 2 – read data from task ***");
            System.out.println(task);
            System.out.println("Name -> " + task.getName());
            System.out.println("Status -> " + task.getStatus());

            System.out.println("*** STEP 3 – read the person from task ***");
            for (Person person : task.getPersons()) {
                System.out.println(person);
                System.out.println("*** STEP 4 – read the data from person ***");
                System.out.println("Firstname - > " + person.getFirstName());
                System.out.println("Lastname - > " + person.getLastName());
            }

            System.out.println("*** STEP 5 – read the subtask from task ***");
            for (Subtask subtask : task.getSubtasks()) {
                System.out.println(subtask);
                System.out.println("*** STEP 6 – read the data from subtask ***");
                System.out.println("Name - > " + subtask.getName());
                System.out.println("Status - > " + subtask.getStatus());
                System.out.println("Person - > " + subtask.getPersons());

                System.out.println("*** STEP 7 – read the person from subtask ***");
                for(Person person : subtask.getPersons()) {
                    System.out.println(person);
                    System.out.println("*** STEP 8 – read the data from person ***");
                    System.out.println("Firstname - > " + person.getFirstName());
                    System.out.println("Lastname - > " + person.getLastName());
                }

            }

        }

        System.out.println("****************** END OF FETCHING *******************");

        //Then
        //Here should be some assertions and the clean up performed

    }

    private String taskIds(List<Long> taskIds) {
        return taskIds.stream()
                .map(n -> "" + n)
                .collect(Collectors.joining(","));
    }

    private List<Long> insertExampleData() {

        Person p1 = new Person(null, "Jan", "Kowalski");
        Person p2 = new Person(null, "Zygmund", "Nowak");
        Person p3 = new Person(null, "Elżbieta", "Jadźko");
        Person p4 = new Person(null, "Alicja", "Malecka");
        List<Person> personList = new ArrayList<>();
        personList.add(p1);
        personList.add(p2);
        personList.add(p3);
        personList.add(p4);

        Task t1 = new Task(null, "Dodatek programistyczny dotyczący zestawień księgowych", "Przystąpienie do realizacji");
        Task t2 = new Task(null, "Raportu SQL z listy płac", "W trakcie realizacji");

        Subtask st1 = new Subtask(null, "Zdefiniowanie zestawień księgowych", "Zrealizowane");
        Subtask st2 = new Subtask(null, "Zaimplementowanie dodatku programistycznego", "W trakcie realizacji");
        Subtask st3 = new Subtask(null, "Zdefiniowanie opisu analitycznego", "W trakcie realizacji");
        Subtask st4 = new Subtask(null, "Stworzenie raportu SQL z listy płac", "W kolejce programistycznej");



        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(p1);
        em.persist(p2);
        em.persist(p3);
        em.persist(p4);
        em.persist(t1);
        em.persist(t2);
        em.persist(st1);
        em.persist(st2);
        em.persist(st3);
        em.persist(st4);
        em.flush();
        em.getTransaction().commit();
        em.close();

        return List.of(t1.getId(), t2.getId());
    }

}