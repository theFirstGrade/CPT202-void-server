package com.voidserver.controller;


import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.voidserver.common.*;
import com.voidserver.entity.Depository;
import com.voidserver.entity.RentalDepository;
import com.voidserver.entity.RentalProduct;
import com.voidserver.entity.User;
import com.voidserver.service.DepositoryService;
import com.voidserver.service.RentalDepositoryService;
import com.voidserver.service.RentalProductService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author void: ZHENHAO CHEN
 * @since 2021-04-25
 */
@RestController
public class RentalProductController {

    @Autowired
    RentalProductService rentalProductService;

    @Autowired
    RentalDepositoryService rentalDepositoryService;

    @RequiresAuthentication
    @CrossOrigin
    @GetMapping("/getRentalProducts")
    public Result getRentalProducts(Integer currentPage, String searchCate, String searchAddress, String searchName) {
        if (currentPage == null || currentPage < 1) currentPage = 1;
        Page<RentalProductVO> productDepository;
        System.out.println(searchCate + "---" + searchAddress + "---" + searchName);
        if (searchCate.equals("全部") || searchCate.equals("All")) {
            if (searchName.equals("")) {
                // SELECT from a.category=searchCate and a.name=
                productDepository = rentalProductService.getRentalProductDepository1(new Page<>(currentPage, 5), searchAddress);
            } else {
                productDepository = rentalProductService.getRentalProductDepository2(new Page<>(currentPage, 5), searchAddress, searchName);
            }
        } else {
            if (searchName.equals("")) {
                productDepository = rentalProductService.getRentalProductDepository3(new Page<>(currentPage, 5), searchAddress, searchCate);
            } else {
                productDepository = rentalProductService.getRentalProductDepository4(new Page<>(currentPage, 5), searchAddress, searchCate, searchName);
            }
        }

        return Result.succ(productDepository);
    }

    /**
     * 暂时无用
     *
     * @param str
     * @return
     */
    @RequiresAuthentication
    @CrossOrigin
    @PostMapping("/rentalProduct/add")
    public Result productAdd(@RequestBody String str) {
        Integer userId = Integer.parseInt(JSON.parseObject(str).get("userId").toString());
//        RentalProduct data = (RentalProduct) JSON.parseObject(str).get("data");
//        JSONObject data = (JSONObject) JSON.parseObject(str).get("data");
        List<RentalProduct> data = JSON.parseArray(JSON.parseObject(str).getString("data"), RentalProduct.class);
        RentalProduct rp = new RentalProduct();
        boolean flag = false;
//        QueryWrapper<RentalProduct> rentalProductQueryWrapper = new QueryWrapper<>();
        System.out.println(data.get(0).getRentalName());
        RentalProduct product = rentalProductService.getOne(new QueryWrapper<RentalProduct>().eq("rental_name", data.get(0).getRentalName()));
        if (product == null) {
            rp.setRentalName(data.get(0).getRentalName());
            rp.setCategory(data.get(0).getCategory());
            rp.setUnit(data.get(0).getUnit());
            rp.setImageSrc(data.get(0).getImageSrc());
            flag = rentalProductService.save(rp);
        }

//        rentalProductQueryWrapper.eq("rental_name", data.get(0).getRentalName());
        if (flag) {
            return Result.succ("成功");
        }
        return Result.fail("失败");
    }

//    @CrossOrigin
//    @PostMapping("/addProduct")
//    public Result uploadFile(String productName, MultipartHttpServletRequest productImage){
////        return userService.uploadFile(Objects.requireNonNull(request.getFile("file")), request.getParameter("type"), request.getParameter("description"), 1L);
//        System.out.println(productImage);
//        return Result.succ("成功");
//    }

    @RequiresAuthentication
    @CrossOrigin
    @PostMapping("/upload")
    public Result upload(HttpServletRequest request) throws IOException {
        String contentType = request.getContentType();//通过request获取请求类型
        String path = "E:\\idea\\void-web\\CPT202-void\\public\\images";
        String url = "";
        Map<String, Object> map = new HashMap<String, Object>();
        if (contentType != null && contentType.toLowerCase().startsWith("multipart/")) {//如果请求类型为multipart，获取里面的文件信息
            MultipartHttpServletRequest multipartRequest =
                    WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
//            MultipartFile productName = multipartRequest.getFile("productName");
//            MultipartFile productImage = multipartRequest.getFile("productImage");

//            for (Iterator<String> it = multipartRequest.getFileNames(); it.hasNext(); ) {
//                String str = it.next();
//                System.out.println(str);
            String fileName = Objects.requireNonNull(multipartRequest.getFile("image")).getOriginalFilename();
//                System.out.println(fileName);
//            }
            Long milliSecond = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
            String[] split = fileName.split("\\.");
            url = path + "\\" + String.valueOf(milliSecond) + "." + split[1];
            File file1 = new File(url);
            if (!file1.exists()) {
                file1.createNewFile();
            }
            Objects.requireNonNull(multipartRequest.getFile("image")).transferTo(file1);
            map.put("url", url);
            return Result.succ(map);   //返回前端传递的文件信息
        } else {
            return Result.fail("失败");
        }
    }

    //String productName, String productImage, String productUnit, String productCate, String productAddress
    @RequiresAuthentication
    @CrossOrigin
    @PostMapping("/addRentalProduct")
    public Result addRentalProduct(@RequestBody String str) {
        String productName = String.valueOf(JSON.parseObject(str).get("productName").toString());
        String productImage = String.valueOf(JSON.parseObject(str).get("productImage").toString());
        String productUnit = String.valueOf(JSON.parseObject(str).get("productUnit").toString());
        String productCate = String.valueOf(JSON.parseObject(str).get("productCate").toString());
        String productAddress = String.valueOf(JSON.parseObject(str).get("productAddress").toString());

        System.out.println(productName + "----");
        System.out.println(productImage + "----");
        // 可优化
        boolean flag = true;
        RentalProduct rentalProduct = rentalProductService.getOne(new QueryWrapper<RentalProduct>().eq("rental_name", productName));
        if (rentalProduct == null) {
            RentalProduct rp = new RentalProduct();
            rp.setRentalName(productName);
            rp.setImageSrc(productImage);
            rp.setUnit(productUnit);
            rp.setCategory(productCate);
            flag = rentalProductService.save(rp);
        }
        if (flag) {
            RentalProduct rentalProduct1 = rentalProductService.getOne(new QueryWrapper<RentalProduct>().eq("rental_name", productName));
            RentalDepository rentalDepository = rentalDepositoryService.getOne(new QueryWrapper<RentalDepository>().eq("rental_id", rentalProduct1.getRentalId()).eq("address", productAddress));
            if (rentalDepository == null) {
                RentalDepository rd = new RentalDepository();
                rd.setAddress(productAddress);
                rd.setStock(0);
                rd.setRentalId(rentalProduct1.getRentalId());
                boolean addSuccess = rentalDepositoryService.save(rd);
                if (addSuccess) {
                    return Result.succ("成功");
                } else {
                    return Result.fail("失败");
                }
            } else {
                return Result.fail("失败");
            }
        } else {
            return Result.fail("失败");
        }
    }

}
