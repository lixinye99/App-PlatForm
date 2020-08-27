package com.ncusoft.app_platform.service;

import com.ncusoft.app_platform.model.AppVersion;
import com.ncusoft.app_platform.model.Bo.AppVersionFormBo;
import com.ncusoft.app_platform.model.Vo.HistoryAppVersionVo;

import java.util.List;

/**
 * 目前均不存在验证
 * @author Arno Clare
 * @date 17:35 2020/7/4
 */
public interface AppVersionService {
    /**
     * 获取指定的 appVersion
     * @param versionId 要查询的 versionId
     * @return AppVersion/null
     */
    AppVersion getAppVersion(long versionId);

    /**
     * 获取已有的全部版本信息
     * @param appId 要查询版本的 appId
     * @return List/null
     */
    List<AppVersion> listAppVersion(long appId);

    /**
     * 获取已有的全部版本信息 VO
     * @param appId 要查询历史版本的 appId
     * @return List/null
     */
    List<HistoryAppVersionVo> listHistoryAppVersionVO(long appId);

    /**
     * 新增版本信息
     * 同时更新 app 最新版本
     * @param AppVersionFormBo 表单对应的 FormBo 类
     * @return 影响行数{-1:异常(外键错误,不存在相应app,Code:10001); 0:数据库操作失败(Code:30002); >0:成功}
     */
    int save(AppVersionFormBo AppVersionFormBo);

    /**
     * 更新 app 的最新版本
     * @param appId 要更新版本的 app
     * @return 影响行数{-1:异常(无版本); 0:数据库操作失败/app不存在(Code:30003); >0:成功}
     */
    int updateLatestVersion(long appId);

    /**
     * 获取指定的 AppVersionFormBo
     * @param versionId 要修改的版本信息的 versionId
     * @return AppVersionFormBo/null
     */
    AppVersionFormBo getAppVersionFormBo(long versionId);

    /**
     * 更新版本信息
     * @param appVersionFormBo 表单对应的 FormBo 类
     * @return 影响行数{0:数据库操作失败(Code:30003); >0: 成功}
     */
    int updateAppVersion(AppVersionFormBo appVersionFormBo);
}
