package com.voidserver.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.voidserver.common.RentalProductVO;
import com.voidserver.common.Result;
import com.voidserver.entity.Depository;
import com.voidserver.entity.RentalDepository;
import com.voidserver.entity.RentalProduct;
import com.voidserver.service.DepositoryService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author void: ZHENHAO CHEN
 * @since 2021-04-03
 */
@RestController
//@RequestMapping("/depository")
public class DepositoryController {
    @Autowired
    DepositoryService depositoryService;


    // 测试用的
    @GetMapping("/{id}")
    public Object test(@PathVariable("id") Long id) {
        return depositoryService.getById(id);

    }


    /**
     * 根据前端传过来的页码来返回数据
     *
     * @param currentPage
     * @return
     */
    @RequiresAuthentication
    @GetMapping("/depository")
    public Result depository(Integer currentPage) {
        if (currentPage == null || currentPage < 1) currentPage = 1;
        Page page = new Page(currentPage, 5);
        IPage pageData = depositoryService.page(page, new QueryWrapper<Depository>().orderByAsc("productId"));
        return Result.succ(pageData);
    }

    /**
     * 根据前端传过来的页码，选择的物品类别，仓库的地址和名字的关键字来返回数据
     *
     * @param currentPage
     * @param searchCate
     * @param searchAddress
     * @param searchName
     * @return
     */
    @RequiresAuthentication
    @GetMapping("/depository/search")
    public Result search(Integer currentPage, String searchCate, String searchAddress, String searchName) {
        if (currentPage == null || currentPage < 1) currentPage = 1;
        QueryWrapper<Depository> sectionQueryWrapper = new QueryWrapper<>();
        if (!searchCate.equals("全部") && !searchCate.equals("All")) {
            sectionQueryWrapper.eq("category", searchCate);
        }
        if (!searchAddress.equals("全部") && !searchAddress.equals("All")) {
            sectionQueryWrapper.eq("address", searchAddress);
        }

        if (!searchName.equals("")) {
            sectionQueryWrapper.like("productName", searchName);
        }

        Page page = new Page(currentPage, 5);
        IPage pageData = depositoryService.page(page, sectionQueryWrapper);
        return Result.succ(pageData);
    }

//    @CrossOrigin
//    @PostMapping("/stock/change")
//    public Result stockChange(@RequestBody String str) {
//        int id = (int) JSON.parseObject(str).get("rentalDepositoryId");
//        int stock = (int) JSON.parseObject(str).get("rentalDepositoryStock");
//        System.out.println(id + "---" + stock);
//        RentalDepository rd = rentalDepositoryService.getById(id);
//        if (rd != null) {
//            rd.setStock(stock);
//            rentalDepositoryService.update(rd, new QueryWrapper<RentalDepository>().eq("depository_id", id));
//        }
//        return Result.succ("成功");
//    }=================

    @RequiresAuthentication
    @CrossOrigin
    @GetMapping("/getProducts")
    public Result getProducts(Integer currentPage, String searchCate, String searchAddress, String searchName) {
        if (currentPage == null || currentPage < 1) currentPage = 1;
//        Page<RentalProductVO> productDepository;
        QueryWrapper<Depository> sectionQueryWrapper = new QueryWrapper<Depository>().eq("address", searchAddress);
        if (!searchCate.equals("全部") && !searchCate.equals("All")) {
            sectionQueryWrapper.eq("category", searchCate);
        }

        if (!searchName.equals("")) {
            sectionQueryWrapper.like("productName", searchName);
        }

        Page page = new Page(currentPage, 5);
        IPage pageData = depositoryService.page(page, sectionQueryWrapper);
        return Result.succ(pageData);
    }

    @RequiresAuthentication
    @CrossOrigin
    @PostMapping("/stock/change")
    public Result stockChange(@RequestBody String str) {
        int id = (int) JSON.parseObject(str).get("depositoryId");
        int stock = (int) JSON.parseObject(str).get("depositoryStock");
        System.out.println(id + "---" + stock);
        Depository d = depositoryService.getById(id);
        if (d != null) {
            d.setStock(stock);
            depositoryService.update(d, new QueryWrapper<Depository>().eq("productId", id));
            return Result.succ("成功");
        }
        return Result.fail("失败");
    }

    @RequiresAuthentication
    @CrossOrigin
    @PostMapping("/addProduct")
    public Result addProduct(@RequestBody String str) {
        String productName = String.valueOf(JSON.parseObject(str).get("productName").toString());
        String productImage = String.valueOf(JSON.parseObject(str).get("productImage").toString());
        String productUnit = String.valueOf(JSON.parseObject(str).get("productUnit").toString());
        String productCate = String.valueOf(JSON.parseObject(str).get("productCate").toString());
        String productAddress = String.valueOf(JSON.parseObject(str).get("productAddress").toString());

        System.out.println(productName + "----");
        System.out.println(productImage + "----");

        Depository product = depositoryService.getOne(new QueryWrapper<Depository>().eq("productName", productName).eq("address", productAddress));
        if (product == null) {
            Depository d = new Depository();
            d.setAddress(productAddress);
            d.setCategory(productCate);
            d.setProductName(productName);
            d.setImageSrc(productImage);
            d.setUnit(productUnit);
            d.setStock(0);
            depositoryService.save(d);

            return Result.succ("成功");
        } else {
            return Result.fail("失败");
        }

        // 可优化
//        boolean flag = true;
//        RentalProduct rentalProduct = rentalProductService.getOne(new QueryWrapper<RentalProduct>().eq("rental_name", productName));
//        if (rentalProduct == null) {
//            RentalProduct rp = new RentalProduct();
//            rp.setRentalName(productName);
//            rp.setImageSrc(productImage);
//            rp.setUnit(productUnit);
//            rp.setCategory(productCate);
//            flag = rentalProductService.save(rp);
//        }
//        if (flag) {
//            RentalProduct rentalProduct1 = rentalProductService.getOne(new QueryWrapper<RentalProduct>().eq("rental_name", productName));
//            RentalDepository rentalDepository = rentalDepositoryService.getOne(new QueryWrapper<RentalDepository>().eq("rental_id", rentalProduct1.getRentalId()).eq("address", productAddress));
//            if (rentalDepository == null) {
//                RentalDepository rd = new RentalDepository();
//                rd.setAddress(productAddress);
//                rd.setStock(0);
//                rd.setRentalId(rentalProduct1.getRentalId());
//                boolean addSuccess = rentalDepositoryService.save(rd);
//                if (addSuccess) {
//                    return Result.succ("成功");
//                } else {
//                    return Result.fail("失败");
//                }
//            } else {
//                return Result.fail("失败");
//            }
//        } else {
//            return Result.fail("失败");
//        }
    }

}
