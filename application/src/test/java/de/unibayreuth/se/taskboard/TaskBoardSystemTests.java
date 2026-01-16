package de.unibayreuth.se.taskboard;

import de.unibayreuth.se.taskboard.api.dtos.TaskDto;
import de.unibayreuth.se.taskboard.api.dtos.UserDto;
import de.unibayreuth.se.taskboard.api.mapper.TaskDtoMapper;
import de.unibayreuth.se.taskboard.business.domain.Task;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class TaskBoardSystemTests extends AbstractSystemTest {

    @Autowired
    private TaskDtoMapper taskDtoMapper;

    @Test
    void getAllCreatedTasks() {
        List<Task> createdTasks = TestFixtures.createTasks(taskService);

        List<Task> retrievedTasks = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/tasks")
                .then()
                .statusCode(200)
                .body(".", hasSize(createdTasks.size()))
                .and()
                .extract().jsonPath().getList("$", TaskDto.class)
                .stream()
                .map(taskDtoMapper::toBusiness)
                .toList();

        assertThat(retrievedTasks)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("createdAt", "updatedAt")
                .containsExactlyInAnyOrderElementsOf(createdTasks);
    }

    @Test
    void createAndDeleteTask() {
        Task createdTask = taskService.create(TestFixtures.getTasks().getFirst());

        when()
                .get("/api/tasks/{id}", createdTask.getId())
                .then()
                .statusCode(200);

        when()
                .delete("/api/tasks/{id}", createdTask.getId())
                .then()
                .statusCode(200);

        when()
                .get("/api/tasks/{id}", createdTask.getId())
                .then()
                .statusCode(400);
    }

    @Test
    void createAndGetAllUsers() {
        // create users via REST
        List<UserDto> createdUsers = TestFixtures.getUsers().stream()
                .map(u -> given()
                        .contentType(ContentType.JSON)
                        .body(new UserDto(null, u.getName(), null))
                        .when()
                        .post("/api/users")
                        .then()
                        .statusCode(200)
                        .extract().as(UserDto.class)
                )
                .toList();

        // retrieve all users (may include initial users -> don't assert exact size)
        List<UserDto> allUsers = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/users")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("$", UserDto.class);

        // verify the created users are present
        for (UserDto created : createdUsers) {
            assertThat(allUsers.stream().anyMatch(u -> u.id().equals(created.id()))).isTrue();
            assertThat(allUsers.stream().anyMatch(u -> u.name().equals(created.name()))).isTrue();
        }
    }

    @Test
    void createAndGetUserById() {
        UserDto created = given()
                .contentType(ContentType.JSON)
                .body(new UserDto(null, "Denise", null))
                .when()
                .post("/api/users")
                .then()
                .statusCode(200)
                .extract().as(UserDto.class);

        UserDto retrieved = when()
                .get("/api/users/{id}", created.id())
                .then()
                .statusCode(200)
                .extract().as(UserDto.class);

        assertThat(retrieved.name()).isEqualTo("Denise");
        assertThat(retrieved.id()).isEqualTo(created.id());
    }
}
