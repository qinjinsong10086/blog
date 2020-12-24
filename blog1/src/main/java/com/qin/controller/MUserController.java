package com.qin.controller;


import com.qin.common.lang.Result;
import com.qin.entity.MUser;
import com.qin.service.IMUserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qin
 * @since 2020-11-17
 */
@RestController
public class MUserController  {

    @Autowired
    IMUserService imUserService;

    @RequiresAuthentication//需要登录才能访问
    @GetMapping("/index")
    public Result index(){
       MUser us = imUserService.getById(1);

        return  Result.succ(200,"操作成功",us);
    }
    @GetMapping("/all")
    public Object all(){
       return null;
    }


    @PostMapping("/save")//Validated校验规则
    public Result save(@Validated @RequestBody MUser user){
        return  Result.succ(200,"操作成功",user);
    }



}
