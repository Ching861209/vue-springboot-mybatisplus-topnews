package com.atguigu.service;

import com.atguigu.pojo.Headline;
import com.atguigu.pojo.vo.PortalVo;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author love0
* @description 针对表【news_headline】的数据库操作Service
* @createDate 2024-02-04 13:04:27
*/
public interface HeadlineService extends IService<Headline> {
    /**
     * 根據關鍵字 新聞標題 頁碼 顯示頁數查詢新聞
     * @param portalVo
     * @return
     */
    Result findNewsPage(PortalVo portalVo);

    /**
     * 根據hid查詢新聞詳情
     * @param hid
     * @return
     */
    Result showHeadlineDetail(Integer hid);

    /**
     * 發布新聞
     * @param headline
     * @return
     */
    Result publish(Headline headline,String token);

    /**
     * 修改新聞
     * @param headline
     * @return
     */
    Result updateData(Headline headline);
}
