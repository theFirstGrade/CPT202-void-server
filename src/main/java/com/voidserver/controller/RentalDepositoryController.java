package com.voidserver.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.voidserver.common.OrderDto;
import com.voidserver.common.RentalDepositoryAddDto;
import com.voidserver.common.RentalProductVO;
import com.voidserver.common.Result;
import com.voidserver.entity.Depository;
import com.voidserver.entity.RentalDepository;
import com.voidserver.entity.RentalProduct;
import com.voidserver.service.RentalDepositoryService;
import com.voidserver.service.RentalProductService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
public class RentalDepositoryController {

    @Autowired
    RentalDepositoryService rentalDepositoryService;

    @Autowired
    RentalProductService rentalProductService;

    @RequiresAuthentication
    @CrossOrigin
    @PostMapping("/rentalStock/change")
    public Result rentalStockChange(@RequestBody String str) {
        int id = (int) JSON.parseObject(str).get("rentalDepositoryId");
        int stock = (int) JSON.parseObject(str).get("rentalDepositoryStock");
        System.out.println(id + "---" + stock);
        RentalDepository rd = rentalDepositoryService.getById(id);
        if (rd != null) {
            rd.setStock(stock);
            rentalDepositoryService.update(rd, new QueryWrapper<RentalDepository>().eq("depository_id", id));
            return Result.succ("成功");
        }
        return Result.fail("失败");
    }

    @RequiresAuthentication
    @GetMapping("/rentalDepository")
    public Result rentalDepository(Integer currentPage) {
        if (currentPage == null || currentPage < 1) currentPage = 1;
        Page<RentalProductVO> productDepository;
        productDepository = rentalDepositoryService.getRentalProductDepository1(new Page<>(currentPage, 5));

        return Result.succ(productDepository);
    }

    //reqSearchRentalProducts

    @RequiresAuthentication
    @CrossOrigin
    @GetMapping("/rentalProducts/search")
    public Result getRentalDepository(Integer currentPage, String searchCate, String searchAddress, String searchName) {
        if (currentPage == null || currentPage < 1) currentPage = 1;
        Page<RentalProductVO> productDepository;
        System.out.println(searchCate + "---" + searchAddress + "---" + searchName);
        if (searchCate.equals("全部") || searchCate.equals("All")) {
            if (searchName.equals("")) {
                // SELECT from a.category=searchCate and a.name=
                if (searchAddress.equals("全部") || searchAddress.equals("All")) {
                    productDepository = rentalDepositoryService.getRentalProductDepository1(new Page<>(currentPage, 5));
                } else {
                    productDepository = rentalDepositoryService.getRentalProductDepository2(new Page<>(currentPage, 5), searchAddress);

                }
            } else {
                if (searchAddress.equals("全部") || searchAddress.equals("All")) {
                    productDepository = rentalDepositoryService.getRentalProductDepository7(new Page<>(currentPage, 5), searchName);
                } else {
                    productDepository = rentalDepositoryService.getRentalProductDepository3(new Page<>(currentPage, 5), searchAddress, searchName);
                }
            }
        } else {
            if (searchName.equals("")) {
                if (searchAddress.equals("全部") || searchAddress.equals("All")) {
                    productDepository = rentalDepositoryService.getRentalProductDepository4(new Page<>(currentPage, 5), searchCate);
                } else {
                    productDepository = rentalDepositoryService.getRentalProductDepository5(new Page<>(currentPage, 5), searchCate, searchAddress);
                }
            } else {
                if (searchAddress.equals("全部") || searchAddress.equals("All")) {
                    productDepository = rentalDepositoryService.getRentalProductDepository8(new Page<>(currentPage, 5), searchCate, searchName);
                } else {
                    productDepository = rentalDepositoryService.getRentalProductDepository6(new Page<>(currentPage, 5), searchCate, searchAddress, searchName);
                }
            }
        }

        return Result.succ(productDepository);
    }

}
