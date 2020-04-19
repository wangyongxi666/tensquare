package entity;

import lombok.*;

import java.util.List;

/**
 * @ClassName PageResult
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年04月08日 0:34
 * @Version 1.0.0
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageResult<T> {

  private Long total;
  private List<T> rows;

}