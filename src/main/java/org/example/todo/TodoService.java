package org.example.todo;

import org.example.exceptions.BadRequestApiException;
import org.example.exceptions.NotFoundApiException;

import javax.ws.rs.WebApplicationException;
import java.util.List;
import java.util.stream.Collectors;

public class TodoService {
    private final TodoDAO todoDAO;

    public TodoService(TodoDAO todoDAO) {
        this.todoDAO = todoDAO;
    }

    public TodoDTO createTodo(TodoDTO todoDTO) {
        if (todoDTO.getTitle() == null || todoDTO.getTitle().isEmpty()) {
            throw new BadRequestApiException("Title can not be Empty");
        }
        TodoEntity todo = todoDTO.toTodoEntity();
        TodoEntity createdTodo = todoDAO.create(todo);
        return new TodoDTO(createdTodo);
    }

    public void delete(long id) {
        TodoEntity todo = todoDAO.findById(id)
                .orElseThrow(() -> new NotFoundApiException("Todo not found with id: " + id));

        todoDAO.delete(todo);
    }

    public TodoDTO updateTodo(long id, TodoDTO todoDTO) {
        TodoEntity todo = todoDAO.findById(id)
                .orElseThrow(() -> new NotFoundApiException("Todo not found with id: " + id));

        // Update the fields of the existing object
        todo.setTitle(todoDTO.getTitle());
        todo.setCompleted(todoDTO.isCompleted());
        return new TodoDTO(todo);
    }

    public TodoDTO getTodoById(long id) {
        TodoEntity todo = todoDAO.findById(id)
                .orElseThrow(() -> new NotFoundApiException("Todo not found with id: " + id));
        return new TodoDTO(todo);
    }

    public List<TodoDTO> getAllTodos() {
        List<TodoEntity> todos = todoDAO.findAll();
        return todos.stream()
                .map(TodoDTO::new)
                .collect(Collectors.toList());
    }
}
