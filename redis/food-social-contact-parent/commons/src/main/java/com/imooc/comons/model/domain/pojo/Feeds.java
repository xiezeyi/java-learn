package com.imooc.comons.model.domain.pojo;

import com.imooc.comons.model.domain.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
/**
 * @program: redis
 * @description: Feed信息类
 * @author: 谢泽毅
 * @create: 2021-08-06 11:16
 **/
@Getter
@Setter
@ApiModel(description = "Feed信息类")
public class Feeds extends BaseModel {

    @ApiModelProperty("内容")
    private String content;
    @ApiModelProperty("食客")
    private Integer fkDinerId;
    @ApiModelProperty("点赞")
    private int praiseAmount;
    @ApiModelProperty("评论")
    private int commentAmount;
    @ApiModelProperty("关联的餐厅")
    private Integer fkRestaurantId;

}

