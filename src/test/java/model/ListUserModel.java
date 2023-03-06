package model;

import java.util.List;
import lombok.Data;

@Data
public class ListUserModel {
  private Integer page;
  private Integer per_page;
  private Integer total;
  private Integer total_pages;
  private List<DataModel> data;
  private Support support;

}
