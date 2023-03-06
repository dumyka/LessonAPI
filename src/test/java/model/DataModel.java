package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DataModel {
  private Integer id;
  private String email;
  @JsonProperty("first_name")
  private String firstName;
  @JsonProperty("last_name")
  private String lastName;
  private String avatar;

}
