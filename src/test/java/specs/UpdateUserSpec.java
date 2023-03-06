package specs;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class UpdateUserSpec {
  public static RequestSpecification updatedUserRequestSpec = with()
      .log().uri()
      .log().headers()
      .log().body()
      .filter(withCustomTemplates())
      .contentType(JSON)
      .baseUri("https://reqres.in")
      .basePath("/api");

  public static ResponseSpecification updatedUserResponseSpec = new ResponseSpecBuilder()
      .log(STATUS)
      .log(BODY)
      .expectStatusCode(200)
      .build();
}
