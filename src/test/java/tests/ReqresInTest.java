package tests;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReqresInTest {

  @DisplayName("Checking the name and position when creating a user")
  @Test
  void createUser() {

    String data = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

    given()
        .log().uri()
        .contentType(JSON)
        .body(data)
        .when()
        .post("https://reqres.in/api/users")
        .then()
        .log().status()
        .log().body()
        .statusCode(201)
        .body("name", is("morpheus"))
        .body("job", is("leader"));
  }

  @DisplayName("Checking the number of all users")
  @Test
  void getUsers() {
    given()
        .log().uri()
        .when()
        .get("https://reqres.in/api/users?page=2")
        .then()
        .log().status()
        .log().body()
        .statusCode(200)
        .body("total", is(12));

  }

  @DisplayName("Editing the user's place of work")
  @Test
  void editUser() {

    String data = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";

    given()
        .log().uri()
        .contentType(JSON)
        .body(data)
        .when()
        .put("https://reqres.in/api/users/2")
        .then()
        .log().status()
        .log().body()
        .statusCode(200)
        .body("name", is("morpheus"))
        .body("job", is("zion resident"));
  }

  @DisplayName("Deleting a user")
  @Test
  void deleteUser() {

    given()
        .log().uri()
        .when()
        .delete("https://reqres.in/api/users/2")
        .then()
        .log().status()
        .log().body()
        .statusCode(204);

  }

  @DisplayName("Login-Unsuccessful")
  @Test
  void loginUnsuccessful() {

    String data = "{ \"email\": \"peter@klaven\" }";

    given()
        .log().uri()
        .contentType(JSON)
        .body(data)
        .when()
        .post("https://reqres.in/api/login")
        .then()
        .log().status()
        .log().body()
        .statusCode(400)
        .body("error", is("Missing password"));

  }
}

