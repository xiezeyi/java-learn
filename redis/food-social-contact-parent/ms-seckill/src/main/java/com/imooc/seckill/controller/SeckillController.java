package com.imooc.seckill.controller;


import com.imooc.comons.model.domain.domain.ResultInfo;
import com.imooc.comons.model.domain.pojo.SeckillVouchers;
import com.imooc.comons.utils.ResultInfoUtil;
import com.imooc.seckill.service.SeckillService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 秒杀控制层
 */
@RestController
public class SeckillController {

    @Resource
    private SeckillService seckillService;
    @Resource
    private HttpServletRequest request;

    /**
     * 秒杀下单
     *
     * @param voucherId
     * @param access_token
     * @return
     */
//    @PostMapping("{voucherId}")
//    public ResultInfo<String> doSeckill(@PathVariable Integer voucherId, String access_token) {
//        ResultInfo resultInfo = seckillService.doSeckill(voucherId, access_token, request.getServletPath());
//        return resultInfo;
//    }

    /**
     * 新增秒杀活动
     *
     * @param seckillVouchers
     * @return
     */
    @PostMapping("add")
    public ResultInfo<String> addSeckillVouchers(@RequestBody SeckillVouchers seckillVouchers) {
        seckillService.addSeckillVouchers(seckillVouchers);
        return ResultInfoUtil.buildSuccess(request.getServletPath(),
                "添加成功");
    }

}