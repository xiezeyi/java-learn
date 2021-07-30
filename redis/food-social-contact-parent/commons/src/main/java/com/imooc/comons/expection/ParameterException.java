package com.imooc.comons.expection;

import com.imooc.comons.constant.ApiConstant;
import lombok.Getter;
import lombok.Setter;



/**
 * @program: redis
 * @description: 全局异常类
 * @author: 谢泽毅
 * @create: 2021-07-30 12:00
 **/
@Getter
@Setter
public class ParameterException extends RuntimeException {

    private Integer errorCode;

    public ParameterException() {
        super(ApiConstant.ERROR_MESSAGE);
        this.errorCode = ApiConstant.ERROR_CODE;
    }

    public ParameterException(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public ParameterException(String message) {
        super(message);
        this.errorCode = ApiConstant.ERROR_CODE;
    }

    public ParameterException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

}
