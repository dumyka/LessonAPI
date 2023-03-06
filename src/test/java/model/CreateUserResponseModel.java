package model;

import lombok.Data;

@Data
public class CreateUserResponseModel {
  private String name;
  private String job;
  private Integer id;
  private String createdAt;
}
