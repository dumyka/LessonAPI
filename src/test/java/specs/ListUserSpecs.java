package specs;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class ListUserSpecs {
  public static RequestSpecification listUserRequestSpec = with()
      .log().uri()
      .log().headers()
      .log().body()
      .filter(withCustomTemplates())
      .baseUri("https://reqres.in")
      .basePath("/api");

  public static ResponseSpecification listUserResponseSpec = new ResponseSpecBuilder()
      .log(STATUS)
      .log(BODY)
      .expectStatusCode(200)
      .build();
}
