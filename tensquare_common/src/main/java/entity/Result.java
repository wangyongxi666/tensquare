package entity;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.*;

/**
 * @ClassName Result
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年04月08日 0:03
 * @Version 1.0.0
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Result {

  private Boolean flag;
  private Integer code;
  private String message;
  private Object data;

  public Result(Boolean flag, Integer code, String message) {
    this.flag = flag;
    this.code = code;
    this.message = message;
  }

}
