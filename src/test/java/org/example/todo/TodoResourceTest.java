package org.example.todo;

import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TodoResourceTest {
    private static final TodoDAO todoDAO = mock(TodoDAO.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new TodoResource(todoDAO))
            .build();

    @Before
    public void setup() {
        when(todoDAO.create(any(TodoEntity.class))).thenReturn(new TodoEntity(1, "Test Todo", false));
    }

    @After
    public void tearDown(){
        // we have to reset the mock after each test because of the
        // @ClassRule, or use a @Rule as mentioned below.
        reset(todoDAO);
    }
    @Test
    public void testCreateTodo() {
        // Given
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setId(1);
        todoDTO.setTitle("Test Todo");
        todoDTO.setCompleted(false);

        // When
        Response response = resources.target("/todos")
                .request()
                .post(Entity.json(todoDTO));

        // Then
        assertThat(response.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
        TodoDTO createdTodo = response.readEntity(TodoDTO.class);
        assertThat(createdTodo.getId()).isEqualTo(1L);
        assertThat(createdTodo.getTitle()).isEqualTo("Test Todo");
        assertThat(createdTodo.isCompleted()).isEqualTo(false);
    }
}
