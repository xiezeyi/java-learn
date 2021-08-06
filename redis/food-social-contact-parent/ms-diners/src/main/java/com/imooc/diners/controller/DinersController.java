package com.imooc.diners.controller;

import com.imooc.comons.model.domain.domain.ResultInfo;
import com.imooc.comons.model.domain.dto.DinersDTO;
import com.imooc.comons.model.domain.vo.ShortDinerInfo;
import com.imooc.comons.utils.ResultInfoUtil;
import com.imooc.diners.service.DinersService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: redis
 * @description: 食客服务控制层
 * @author: 谢泽毅
 * @create: 2021-08-02 11:16
 **/
@RestController
@Api(tags = "食客相关接口")
public class DinersController {

    @Resource
    private DinersService dinersService;

    @Resource
    private HttpServletRequest request;

    @PostMapping("register")
    public ResultInfo register(@RequestBody DinersDTO dinersDTO){
        return dinersService.register(dinersDTO, request.getServletPath());
    }

    /**
     * 根据 ids 查询食客信息
     *
     * @param ids
     * @return
     */
    @GetMapping("findByIds")
    public ResultInfo<List<ShortDinerInfo>> findByIds(String ids) {
        List<ShortDinerInfo> dinerInfos = dinersService.findByIds(ids);
        return ResultInfoUtil.buildSuccess(request.getServletPath(), dinerInfos);
    }
    /**
     * 校验手机号是否已注册
     * @param phone
     * @return
     */
    @GetMapping("checkPhone")
    public ResultInfo checkPhone(String phone) {
        dinersService.checkPhoneIsRegistered(phone);
        return ResultInfoUtil.buildSuccess(request.getServletPath());
    }

    @GetMapping("signin")
    public ResultInfo signIn(String account, String password) {
        return dinersService.signIn(account, password, request.getServletPath());
    }

}
