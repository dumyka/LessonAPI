package tests;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static specs.CreateUserSpecs.createUserRequestSpec;
import static specs.CreateUserSpecs.createUserResponseSpec;
import static specs.DeleteUserSpecs.deletedUserRequestSpec;
import static specs.DeleteUserSpecs.deletedUserResponseSpec;
import static specs.ListUserSpecs.listUserRequestSpec;
import static specs.ListUserSpecs.listUserResponseSpec;
import static specs.LoginSpec.loginRequestSpec;
import static specs.LoginSpec.loginResponseSpec;
import static specs.UpdateUserSpec.updatedUserRequestSpec;
import static specs.UpdateUserSpec.updatedUserResponseSpec;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import model.CreateUserResponseModel;
import model.LoginBodyModel;
import model.LoginErrorResponseModel;
import model.UpdateUserResponseModel;
import model.UserBodyModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Lombok")
public class ReqresInLombokAndGroovyTest {

  @DisplayName("Checking the name and position when creating a user")
  @Test
  void createUser() {
    UserBodyModel data = new UserBodyModel();
    data.setName("morpheus");
    data.setJob("leader");

    CreateUserResponseModel response = given(createUserRequestSpec)
        .body(data)
        .when()
        .post("/users")
        .then()
        .spec(createUserResponseSpec)
        .extract().as(CreateUserResponseModel.class);

    assertThat(response.getName()).isEqualTo("morpheus");
    assertThat(response.getJob()).isEqualTo("leader");
  }

  @DisplayName("Checking the number of all users to groovy")
  @Test
  void getUsers() {
    given(listUserRequestSpec)
        .when()
        .get("/users?page=2")
        .then()
        .spec(listUserResponseSpec)
        .body("total", is(12))
        .body("data.find{it.id == 7}.email", is("michael.lawson@reqres.in"))
        .body("data.findAll{it.email =~/.*?@reqres.in/}.email.flatten()",
            hasItem("byron.fields@reqres.in"));
  }

  @DisplayName("Editing the user's place of work")
  @Test
  void editUser() {
    String dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
    UserBodyModel data = new UserBodyModel();
    data.setName("morpheus");
    data.setJob("zion resident");

    UpdateUserResponseModel response = given(updatedUserRequestSpec)
        .when()
        .patch("/users/2")
        .then()
        .spec(updatedUserResponseSpec)
        .extract().as(UpdateUserResponseModel.class);

    assertThat(response.getUpdatedAt()).contains(dateTime);
  }

  @DisplayName("Deleting a user")
  @Test
  void deleteUser() {
    given(deletedUserRequestSpec)
        .when()
        .delete("/users/2")
        .then()
        .spec(deletedUserResponseSpec);
  }

  @DisplayName("Login-Unsuccessful")
  @Test
  void loginUnsuccessful() {
    LoginBodyModel data = new LoginBodyModel();
    data.setEmail("peter@klaven");

    LoginErrorResponseModel response = given(loginRequestSpec)
        .body(data)
        .when()
        .post("/login")
        .then()
        .spec(loginResponseSpec)
        .extract().as(LoginErrorResponseModel.class);

    assertThat(response.getError()).isEqualTo("Missing password");
  }
}

