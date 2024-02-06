package com.atguigu.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.atguigu.mapper.UserMapper;
import com.atguigu.pojo.User;
import com.atguigu.utils.JwtHelper;
import com.atguigu.utils.MD5Util;
import com.atguigu.utils.Result;
import com.atguigu.utils.ResultCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author love0
 * @description 针对表【news_user】的数据库操作Service实现
 * @createDate 2024-02-04 13:04:27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtHelper jwtHelper;

    /**
     * 登入業務
     * 1.根據帳號查詢
     * 2.帳號為null 查詢失敗 501
     * 3.對比密碼失敗 返回503
     * 4.根據用戶id生成token,裝入result返回
     *
     * @param user
     * @return
     */
    @Override
    public Result login(User user) {
        //根據帳號查詢
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, user.getUsername());
        User loginUser = userMapper.selectOne(lambdaQueryWrapper);

        if (loginUser == null) {
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }
        //根據密碼查詢
        if (!StringUtils.isEmpty(user.getUserPwd())
                && MD5Util.encrypt(user.getUserPwd()).equals(loginUser.getUserPwd())) {
            //登入成功依據用戶id生成token 裝入result後返回
            String token = jwtHelper.createToken(Long.valueOf(loginUser.getUid()));
            Map data = new HashMap<>();
            data.put("token", token);
            return Result.ok(data);
        }
        return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
    }

    /**
     * 根據token獲取數據
     * 1.token是否還在有效期間
     * 2.根據token解析userId
     * 3.根據用戶id查詢數據
     * 4.去掉密碼,封裝result結果後返回
     *
     * @param token
     * @return
     */
    @Override
    public Result getUserInfo(String token) {
        //是否過期
        boolean expiration = jwtHelper.isExpiration(token);
        if (expiration) {
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }
        int userId = jwtHelper.getUserId(token).intValue();
        User user = userMapper.selectById(userId);
        user.setUserPwd("");
        Map data = new HashMap<>();
        data.put("loginUser", user);
        return Result.ok(data);
    }

    /**
     * 根據傳入的用戶id檢查是否已被占用
     * 1.根據帳號進行count查詢
     * 2.count == 0 可用
     * 3.count > 0 不可用
     *
     * @param username
     * @return
     */
    @Override
    public Result checkUserName(String username) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, username);
        Long count = userMapper.selectCount(lambdaQueryWrapper);
        if (count == 0) {
            return Result.ok(null);
        }
        return Result.build(null, ResultCodeEnum.USERNAME_USED);
    }

    /**
     * 註冊業務
     * 1.檢查帳號是否被註冊
     * 2.密碼加密處理
     * 3.將數據保存
     * 4.返回結果
     *
     * @param user
     * @return
     */
    @Override
    public Result regist(User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, user.getUsername());
        Long count = userMapper.selectCount(lambdaQueryWrapper);

        if (count > 0) {
            return Result.build(null, ResultCodeEnum.USERNAME_USED);

        }
        user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));

        userMapper.insert(user);

        return Result.ok(null);

    }
}




