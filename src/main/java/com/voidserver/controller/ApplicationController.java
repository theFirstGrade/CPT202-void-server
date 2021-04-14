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
import com.voidserver.common.UserDto;
import com.voidserver.entity.Application;
import com.voidserver.mapper.ApplicationMapper;
import com.voidserver.mapper.UserMapper;
import com.voidserver.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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

    @Resource
    private JavaMailSenderImpl javaMailSenderImpl;


    @GetMapping("/sendEmail")
    public void sendSimpleMail() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // 设置邮件主题
        simpleMailMessage.setSubject("账号激活");
        // 设置要发送的邮件内容
        simpleMailMessage.setText("hello!");
        // 要发送的目标邮箱
        simpleMailMessage.setTo("ZHENHAO.CHEN18@student.xjtlu.edu.cn");
        // 发送者邮箱和配置文件中的邮箱一致
//        simpleMailMessage.setFrom("136853349@qq.com");
        simpleMailMessage.setFrom("XJTLU-VOID" + '<' + "136853349@qq.com" + '>');
        javaMailSenderImpl.send(simpleMailMessage);
    }


    @CrossOrigin
    @PostMapping("/application")
    public Result makeApplication(@RequestBody String str) {
        int verifyCode = 1000 + (int) (Math.random() * 9000); // 随机验证码
        LocalDateTime localDateTime = LocalDateTime.now();
        Application application;

        Integer userId = Integer.parseInt(JSON.parseObject(str).get("userId").toString());
        String email = JSON.parseObject(str).get("email").toString();

        // {"水性笔（黑）1":{"number":4,"unit":"支","address":"新兴科学楼（南校区）","productId":2,"director":"Sterling"},"笔记本":{"number":2,"unit":"本","address":"理科楼（北校区）","productId":1,"director":"Erick"}}
        //[{"number":2,"unit":"本","address":"理科楼（北校区）","productId":1,"director":"Erick","name":"笔记本"},{"number":4,"unit":"支","address":"新兴科学楼（南校区）","productId":2,"director":"Sterling","name":"水性笔（黑）1"}]

        List<OrderDto> data = JSON.parseArray(JSON.parseObject(str).getString("data"), OrderDto.class);
        List<Application> applications = new ArrayList<>();
//        List<OrderDto> orderDtoList = new ArrayList<>();
        StringBuilder orderMessage = new StringBuilder();
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
            orderMessage.append(item.getProductName()).append("&nbsp;&nbsp;&nbsp;&nbsp;").append(item.getNumber()).append(item.getUnit()).append("&nbsp;&nbsp;&nbsp;&nbsp;").append(item.getAddress()).append("<br/>");
        }
        System.out.println(orderMessage);
        boolean affectRows = applicationService.saveBatch(applications);
        System.out.println(affectRows);

        if (affectRows) {

            MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();
            try {
                // 开启文件上传
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                // 设置文件主题
                mimeMessageHelper.setSubject("您的物资领取验证码");
                // 设置文件内容 第二个参数设置是否支持html
                mimeMessageHelper.setText("<b style='color:red'>您的订单：</b><br/>" + orderMessage + "<br/>" + "<b style='color:red'>验证码：</b>" + "<b>" + verifyCode + "</b>", true);
                // 设置发送到的邮箱
                mimeMessageHelper.setTo(email);
                // 设置发送人和配置文件中邮箱一致
                mimeMessageHelper.setFrom("XJTLU-VOID" + '<' + "136853349@qq.com" + '>');
                // 上传附件
                // mimeMessageHelper.addAttachment("", new File(""));
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            javaMailSenderImpl.send(mimeMessage);


//            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//            // 设置邮件主题
//            simpleMailMessage.setSubject("您的物资领取验证码");
//            // 设置要发送的邮件内容
//            simpleMailMessage.setText("<b style='color:red'>您的订单：</b>" + "验证码：" + verifyCode);
//            // 要发送的目标邮箱
//            simpleMailMessage.setTo(email);
//            // 发送者邮箱和配置文件中的邮箱一致
//            simpleMailMessage.setFrom("XJTLU-VOID" + '<' + "136853349@qq.com" + '>');
//            javaMailSenderImpl.send(simpleMailMessage);
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
    public Result search(Integer currentPage, String searchAddress, Integer verifyCode) {
        if (currentPage == null || currentPage < 1) currentPage = 1;
        QueryWrapper<Application> sectionQueryWrapper = new QueryWrapper<>();
        if (!searchAddress.equals("全部")) {
            sectionQueryWrapper.eq("address", searchAddress);
        }

        if (verifyCode != null) {
            sectionQueryWrapper.eq("verifyCode", verifyCode);
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
                count += applicationMapper.deleteById((Serializable) applicationId);
//                count = applicationMapper.deleteBatchIds(Collections.singletonList((Serializable) applicationIds));
                System.out.println(count);
            }
        }

        if (correctApplication && count == applicationIds.size()) {
            return Result.succ("成功");
        } else {
            return Result.fail("失败");
        }
    }

}
