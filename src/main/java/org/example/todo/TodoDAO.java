package org.example.todo;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class TodoDAO extends AbstractDAO<TodoEntity> {
    public TodoDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<TodoEntity> findById(Long id) {
        return Optional.ofNullable(get(id));
    }

    public TodoEntity create(TodoEntity todo) {
        return persist(todo);
    }

    public List<TodoEntity> findAll() {
        Query<TodoEntity> query = currentSession().createQuery("from TodoEntity", TodoEntity.class);
        return list(query);
    }


    public void delete(TodoEntity todo) {
        currentSession().remove(todo);
    }

    public List<TodoEntity> findTodosByTitle(String title) {
        String sql = "SELECT * FROM todos WHERE title = :title";
        NativeQuery<TodoEntity> query = this.currentSession().createNativeQuery(sql, TodoEntity.class);
        query.setParameter("title", title);
        return query.getResultList();
    }

    public List<TodoEntity> findCompletedTodos() {
        return list(query("FROM TodoEntity WHERE completed = true"));
    }
}