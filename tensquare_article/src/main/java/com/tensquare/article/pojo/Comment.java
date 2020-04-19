package com.tensquare.article.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName Comment
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年04月08日 23:18
 * @Version 1.0.0
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "comment")
public class Comment implements Serializable {
  @Id
  private String _id;
  private String articleid;
  private String content;
  private String userid;
  private String parentid;
  private Date publishdate;
  private Integer thumbup;
}
