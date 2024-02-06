package com.atguigu.service;

import com.atguigu.pojo.User;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author love0
* @description 针对表【news_user】的数据库操作Service
* @createDate 2024-02-04 13:04:27
*/
public interface UserService extends IService<User> {
    /**
     * 登入業務
     * @param user
     * @return
     */
    Result login(User user);

    /**
     * 根據token獲取數據
     * @param token
     * @return
     */
    Result getUserInfo(String token);

    /**
     * 根據傳入的用戶名檢查是否被占用
     * @param username
     * @return
     */
    Result checkUserName(String username);

    /**
     * 註冊業務
     * @param user
     * @return
     */
    Result regist(User user);
}
