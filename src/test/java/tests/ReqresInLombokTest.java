package tests;

import static Specs.CreateUserSpecs.createUserRequestSpec;
import static Specs.CreateUserSpecs.createUserResponseSpec;
import static Specs.DeleteUserSpecs.deletedUserRequestSpec;
import static Specs.DeleteUserSpecs.deletedUserResponseSpec;
import static Specs.ListUserSpecs.listUserRequestSpec;
import static Specs.ListUserSpecs.listUserResponseSpec;
import static Specs.LoginSpec.loginRequestSpec;
import static Specs.LoginSpec.loginResponseSpec;
import static Specs.UpdateUserSpec.updatedUserRequestSpec;
import static Specs.UpdateUserSpec.updatedUserResponseSpec;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import model.CreateUserResponseModel;
import model.ListUserModel;
import model.LoginBodyModel;
import model.LoginErrorResponseModel;
import model.UpdateUserResponseModel;
import model.UserBodyModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Lombok")
public class ReqresInLombokTest {

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

  @DisplayName("Checking the number of all users")
  @Test
  void getUsers() {
    ListUserModel response = given(listUserRequestSpec)
        .when()
        .get("/users?page=2")
        .then()
        .spec(listUserResponseSpec)
        .extract().as(ListUserModel.class);

    assertThat(response.getTotal()).isEqualTo(12);

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

