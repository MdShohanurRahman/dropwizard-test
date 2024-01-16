package org.example.todo;

public class TodoDTO {
    private long id;
    private String title;
    private boolean completed;

    public TodoDTO() {}

    public TodoDTO(TodoEntity todo) {
        id = todo.getId();
        title = todo.getTitle();
        completed = todo.isCompleted();
    }

    public TodoEntity toTodoEntity() {
        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setTitle(title);
        todoEntity.setCompleted(completed);
        return todoEntity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}