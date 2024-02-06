package com.atguigu.mapper;

import com.atguigu.pojo.Headline;
import com.atguigu.pojo.vo.PortalVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @author love0
* @description 针对表【news_headline】的数据库操作Mapper
* @createDate 2024-02-04 13:04:27
* @Entity generator.pojo.Headline
*/
public interface HeadlineMapper extends BaseMapper<Headline> {
    IPage<Map> selectMyPage(IPage page ,@Param("portalVo") PortalVo portalVo);

    Map queryDetailMap(Integer hid);
}




