package com.atguigu.service;

import com.atguigu.pojo.Type;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author love0
* @description 针对表【news_type】的数据库操作Service
* @createDate 2024-02-04 13:04:27
*/
public interface TypeService extends IService<Type> {
    /**
     * 查詢所有數據
     * @return
     */
    Result findAllTypes();
}
