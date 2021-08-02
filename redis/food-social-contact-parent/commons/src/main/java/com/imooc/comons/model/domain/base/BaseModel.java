package com.imooc.comons.model.domain.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: redis
 * @description: 实体对象公共属性
 * @author: 谢泽毅
 * @create: 2021-07-30 16:43
 **/
@Getter
@Setter
public class BaseModel implements Serializable {

    private Integer id;
    private Date createDate;
    private Date updateDate;
    private int isValid;

}