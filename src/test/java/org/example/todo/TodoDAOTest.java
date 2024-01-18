package org.example.todo;

import io.dropwizard.testing.junit.DAOTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TodoDAOTest {

    @Rule
    public DAOTestRule database = DAOTestRule.newBuilder()
            .addEntityClass(TodoEntity.class)
           /* .customizeConfiguration(builder -> {
            // Customize the configuration here
            builder.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
            builder.setProperty("hibernate.hbm2ddl.auto", "update");
            // so on ... as your needed
            })*/
            .build();

    private TodoDAO todoDAO;


    @Before
    public void setUp() {
        todoDAO = new TodoDAO(database.getSessionFactory());
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testCreateTodo() {
        // Given
        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setTitle("Test Todo");
        todoEntity.setCompleted(false);

        // When
        TodoEntity savedTodo = database.inTransaction(() -> todoDAO.create(todoEntity));

        // Then
        assertThat(savedTodo.getId()).isNotNull();
        assertThat(savedTodo.getTitle()).isEqualTo("Test Todo");
        assertThat(savedTodo.isCompleted()).isFalse();
    }

    @Test
    public void testGetTodo() {
        // Given
        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setTitle("Test Todo");
        todoEntity.setCompleted(false);

        // When
        TodoEntity savedTodo = database.inTransaction(() -> todoDAO.create(todoEntity));

        // Then
        TodoEntity retrievedTodo = todoDAO.findById(savedTodo.getId()).orElse(null);

        assertThat(retrievedTodo).isNotNull();
        assertThat(retrievedTodo.getId()).isEqualTo(savedTodo.getId());
        assertThat(retrievedTodo.getTitle()).isEqualTo("Test Todo");
        assertThat(retrievedTodo.isCompleted()).isFalse();
    }
}