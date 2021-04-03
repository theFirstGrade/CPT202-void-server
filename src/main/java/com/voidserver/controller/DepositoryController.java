package com.voidserver.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.voidserver.common.Result;
import com.voidserver.entity.Depository;
import com.voidserver.service.DepositoryService;
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
    @GetMapping("/depository")
    public Result depository(Integer currentPage) {
        if (currentPage == null || currentPage < 1) currentPage = 1;
        Page page = new Page(currentPage, 9);
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
    @GetMapping("/depository/search")
    public Result search(Integer currentPage, String searchCate, String searchAddress, String searchName) {
        if (currentPage == null || currentPage < 1) currentPage = 1;
        QueryWrapper<Depository> sectionQueryWrapper = new QueryWrapper<>();
        if (!searchCate.equals("全部")) {
            sectionQueryWrapper.eq("category", searchCate);
        }
        if (!searchAddress.equals("全部")) {
            sectionQueryWrapper.eq("address", searchAddress);
        }

        if (!searchName.equals("")) {
            sectionQueryWrapper.like("name", searchName);
        }

        Page page = new Page(currentPage, 9);
        IPage pageData = depositoryService.page(page, sectionQueryWrapper);
        return Result.succ(pageData);
    }

//    @PostMapping("/depository/edit")
//    public Result edit(@Validated @RequestBody Blog blog) {
//        System.out.println(blog.toString());
//        Blog temp = null;
//        if (blog.getId() != null) {
//            temp = blogService.getById(blog.getId());
//            Assert.isTrue(temp.getUserId() == ShiroUtil.getProfile().getId(), "没有权限编辑");
//        } else {
//            temp = new Blog();
//            temp.setUserId(ShiroUtil.getProfile().getId());
//            temp.setCreated(LocalDateTime.now());
//            temp.setStatus(0);
//        }
//        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
//        blogService.saveOrUpdate(temp);
//        return Result.succ("操作成功", null);
//    }


}
