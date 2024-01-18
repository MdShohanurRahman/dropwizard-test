package org.example.todo;

import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DAOTestRule;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.example.App;
import org.example.configs.AppConfig;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class TodoResourceIntegrationTest {

    @ClassRule
    public static final DropwizardAppRule<AppConfig> RULE =
            new DropwizardAppRule<>(App.class, ResourceHelpers.resourceFilePath("config.yml"));
    private final Client client = ClientBuilder.newClient();
    private final String baseUrl = String.format("http://localhost:%d", RULE.getLocalPort());

    @Rule
    public DAOTestRule database = DAOTestRule.newBuilder().addEntityClass(TodoEntity.class).build();

    @Test
    public void testCrudOperation() {
        testCreateTodo();
        testGetTodo();
        testUpdateTodo();
        testDeleteTodo();
    }

    @Test
    public void testCreateTodo() {
        //given
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setTitle("Test Todo");
        todoDTO.setCompleted(false);

        // when
        Response response = client.target(baseUrl + "/todos")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(todoDTO));

        // then
        assertThat(response.getStatus()).isEqualTo(201);
    }

    @Test
    public void testGetTodo() {
        // given
        long id = 1;
        String title = "Test Todo";

        // when
        Response getResponse = client.target(baseUrl + "/todos/" + id)
                .request(MediaType.APPLICATION_JSON)
                .get();

        // then
        assertThat(getResponse.getStatus()).isEqualTo(200);
        TodoDTO retrievedTodo = getResponse.readEntity(TodoDTO.class);
        assertThat(retrievedTodo.getTitle()).isEqualTo(title);
        assertThat(retrievedTodo.isCompleted()).isFalse();
    }

    @Test
    public void testUpdateTodo() {
        // given
        long id = 1;
        TodoDTO updatedTodo = new TodoDTO();
        updatedTodo.setTitle("Updated Todo");
        updatedTodo.setCompleted(true);

        // when
        Response updateResponse = client.target(baseUrl + "/todos/" + id)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(updatedTodo));

        // then
        assertThat(updateResponse.getStatus()).isEqualTo(200);

        // Read again to verify the update
        Response getUpdatedResponse = client.target(baseUrl + "/todos/1")
                .request(MediaType.APPLICATION_JSON)
                .get();

        assertThat(getUpdatedResponse.getStatus()).isEqualTo(200);
        TodoDTO updatedRetrievedTodo = getUpdatedResponse.readEntity(TodoDTO.class);
        assertThat(updatedRetrievedTodo.getTitle()).isEqualTo("Updated Todo");
        assertThat(updatedRetrievedTodo.isCompleted()).isTrue();
    }

    @Test
    public void testDeleteTodo() {
        // Delete
        Response deleteResponse = client.target(baseUrl + "/todos/1")
                .request()
                .delete();

        assertThat(deleteResponse.getStatus()).isEqualTo(204);

        // Try to get the deleted resource, should return 404
        Response getDeletedResponse = client.target(baseUrl + "/todos/1")
                .request(MediaType.APPLICATION_JSON)
                .get();

        assertThat(getDeletedResponse.getStatus()).isEqualTo(404);
    }

}