package org.example.todo;

import org.example.exceptions.BadRequestApiException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TodoServiceTest  {
    private static final TodoDAO todoDAO = mock(TodoDAO.class);
    private static final TodoService todoService = mock(TodoService.class);
    private final TodoEntity todoEntity =  new TodoEntity(1, "Test Todo", false);

    @Before
    public void setup() {
    }

    @After
    public void tearDown(){
        // we have to reset the mock after each test because of the
        // @ClassRule, or use a @Rule as mentioned below.
        reset(todoDAO);
    }
    @Test
    public void createTodo() {
        // Given
        TodoDTO todoDTO = new TodoDTO(todoEntity);

        // When
        when(todoDAO.create(any(TodoEntity.class))).thenReturn(todoEntity);
        todoService.createTodo(todoDTO);

        // Then
        assertThat(todoEntity.getId()).isEqualTo(1L);
        assertThat(todoEntity.getTitle()).isEqualTo("Test Todo");
        assertThat(todoEntity.isCompleted()).isEqualTo(false);
    }

    @Test
    public void shouldThroughExceptionWhenTitleIsNull() {
        // Given
        TodoDTO todoDTO = new TodoDTO(1, null, false);

        // When and then
        assertThatThrownBy(() -> new TodoService(todoDAO).createTodo(todoDTO))
                .isInstanceOf(BadRequestApiException.class);
    }
}