package com.kodilla.jpa.task;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JpaTestSuite {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    void shouldPersistCustomer() {
        //Given
        EntityManager em = emf.createEntityManager();

        Subtask subtask = new Subtask(null, "Aplikacja generująca wydruki Crystal Reports", "Ustalanie założeń z klientem");

        //When
        em.getTransaction().begin();
        em.persist(subtask);
        em.flush();
        em.getTransaction().commit();

        //Then
        Long key = subtask.getId();
        Subtask readSubtask = em.find(Subtask.class, key);
        em.close();
        assertThat(readSubtask.getName()).isEqualTo(subtask.getName());
    }

}