package com.atguigu.service.impl;

import com.atguigu.mapper.TypeMapper;
import com.atguigu.pojo.Type;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author love0
* @description 针对表【news_type】的数据库操作Service实现
* @createDate 2024-02-04 13:04:27
*/
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
    implements TypeService{

    @Autowired
    private TypeMapper typeMapper;
    /**
     * 查詢所有數據
     * @return
     */
    @Override
    public Result findAllTypes() {
        List<Type> types = typeMapper.selectList(null);
        return Result.ok(types);

    }
}




