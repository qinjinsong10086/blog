package com.qin.shiro;

import cn.hutool.core.bean.BeanUtil;


import com.qin.entity.MUser;
import com.qin.service.IMUserService;
import com.qin.util.JWTUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountRealm extends AuthorizingRealm {

    @Autowired
    JWTUtils jwtUtils;

    @Autowired
    IMUserService userService;

    @Override
    public boolean supports(AuthenticationToken token){
        return token instanceof JwtToken; //判断是不是jwt 的token  支持jwt的toeken
    }


    //获取权限
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
       JwtToken jwtToken = (JwtToken)authenticationToken;
        System.out.println("认证");
      String  userId = jwtUtils.getClaimByToken((String)jwtToken.getPrincipal()).getSubject();

        MUser user = userService.getById(Long.valueOf(userId));

        if(user == null){
            throw new UnknownAccountException("账户不存在");
        }
        if(user.getStatus()== -1){
            throw new LockedAccountException("账号已被锁定");
        }

        AccountProfile profile = new AccountProfile();
        BeanUtil.copyProperties(user,profile);//源-> 目标

        return new SimpleAuthenticationInfo(profile,jwtToken.getCredentials(),getName());
    }
}
