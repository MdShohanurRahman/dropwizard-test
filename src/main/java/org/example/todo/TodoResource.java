package org.example.todo;

import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource {
    private final TodoService todoService;

    public TodoResource(TodoDAO todoDAO) {
        this.todoService = new TodoService(todoDAO);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public List<TodoDTO> getAllTodos(@BeanParam TodoDTO request) {
        return todoService.getAllTodos();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response createTodo(@Valid TodoDTO todoDTO) {
        return Response.status(Response.Status.CREATED)
                .entity(todoService.createTodo(todoDTO))
                .build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response getTodoById(@PathParam("id") long id) {
        return Response.ok(todoService.getTodoById(id)).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response updateTodoById(@PathParam("id") long id, @Valid TodoDTO todoDTO) {
        return Response.ok(todoService.updateTodo(id, todoDTO)).build();
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public Response deleteTodoById(@PathParam("id") long id) {
        todoService.delete(id);
        return Response.noContent().build();
    }
}
