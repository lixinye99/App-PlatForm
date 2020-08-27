package com.ncusoft.app_platform.service.impl;

import com.ncusoft.app_platform.mapper.AppInfoMapper;
import com.ncusoft.app_platform.mapper.AppMapper;
import com.ncusoft.app_platform.mapper.AppVersionMapper;
import com.ncusoft.app_platform.model.AppInfo;
import com.ncusoft.app_platform.model.AppVersion;
import com.ncusoft.app_platform.model.Bo.AppVersionFormBo;
import com.ncusoft.app_platform.model.Vo.HistoryAppVersionVo;
import com.ncusoft.app_platform.model.enumeration.AppPublishStatus;
import com.ncusoft.app_platform.service.AppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * 主要进行返回结果的检查
 * @author Arno Clare
 * @date 17:35 2020/7/4
 */
@Service
public class AppVersionServiceImpl implements AppVersionService {
    private final AppMapper appMapper;
    private final AppVersionMapper appVersionMapper;
    private final AppInfoMapper appInfoMapper;

    @Autowired
    public AppVersionServiceImpl(AppMapper appMapper, AppVersionMapper appVersionMapper, AppInfoMapper appInfoMapper) {
        this.appMapper = appMapper;
        this.appVersionMapper = appVersionMapper;
        this.appInfoMapper = appInfoMapper;
    }

    @Override
    public AppVersion getAppVersion(long versionId) {
        return appVersionMapper.selectByPrimaryKey(versionId);
    }

    @Override
    public List<AppVersion> listAppVersion(long appId) {
        Example example = new Example(AppVersion.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("appId", appId);
        return appVersionMapper.selectByExample(example);
    }

    @Override
    public List<HistoryAppVersionVo> listHistoryAppVersionVO(long appId) {
        return appMapper.listHistoryAppVersionVo(appId);
    }

    @Override
    public int save(AppVersionFormBo appVersionFormBo) {
        /*通过 Bo 构建 AppVersion */
        AppVersion appVersion = appVersionFormBo.toAppVersion();

        // 外键检查
        if (!isAppExist(appVersion.getAppId()))
            return -1;

        appVersion.setCreationDate(new Date());
        appVersion.setModifyDate(appVersion.getCreationDate());

        return appVersionMapper.insert(appVersion);
    }

    @Override
    public int updateLatestVersion(long appId) {
        /*获取 AppInfo*/
        AppInfo appInfo = appInfoMapper.selectByPrimaryKey(appId);
        /*获取 AppVersionId*/
        Long versionId = appVersionMapper.getLatestAppVersionId(appId);

        if (null == versionId)
            return -1;
        appInfo.setVersionId(versionId);
        return appInfoMapper.updateByPrimaryKey(appInfo);
    }

    @Override
    public AppVersionFormBo getAppVersionFormBo(long versionId) {
        AppVersion appVersion = appVersionMapper.selectByPrimaryKey(versionId);
        if (null == appVersion)
            return null;
        return AppVersionFormBo.toAppVersionFormBo(appVersion);
    }

    @Override
    public int updateAppVersion(AppVersionFormBo appVersionFormBo) {
        /* 通过 Bo 构建 AppVersion */
        AppVersion appVersion = appVersionFormBo.toAppVersion();
        appVersion.setModifyDate(new Date());

        return appVersionMapper.updateByPrimaryKey(appVersion);
    }

    /**
     * 检查 app 是否存在
     * @param appId app_id
     * @return true - 存在, false - 不存在
     */
    private boolean isAppExist(long appId) {
        return appInfoMapper.existsWithPrimaryKey(appId);
    }
}
