package com.qin.shiro;

import cn.hutool.json.JSONUtil;
import com.qin.common.lang.Result;

import com.qin.util.JWTUtils;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends AuthenticatingFilter {



    @Autowired
    JWTUtils jwtUtils;

    @Override  //生成token
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        HttpServletRequest request  = (HttpServletRequest)servletRequest;
        String jwt = request.getHeader("Authorization");
        if(StringUtils.isEmpty(jwt)){
            return  null;
        }

        return new JwtToken(jwt);
    }


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        System.out.println("isAccessAllowed  false");
        return false;   //是否认证 无状态 不需要  进行onAccessDenied
    }

    //拦截 没有登录的
    @Override//    在访问被拒绝  是否是拒绝登录   isAccessAllowed：判断是否登录  就是没有登录
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
       //判断用户 jwt  进行校验
        HttpServletRequest request  = (HttpServletRequest)servletRequest;
        String jwt = request.getHeader("Authorization");//Authorization
        System.out.println("StringUtils.isEmpty(jwt)"+jwt);
        if(StringUtils.isEmpty(jwt)){

           return  true;  //当没有jwt 就不需要 登录处理   给注解拦截

        }else {
            //校验jwt
            Claims claims = jwtUtils.getClaimByToken(jwt);
            if(claims == null || jwtUtils.isTokenExpired(claims.getExpiration())){
                throw new ExpiredCredentialsException("token 已失效 请重新 登录");
            }
            System.out.println("onAccessDenied=="+jwt);
            //执行登录
            System.out.println("执行登录  onAccessDenied");

            return  executeLogin(servletRequest,servletResponse);
        }
    }
    @Override //执行登录出现异常 登录失败
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {

       HttpServletResponse httpServletRequest =(HttpServletResponse)response;

       Throwable throwable = e.getCause() ==null ? e:e.getCause();  //获取失败原因

        Result result = Result.fail(throwable.getMessage());
        //hutool
        String json = JSONUtil.toJsonStr(result);
        try{
            //返回 给
            httpServletRequest.getWriter().print(json);

        }catch (IOException ioException){

        }

        return false;
    }
    //
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));


        //跨域时候 会首先发送一个options 请求,这里我们给options请求直接返回正常状态
        if(httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())){
            httpServletResponse.setStatus(org.springframework.http.HttpStatus.OK.value());
            return false;
        }

        return super.preHandle(request, response);
    }
}
