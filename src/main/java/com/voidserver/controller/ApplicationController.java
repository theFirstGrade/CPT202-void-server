package com.voidserver.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.voidserver.common.OrderDto;
import com.voidserver.common.Result;
import com.voidserver.entity.Application;
import com.voidserver.mapper.ApplicationMapper;
import com.voidserver.mapper.UserMapper;
import com.voidserver.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;

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

    @Resource
    ApplicationMapper applicationMapper;

    @CrossOrigin
    @PostMapping("/application")
    public Result makeApplication(@RequestBody String str) {
        int verifyCode = 1000 + (int) (Math.random() * 9000); // 随机验证码
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
            application.setVerifyCode(verifyCode);
            application.setUnit(item.getUnit());
            application.setDeleted(0);
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

    @CrossOrigin
    @GetMapping("/getApplication")
    public Result getApplication(Integer currentPage) {
        if (currentPage == null || currentPage < 1) currentPage = 1;
        Page page = new Page(currentPage, 9);
        IPage pageData = applicationService.page(page, new QueryWrapper<Application>().orderByDesc("applicationId"));
        return Result.succ(pageData);
    }

    @CrossOrigin
    @GetMapping("/getApplication/search")
    public Result search(Integer currentPage, String searchAddress, Integer userId) {
        if (currentPage == null || currentPage < 1) currentPage = 1;
        QueryWrapper<Application> sectionQueryWrapper = new QueryWrapper<>();
        if (!searchAddress.equals("全部")) {
            sectionQueryWrapper.eq("address", searchAddress);
        }

        if (userId != null) {
            sectionQueryWrapper.eq("userId", userId);
        }

        Page page = new Page(currentPage, 9);
        IPage pageData = applicationService.page(page, sectionQueryWrapper);
        return Result.succ(pageData);
    }

    @CrossOrigin
    @PostMapping("/verifyApplication")
    public Result verifyApplication(@RequestBody String str) {
        boolean correctApplication = true;
        // 暂时没考虑传null值
        Integer verifyCode = Integer.parseInt(JSON.parseObject(str).get("verifyCode").toString());
        JSONArray applicationIds = JSON.parseArray(JSON.parseObject(str).getString("applicationId"));
        System.out.println(applicationIds);
        if (applicationIds.size() <= 0) {
            correctApplication = false;
        } else {
            for (Object applicationId : applicationIds) {
                Application application = applicationService.getById((Serializable) applicationId);
                if (application != null) {
                    if (!application.getVerifyCode().equals(verifyCode)) {
                        correctApplication = false;
                    }
                } else {
                    correctApplication = false;
                }
            }
        }

        int count = 0;
        if (correctApplication) {
            for (Object applicationId : applicationIds) {
                count = applicationMapper.deleteById((Serializable) applicationId);
//                count = applicationMapper.deleteBatchIds(Collections.singletonList((Serializable) applicationIds));
                System.out.println(count);
            }
        }

        if (count != 0) {
            return Result.succ("成功");
        } else {
            return Result.fail("失败");
        }
    }

}
