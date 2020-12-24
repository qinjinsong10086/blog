package com.qin.controller;


import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qin.common.dto.LoginDto;
import com.qin.common.lang.Result;
import com.qin.entity.MUser;
import com.qin.service.IMUserService;
import com.qin.util.JWTUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AccountController {

    @Autowired
    IMUserService imUserService;

    @Autowired
    JWTUtils jwtUtils;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response){
        System.out.println(SecurityUtils.getSubject());
        MUser user = imUserService.getOne(new QueryWrapper<MUser>().eq("username", loginDto.getUsername()));

        Assert.notNull(user,"用户不存在");
        if(!user.getPassword().equals(loginDto.getPassword())){
            return Result.fail("密码错误！");
        }




        String jwt = jwtUtils.generateToken(user.getId());
      //jwt延期问题   有就刷新 jwt 放到header 里面
        response.setHeader("Authorization",jwt);
        response.setHeader("Access-control-Expose-Headers","Authorization");

        return Result.succ(MapUtil.builder()
                .put("id",user.getId())
                .put("username",user.getUsername())
                .put("avatar",user.getAvatar())
                .put("email",user.getEmail())
                .map()
        );


    }

    @RequiresAuthentication  //需要登录的权限
    @GetMapping("/logout")
    public Result login(){
        System.out.println(SecurityUtils.getSubject().isAuthenticated());
       // System.out.println("---------------------"+SecurityUtils.getSubject().getSession().getId());
      //  SecurityUtils.getSubject().logout();
        return  Result.succ(null);

    }

}
