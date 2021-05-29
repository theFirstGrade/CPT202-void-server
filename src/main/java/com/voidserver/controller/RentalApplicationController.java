package com.voidserver.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.voidserver.common.*;
import com.voidserver.entity.Application;
import com.voidserver.entity.Depository;
import com.voidserver.entity.RentalApplication;
import com.voidserver.entity.RentalDepository;
import com.voidserver.mapper.ApplicationMapper;
import com.voidserver.mapper.RentalApplicationMapper;
import com.voidserver.service.RentalApplicationService;
import com.voidserver.service.RentalDepositoryService;
import com.voidserver.utils.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author void: ZHENHAO CHEN
 * @since 2021-04-25
 */
@RestController
public class RentalApplicationController {
    @Resource
    private JavaMailSenderImpl javaMailSenderImpl;

    @Autowired
    RentalApplicationService rentalApplicationService;

    @Autowired
    RentalDepositoryService rentalDepositoryService;

    @Resource
    RentalApplicationMapper rentalApplicationMapper;

    @RequiresAuthentication
    @CrossOrigin
    @GetMapping("/getRentalApplication")
    public Result getRentalApplication(Integer currentPage) {
        if (currentPage == null || currentPage < 1) currentPage = 1;
        Page<RentalApplicationVO> page = rentalApplicationService.getApplicationUser(new Page<>(currentPage, 9));

        return Result.succ(page);
    }

    @RequiresAuthentication
    @CrossOrigin
    @GetMapping("/getReturnRentalApplication")
    public Result getReturnRentalApplication(Integer currentPage) {
        if (currentPage == null || currentPage < 1) currentPage = 1;
        Page<RentalApplicationVO> page = rentalApplicationService.getReturnApplicationUser1(new Page<>(currentPage, 9));

        return Result.succ(page);
    }

    @RequiresAuthentication
    @CrossOrigin
    @GetMapping("/getRentalApplication/search")
    public Result search(Integer currentPage, String searchAddress, Integer verifyCode) {
        if (currentPage == null || currentPage < 1) currentPage = 1;
        Page<RentalApplicationVO> page;
        if (searchAddress.equals("全部") || searchAddress.equals("All")) {
            page = rentalApplicationService.getApplicationUser4(new Page<>(currentPage, 9), verifyCode);
        } else {
            if (verifyCode != null) {
                page = rentalApplicationService.getApplicationUser2(new Page<>(currentPage, 9), searchAddress, verifyCode);
            } else {
                page = rentalApplicationService.getApplicationUser3(new Page<>(currentPage, 9), searchAddress);
            }
        }
        return Result.succ(page);
    }

    @RequiresAuthentication
    @CrossOrigin
    @GetMapping("/getReturnRentalApplication/search")
    public Result returnSearch(Integer currentPage, String searchAddress, Integer verifyCode) {
        if (currentPage == null || currentPage < 1) currentPage = 1;
        Page<RentalApplicationVO> page;
        if (searchAddress.equals("全部") || searchAddress.equals("All")) {
            page = rentalApplicationService.getReturnApplicationUser4(new Page<>(currentPage, 9), verifyCode);
        } else {
            if (verifyCode != null) {
                page = rentalApplicationService.getReturnApplicationUser2(new Page<>(currentPage, 9), searchAddress, verifyCode);
            } else {
                page = rentalApplicationService.getReturnApplicationUser3(new Page<>(currentPage, 9), searchAddress);
            }
        }
        return Result.succ(page);
    }

    @RequiresAuthentication
    @CrossOrigin
    @GetMapping("/getPersonalRentalApplication")
    public Result getPersonalApplication(Integer currentPage, Integer id, String searchAddress, String searchCompleted) {
        if (currentPage == null || currentPage < 1) currentPage = 1;
        Page<UserRentalApplication> userRentalApplicationPage = null;
//        Integer isCompleted = "completed".equals(searchCompleted) ? 1 : 0;
        if (!searchAddress.equals("All")) {
            if (!"completed".equals(searchCompleted)) {
                userRentalApplicationPage = rentalApplicationService.getUserRentalApplication1(new Page<>(currentPage, 9), id, searchAddress, 0);
            } else {
                userRentalApplicationPage = rentalApplicationService.getUserRentalApplication3(new Page<>(currentPage, 9), id, searchAddress, 0);
            }
        } else {
            if (!"completed".equals(searchCompleted)) {

                userRentalApplicationPage = rentalApplicationService.getUserRentalApplication2(new Page<>(currentPage, 9), id, 0);
            } else {
                userRentalApplicationPage = rentalApplicationService.getUserRentalApplication4(new Page<>(currentPage, 9), id, 0);

            }
        }

        return Result.succ(userRentalApplicationPage);

    }

    @RequiresAuthentication
    @CrossOrigin
    @PostMapping("/cancelRentalApplication")
    public Result cancelRentalApplication(@RequestBody String str) {
        Integer applicationId = Integer.parseInt(JSON.parseObject(str).get("applicationId").toString());
        RentalApplication application = rentalApplicationService.getById(applicationId);
        if (application != null && (long) application.getUserId() == ShiroUtil.getProfile().getId() && application.getDeleted() == 0) {
            application.setDeleted(5);
            rentalApplicationService.update(application, new QueryWrapper<RentalApplication>().eq("application_id", applicationId));
            return Result.succ("成功");
        }

        return Result.fail("失败");
    }


    @RequiresAuthentication
    @CrossOrigin
    @PostMapping("/verifyRentalApplication")
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
                RentalApplication application = rentalApplicationService.getById((Serializable) applicationId);
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
//                count += rentalApplicationMapper.deleteById((Serializable) applicationId);
                RentalApplication application = rentalApplicationService.getById((Serializable) applicationId);
                application.setDeleted(1);
                rentalApplicationService.update(application, new QueryWrapper<RentalApplication>().eq("application_id", applicationId));
                count++;
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


    @RequiresAuthentication
    @CrossOrigin
    @PostMapping("/verifyReturnRentalApplication")
    public Result verifyReturnApplication(@RequestBody String str) {
        boolean correctApplication = true;
        // 暂时没考虑传null值
        Integer verifyCode = Integer.parseInt(JSON.parseObject(str).get("verifyCode").toString());
        JSONArray applicationIds = JSON.parseArray(JSON.parseObject(str).getString("applicationId"));
        System.out.println(applicationIds);
        if (applicationIds.size() <= 0) {
            correctApplication = false;
        } else {
            for (Object applicationId : applicationIds) {
                RentalApplication application = rentalApplicationService.getById((Serializable) applicationId);
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
//                count += rentalApplicationMapper.deleteById((Serializable) applicationId);
                RentalApplication application = rentalApplicationService.getById((Serializable) applicationId);
                application.setDeleted(2);
                rentalApplicationService.update(application, new QueryWrapper<RentalApplication>().eq("application_id", applicationId));
                count++;
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

    @RequiresAuthentication
    @CrossOrigin
    @PostMapping("/rentalApplication")
    public Result makeRentalApplication(@RequestBody String str) {
        int verifyCode = 1000 + (int) (Math.random() * 9000); // 随机验证码
        LocalDateTime localDateTime = LocalDateTime.now();
        RentalApplication application;

        Integer userId = Integer.parseInt(JSON.parseObject(str).get("userId").toString());
        String email = JSON.parseObject(str).get("email").toString();

        // {"水性笔（黑）1":{"number":4,"unit":"支","address":"新兴科学楼（南校区）","productId":2,"director":"Sterling"},"笔记本":{"number":2,"unit":"本","address":"理科楼（北校区）","productId":1,"director":"Erick"}}
        //[{"number":2,"unit":"本","address":"理科楼（北校区）","productId":1,"director":"Erick","name":"笔记本"},{"number":4,"unit":"支","address":"新兴科学楼（南校区）","productId":2,"director":"Sterling","name":"水性笔（黑）1"}]

        List<RentalOrderDto> data = JSON.parseArray(JSON.parseObject(str).getString("data"), RentalOrderDto.class);
        List<RentalApplication> applications = new ArrayList<>();
//        List<OrderDto> orderDtoList = new ArrayList<>();
        StringBuilder orderMessage = new StringBuilder();
        for (RentalOrderDto item : data) {
//            System.out.println(item.getProductId() + "---" + item.getProductName() + "---" + item.getNumber());
            application = new RentalApplication();
            application.setDepositoryId(item.getDepositoryId());
            application.setNumber(item.getNumber());
            application.setCreated(localDateTime);
            application.setAddress(item.getAddress());
            application.setUserId(userId);
            application.setDirector("Erick");
            application.setVerifyCode(verifyCode);
            application.setRentalDay(item.getDays());
            application.setDeleted(0);
            application.setExpired(LocalDate.from(localDateTime.plusDays(item.getDays())));
            applications.add(application);
            System.out.println(application);
            orderMessage.append(item.getRentalName()).append("&nbsp;&nbsp;&nbsp;&nbsp;").append(item.getNumber()).append(item.getUnit()).append("&nbsp;&nbsp;&nbsp;&nbsp;").append(item.getDays()).append("天").append("&nbsp;&nbsp;&nbsp;&nbsp;").append(item.getAddress()).append("<br/>");

            RentalDepository rd = rentalDepositoryService.getById(item.getDepositoryId());
            Integer stock = rd.getStock();
            rd.setStock(stock - item.getNumber());
            rentalDepositoryService.update(rd, new QueryWrapper<RentalDepository>().eq("depository_id", item.getDepositoryId()));
        }
        System.out.println(orderMessage);
        boolean affectRows = rentalApplicationService.saveBatch(applications);
        System.out.println(affectRows);

        if (affectRows) {

            MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();
            try {
                // 开启文件上传
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                // 设置文件主题
                mimeMessageHelper.setSubject("您的物资领取验证码");
                // 设置文件内容 第二个参数设置是否支持html
                mimeMessageHelper.setText("<b style='color:red'>您的租借订单：</b><br/>" + orderMessage + "<br/>" + "<b style='color:red'>验证码：</b>" + "<b>" + verifyCode + "</b>", true);
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
}
