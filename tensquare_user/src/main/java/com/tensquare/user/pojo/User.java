package com.tensquare.user.pojo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName User
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年04月09日 23:55
 * @Version 1.0.0
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("tb_user")
public class User implements Serializable {
  @TableId(type = IdType.INPUT)
  private String id;
  private String mobile;
  private String password;
  private String nickname;
  private String sex;
  private Date birthday;
  private String avatar;
  private String email;
  private Date regdate;
  private Date updatedate;
  private Date lastdate;
  private Long online;
  private String interest;
  private String personality;
  private Integer fanscount;
  private Integer followcount;
}
