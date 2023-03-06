package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class ListUserModel {
  private Integer page;
  @JsonProperty("per_page")
  private Integer perPage;
  private Integer total;
  @JsonProperty("total_pages")
  private Integer totalPages;
  private List<DataModel> data;
  private Support support;

}
