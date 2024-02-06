package com.atguigu.service.impl;

import com.atguigu.mapper.HeadlineMapper;
import com.atguigu.pojo.Headline;
import com.atguigu.pojo.vo.PortalVo;
import com.atguigu.utils.JwtHelper;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.service.HeadlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author love0
* @description 针对表【news_headline】的数据库操作Service实现
* @createDate 2024-02-04 13:04:27
*/
@Service
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper, Headline>
    implements HeadlineService{
    @Autowired
    private HeadlineMapper headlineMapper;
    @Autowired
    private JwtHelper jwtHelper;
    /**
     * 根據關鍵字 新聞標題 頁碼 顯示頁數查詢新聞
     * 1.進行分頁數據查詢
     * 2.分頁數據拼接到result
     * @param portalVo
     * @return
     */
    @Override
    public Result findNewsPage(PortalVo portalVo) {
        IPage<Map> page = new Page<>(portalVo.getPageNum(),portalVo.getPageSize());
        headlineMapper.selectMyPage(page,portalVo);

        Map data = new HashMap();
        data.put("pageData",page.getRecords());
        data.put("pageNum",page.getCurrent());
        data.put("pageSize",page.getSize());
        data.put("totalPage",page.getPages());
        data.put("totalSize",page.getTotal());

        Map pageInfo = new HashMap();
        pageInfo.put("pageInfo",data);

        return Result.ok(pageInfo);
    }

    /**
     * 1.查詢對應的數據(多表查詢,新聞表以及用戶表,返回Map)
     * 2.拿到version
     * 3.修改瀏覽量+1
     * @param hid
     * @return
     */
    @Override
    public Result showHeadlineDetail(Integer hid) {
        Map headlineMap = headlineMapper.queryDetailMap(hid);
        Map data = new HashMap<>();
        data.put("headline",headlineMap);
        //根據hid 修改閱讀量,版本
        Headline headline = new Headline();
        headline.setHid((Integer) headlineMap.get("hid"));
        //設置版本
        headline.setVersion((Integer) headlineMap.get("version"));
        //修改瀏覽量+1
        headline.setPageViews((Integer) headlineMap.get("pageViews")+1);
        headlineMapper.updateById(headline);
        return Result.ok(data);
    }

    /**
     * 發布新聞
     * 1.資料補齊
     * @param headline
     * @return
     */
    @Override
    public Result publish(Headline headline,String token) {
        //查詢用戶數據
        int userId = jwtHelper.getUserId(token).intValue();
        headline.setPublisher(userId);
        headline.setPageViews(0);
        headline.setCreateTime(new Date());
        headline.setUpdateTime(new Date());

        headlineMapper.insert(headline);
        return Result.ok(null);
    }

    /**
     * 1.根據hid查詢數據最新的version
     * 2.修改數據的修改時間
     * @param headline
     * @return
     */
    @Override
    public Result updateData(Headline headline) {
        Integer version = headlineMapper.selectById(headline.getHid()).getVersion();
        headline.setVersion(version);
        headline.setUpdateTime(new Date());

        headlineMapper.updateById(headline);

        return Result.ok(null);
    }
}




