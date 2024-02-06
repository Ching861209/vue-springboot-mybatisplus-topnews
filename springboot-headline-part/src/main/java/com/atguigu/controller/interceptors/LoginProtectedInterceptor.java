package com.atguigu.controller.interceptors;

import com.atguigu.utils.JwtHelper;
import com.atguigu.utils.Result;
import com.atguigu.utils.ResultCodeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登入包含攔截器,檢查請求頭是否有效
 * 有效:放行 沒效:返回504
 */
@Component
public class LoginProtectedInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtHelper jwtHelper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //從請求頭中獲取token
        String token = request.getHeader("token");
        //檢查是否有效
        boolean expiration = jwtHelper.isExpiration(token);
        //有效:放行
        if(!expiration){
            return true;
        }
        Result result = Result.build(null, ResultCodeEnum.NOTLOGIN);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(result);
        response.getWriter().print(json);
        //無效:504
        return false;
    }
}
