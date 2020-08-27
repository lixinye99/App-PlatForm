package com.ncusoft.app_platform.mapper;

import com.ncusoft.app_platform.model.Vo.HistoryAppVersionVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 处理 App 相关的复杂查询
 * 包括 AppInfo, AppVersion 等
 * @author Arno Clare
 * @date 8:05 2020/7/6
 */
@Repository
@Mapper
public interface AppMapper {
    /**
     * 根据 appId 获取 HistoryAppVersionVo 链表
     * @param appId app_id
     * @return null - 异常
     */
    List<HistoryAppVersionVo> listHistoryAppVersionVo(@Param("appId") long appId);
}
