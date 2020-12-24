package com.qin.controller;



import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qin.common.lang.Result;
import com.qin.entity.MBlog;
import com.qin.service.IMBlogService;
import com.qin.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * <p>
 *  ?????
 * </p>
 *
 * @author qin
 * @since 2020-11-17
 */
@RestController
public class MBlogController{


    @Autowired
    IMBlogService imBlogService;

    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){
        Page page = new Page(currentPage,5);
        IPage pagedata = imBlogService.page(page, new QueryWrapper<MBlog>().orderByDesc("created"));

        return Result.succ(pagedata);
    }

    @GetMapping("/blogs/{id}")
    public Result findOne(@PathVariable(name = "id") Integer id){

        MBlog blog = imBlogService.getById(id);
        Assert.notNull(blog,"????????");

        return Result.succ(blog);
    }

    @RequiresAuthentication
    @PostMapping("/blogs/edit")
    public Result list(@Validated @RequestBody MBlog blog){
        MBlog temp =null;

        if(blog.getId() !=null){
            temp = imBlogService.getById(blog.getId());
            Assert.isTrue(temp.getUserId() == ShiroUtils.getProfile().getId(),"??????");


        }
        else {
            temp = new MBlog();
            temp.setUserId(ShiroUtils.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }

        BeanUtil.copyProperties(blog,temp,"id","userId","created","status");

        imBlogService.saveOrUpdate(temp);

        return  Result.succ(null);
    }
}
