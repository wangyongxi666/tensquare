package com.tensquare.notice.pojo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

@Data
@TableName("tb_notice_fresh")
public class NoticeFresh {

  @TableId(type = IdType.INPUT)
  private String userId;
  private String noticeId;
}