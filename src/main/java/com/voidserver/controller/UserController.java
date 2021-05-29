package com.voidserver.controller;


import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.voidserver.common.LoginDto;
import com.voidserver.common.Result;
import com.voidserver.entity.Application;
import com.voidserver.entity.User;
import com.voidserver.service.UserService;
import com.voidserver.utils.JwtUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author void: ZHENHAO CHEN
 * @since 2021-04-03
 */
@RestController
//@RequestMapping("/user")
public class UserController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @Resource
    private JavaMailSenderImpl javaMailSenderImpl;


    @CrossOrigin
    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
//        Assert.notNull(user, "用户不存在");
        if (user == null || !user.getPassword().equals(loginDto.getPassword())) {
            return Result.fail("wrong username or password！");
        }
        String jwt = jwtUtils.generateToken(user.getId());
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        // 用户可以另一个接口
        return Result.succ(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("email", user.getEmail())
                .put("building", user.getBuilding())
                .put("room", user.getRoom())
                .map()
        );
    }

    @CrossOrigin
    @PostMapping("/sendVerifyCode")
    public Result getVerifyCode(@RequestBody String str) {
//        String reg = "/^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$/";

        String email = JSON.parseObject(str).get("email").toString();

//        if (!reg.matches(email)) {
//            System.out.println(email);
//            return Result.fail("失败");
//        }
        User user = new User();
        String verifyCode = String.valueOf(1000 + (int) (Math.random() * 9000)); // 随机验证码
        System.out.println(email);
        MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();
        try {
            // 开启文件上传
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            // 设置文件主题
            mimeMessageHelper.setSubject("您的物资领取验证码");
            // 设置文件内容 第二个参数设置是否支持html
            mimeMessageHelper.setText("<b style='color:red'>西浦教资管理系统验证码：</b>" + "<b>" + verifyCode + "</b>", true);
            // 设置发送到的邮箱
            mimeMessageHelper.setTo(email);
            // 设置发送人和配置文件中邮箱一致
            mimeMessageHelper.setFrom("XJTLU-VOID" + '<' + "136853349@qq.com" + '>');
            // 上传附件
            // mimeMessageHelper.addAttachment("", new File(""));
        } catch (MessagingException e) {
            return Result.fail("失败");
        }
        javaMailSenderImpl.send(mimeMessage);
        user.setVerifyCode(verifyCode);
        user.setCreated(null);
        user.setUsername("");
        user.setPassword("");
        user.setLastLogin(null);
        user.setStatus(0);
        user.setEmail(email);

        userService.save(user);
        return Result.succ("成功");
    }

    @CrossOrigin
    @PostMapping("/addUser")
    public Result addUser(@RequestBody String str) {
        String email = JSON.parseObject(str).get("email").toString();
        String username = JSON.parseObject(str).get("username").toString();
        String password = JSON.parseObject(str).get("password").toString();
        String building = JSON.parseObject(str).get("building").toString();
        String room = JSON.parseObject(str).get("room").toString();
        String verifyCode = JSON.parseObject(str).get("verifyCode").toString();
        LocalDateTime localDateTime = LocalDateTime.now();

        User user = userService.getOne(new QueryWrapper<User>().eq("email", email).eq("verify_code", verifyCode));
        if (user == null) {
            return Result.fail("失败");
        }

        user.setUsername(username);
        user.setPassword(password);
        user.setCreated(localDateTime);
        user.setBuilding(building);
        user.setRoom(room);
        user.setLastLogin(localDateTime);
        userService.update(user, new QueryWrapper<User>().eq("email", email).eq("verify_code", verifyCode));
        return Result.succ("成功");

    }


}
