

package com.imooc.seckill.hander;

import com.imooc.comons.expection.ParameterException;
import com.imooc.comons.model.domain.domain.ResultInfo;
import com.imooc.comons.utils.ResultInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @program: redis
 * @description: 全局统一异常处理类
 * 本来在返回时，会将状态码信息返回
 * 例如在配置完该类后会在手机号不存在时，不再返回500，而是返回该手机号未注册
 * @author: 谢泽毅
 * @create: 2021-08-03 09:03
 **/
@RestControllerAdvice // 将输出的内容写入 ResponseBody 中
//https://blog.csdn.net/qq_35098526/article/details/88949425
@Slf4j
public class GlobalExceptionHandler {

    @Resource
    private HttpServletRequest request;

    @ExceptionHandler(ParameterException.class)
    public ResultInfo<Map<String, String>> handlerParameterException(ParameterException ex) {
        String path = request.getRequestURI();
        ResultInfo<Map<String, String>> resultInfo =
                ResultInfoUtil.buildError(ex.getErrorCode(), ex.getMessage(), path);
        return resultInfo;
    }

    @ExceptionHandler(Exception.class)
    public ResultInfo<Map<String, String>> handlerException(Exception ex) {
        log.info("未知异常：{}", ex);
        String path = request.getRequestURI();
        ResultInfo<Map<String, String>> resultInfo =
                ResultInfoUtil.buildError(path);
        return resultInfo;
    }

}