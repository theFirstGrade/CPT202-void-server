package com.voidserver.controller;


import cn.hutool.db.sql.Order;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.voidserver.common.OrderDto;
import com.voidserver.common.Result;
import com.voidserver.entity.Application;
import com.voidserver.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author void: ZHENHAO CHEN
 * @since 2021-04-05
 */
@RestController
//@RequestMapping("/application")
public class ApplicationController {

    @Autowired
    ApplicationService applicationService;

    @CrossOrigin
    @PostMapping("/application")
    public Result depository(@RequestBody String str) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Application application;

        Integer userId = Integer.parseInt(JSON.parseObject(str).get("userId").toString());
        // {"水性笔（黑）1":{"number":4,"unit":"支","address":"新兴科学楼（南校区）","productId":2,"director":"Sterling"},"笔记本":{"number":2,"unit":"本","address":"理科楼（北校区）","productId":1,"director":"Erick"}}
        //[{"number":2,"unit":"本","address":"理科楼（北校区）","productId":1,"director":"Erick","name":"笔记本"},{"number":4,"unit":"支","address":"新兴科学楼（南校区）","productId":2,"director":"Sterling","name":"水性笔（黑）1"}]

        List<OrderDto> data = JSON.parseArray(JSON.parseObject(str).getString("data"), OrderDto.class);
        List<Application> applications = new ArrayList<>();
        for (OrderDto item : data) {
//            System.out.println(item.getProductId() + "---" + item.getProductName() + "---" + item.getNumber());
            application = new Application();
            application.setProductId(item.getProductId());
            application.setProductName(item.getProductName());
            application.setNumber(item.getNumber());
            application.setDate(localDateTime);
            application.setAddress(item.getAddress());
            application.setUserId(userId);
            application.setDirector("Erick");

            applications.add(application);
            System.out.println(application);
        }

        boolean affectRows = applicationService.saveBatch(applications);
        System.out.println(affectRows);

        if (affectRows) {
            return Result.succ("成功");
        } else {
            return Result.fail("失败");
        }

    }

}
