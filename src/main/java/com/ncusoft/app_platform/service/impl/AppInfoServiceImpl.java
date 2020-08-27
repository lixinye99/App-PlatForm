package com.ncusoft.app_platform.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.ncusoft.app_platform.mapper.AppCategoryMapper;
import com.ncusoft.app_platform.mapper.AppInfoMapper;
import com.ncusoft.app_platform.mapper.AppVersionMapper;
import com.ncusoft.app_platform.model.AppCategory;
import com.ncusoft.app_platform.model.AppInfo;
import com.ncusoft.app_platform.model.AppVersion;
import com.ncusoft.app_platform.model.Bo.AppInfoSearchBo;
import com.ncusoft.app_platform.model.Vo.AppInfoFormVo;
import com.ncusoft.app_platform.model.Vo.AppInfoVo;
import com.ncusoft.app_platform.model.enumeration.AppPlatform;
import com.ncusoft.app_platform.model.enumeration.AppStatus;
import com.ncusoft.app_platform.service.AppInfoService;
import com.ncusoft.app_platform.utils.JsonResult;
import com.ncusoft.app_platform.utils.ResultCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 17:15 2020/7/4
 */
@Service
public class AppInfoServiceImpl implements AppInfoService {

    @Resource
    AppInfoMapper appInfoMapper;

    @Resource
    AppCategoryMapper appCategoryMapper;

    @Resource
    AppVersionMapper appVersionMapper;

    /**
     * 分页查询用户所有提交的app信息
     * @param userId
     * @param pageNum
     * @return
     */
    @Override
    public List<AppInfoVo> findAppListInfoByUserId(Long userId, int pageNum) {
        //启动分页
        PageHelper.startPage(pageNum,5);
        //根据条件查询数据
        Example appInfoExample = new Example(AppInfo.class);
        appInfoExample.createCriteria().andEqualTo("devId",userId);
        List<AppInfo> appInfos = appInfoMapper.selectByExample(appInfoExample);
        //封装为对象返回
        List<AppInfoVo> appInfoVos = new ArrayList<>();
        for(AppInfo app : appInfos){
            AppInfoVo appInfoVo = new AppInfoVo();
            appInfoVo.setId(app.getId());
            appInfoVo.setSoftwareName(app.getSoftwareName());
            appInfoVo.setApkName(app.getApkName());
            appInfoVo.setSoftwareSize(app.getSoftwareSize());
            appInfoVo.setPlatformName(AppPlatform.getValue(app.getPlatformId()));
            appInfoVo.setCategoryLevel1(appCategoryMapper.selectByPrimaryKey(app.getCategoryLevel1()).getCategoryName());
            appInfoVo.setCategoryLevel2(appCategoryMapper.selectByPrimaryKey(app.getCategoryLevel2()).getCategoryName());
            appInfoVo.setCategoryLevel3(appCategoryMapper.selectByPrimaryKey(app.getCategoryLevel3()).getCategoryName());
            appInfoVo.setVersionNo(appVersionMapper.selectByPrimaryKey(app.getVersionId()).getVersionNo());
            appInfoVo.setStatus(AppStatus.getValue(app.getStatus()));
            appInfoVos.add(appInfoVo);
        }
        return appInfoVos;
    }

    /**
     * 查询总页数
     * @param userId
     * @return
     */
    public int queryPageTotal(Long userId){
        //查询数据总数计算需要的页数
        Example appInfoExample = new Example(AppInfo.class);
        appInfoExample.createCriteria().andEqualTo("devId",userId);
        int totalPage = appInfoMapper.selectCountByExample(appInfoExample);
        return totalPage;
    }

    /**
     * 查询所有的一级标题
     * @return
     */
    public List<String> queryLevelOne(){
        Example example = new Example(AppCategory.class);
        example.createCriteria().andIsNull("parentId");
        List<AppCategory> appCategories = appCategoryMapper.selectByExample(example);
        List<String> nameList = appCategories.stream().map(AppCategory::getCategoryName).collect(Collectors.toList());
        return nameList;
    }

    /**
     * 查找子级标题
     * @param parentLevel
     * @return
     */
    public List<String> queryLevel(String parentLevel){
        //先根据父标题的name查询对应的id
        Example example = new Example(AppCategory.class);
        example.createCriteria().andEqualTo("categoryName",parentLevel);
        AppCategory appCategory = appCategoryMapper.selectOneByExample(example);

        //根据父标题的id获取子标题
        Example exampleTwo = new Example(AppCategory.class);
        exampleTwo.createCriteria().andEqualTo("parentId",appCategory.getId());
        List<AppCategory> sonLevel = appCategoryMapper.selectByExample(exampleTwo);
        List<String> nameList = sonLevel.stream().map(AppCategory::getCategoryName).collect(Collectors.toList());
        return nameList;
    }

    /**
     * 根据输入的条件查询appInfo
     * @param info
     * @return
     */
    @Override
    public AppInfoVo searchByInfo(AppInfoSearchBo info,Long useId) {
        //标题查找id
        Example example1 = new Example(AppCategory.class);
        example1.createCriteria().andEqualTo("categoryName", info.getCategoryLevel1());
        AppCategory level1 = appCategoryMapper.selectOneByExample(example1);
        Example example2 = new Example(AppCategory.class);
        example2.createCriteria().andEqualTo("categoryName", info.getCategoryLevel2());
        AppCategory level2 = appCategoryMapper.selectOneByExample(example2);
        Example example3 = new Example(AppCategory.class);
        example3.createCriteria().andEqualTo("categoryName", info.getCategoryLevel3());
        AppCategory level3 = appCategoryMapper.selectOneByExample(example3);

        Example example = new Example(AppInfo.class);
        example.createCriteria()
                .andEqualTo("softwareName",info.getAppName())
                .andEqualTo("platformId", AppPlatform.getValue(info.getPlatformName()))
                .andEqualTo("status", AppStatus.getValue(info.getAppStatus()))
                .andEqualTo("categoryLevel1",level1.getId())
                .andEqualTo("categoryLevel2",level2.getId())
                .andEqualTo("categoryLevel3",level3.getId())
                .andEqualTo("devId",useId);
        AppInfo appInfo = appInfoMapper.selectOneByExample(example);
        if(null == appInfo){
            return null;
        }
        AppInfoVo appInfoVo = new AppInfoVo();
        appInfoVo.setId(appInfo.getId());
        appInfoVo.setSoftwareName(appInfo.getSoftwareName());
        appInfoVo.setApkName(appInfo.getApkName());
        appInfoVo.setSoftwareSize(appInfo.getSoftwareSize());
        appInfoVo.setStatus(AppStatus.getValue(appInfo.getStatus()));
        appInfoVo.setPlatformName(AppPlatform.getValue(appInfo.getPlatformId()));
        appInfoVo.setCategoryLevel1(level1.getCategoryName());
        appInfoVo.setCategoryLevel2(level2.getCategoryName());
        appInfoVo.setCategoryLevel3(level3.getCategoryName());
        appInfoVo.setVersionNo(appVersionMapper.selectByPrimaryKey(appInfo.getVersionId()).getVersionNo());
        return appInfoVo;
    }

    /**
     * 根据分类的名字查找对应的id
     * @param name
     * @return
     */
    @Override
    public Long queryCategoryIdByName(String name) {
        Example example = new Example(AppCategory.class);
        example.createCriteria().andEqualTo("categoryName", name);
        Long categoryId = appCategoryMapper.selectOneByExample(example).getId();
        return categoryId;
    }

    /**
     * 插入一条appInfo数据
     * @param appInfo
     * @return
     */
    @Override
    public int addAppInfo(AppInfo appInfo) {
        int insert = appInfoMapper.insert(appInfo);
        return insert;
    }

    /**
     * 判断apkName是不是已经被使用了
     * @param apkName
     * @return
     */
    @Override
    public boolean queryAPKNameExist(String apkName) {

        Example example = new Example(AppInfo.class);
        example.createCriteria().andEqualTo("apkName", apkName);
        List<AppInfo> appList = appInfoMapper.selectByExample(example);
        if(appList.size() > 0){
            return true;
        }
        return false;
    }

    /**
     * 查询一级列表、状态和所属平台数据
     * @return
     */
    @Override
    public JSONObject readyForSearchOrAdd() {
        //查询所有的一级标题
        List<String> categoryOneList = queryLevelOne();
        //查询所有的状态信息
        List<String> platFormList = new ArrayList<>();
        for (AppPlatform appPlatform : AppPlatform.values()) {
            platFormList.add(appPlatform.getMsg());
        }
        //查询所有的状态信息
        List<String> statusList = new ArrayList<>();
        for (AppStatus appStatus : AppStatus.values()) {
            statusList.add(appStatus.getMsg());
        }
        //把所有的信息封装为json对象
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("categoryOneList",categoryOneList);
        jsonObject.put("platFormList",platFormList);
        jsonObject.put("statusList",statusList);
        return jsonObject;
    }

    /**
     * appInfo修改前查询要修改的app进行前端页面渲染
     * @param appId
     * @return
     */
    @Override
    public AppInfoFormVo readyForUpdate(long appId) {
        Example example = new Example(AppInfo.class);
        example.createCriteria().andEqualTo("id", appId);
        AppInfo app = appInfoMapper.selectOneByExample(example);
        AppInfoFormVo appInfoFormVo = new AppInfoFormVo();
        appInfoFormVo.setSoftwareName(app.getSoftwareName());
        appInfoFormVo.setAPKName(app.getApkName());
        appInfoFormVo.setSupportROM(app.getSupportROM());
        appInfoFormVo.setInterfaceLanguage(app.getInterfaceLanguage());
        appInfoFormVo.setSoftwareSize(app.getSoftwareSize());
        appInfoFormVo.setDownloads(app.getDownloads());
        appInfoFormVo.setPlatformName(AppPlatform.getValue(app.getPlatformId()));
        appInfoFormVo.setCategoryLevel1(appCategoryMapper.selectByPrimaryKey(app.getCategoryLevel1()).getCategoryName());
        appInfoFormVo.setCategoryLevel2(appCategoryMapper.selectByPrimaryKey(app.getCategoryLevel2()).getCategoryName());
        appInfoFormVo.setCategoryLevel3(appCategoryMapper.selectByPrimaryKey(app.getCategoryLevel3()).getCategoryName());
        appInfoFormVo.setStatus(AppStatus.getValue(app.getStatus()));
        appInfoFormVo.setAppInfo(app.getAppInfo());
        appInfoFormVo.setImagePath(app.getLogoPicPath());
        return appInfoFormVo;
    }

    @Override
    public JsonResult updateAppStatus(int app_id, int status, String version){
        AppInfo info = appInfoMapper.selectByPrimaryKey(app_id);
        if (status == AppStatus.ON_SHELF.getType()) {
            if (info.getStatus() == AppStatus.REVIEW_SUCCESS.getType() ||
                    info.getStatus() == AppStatus.OFF_SHELF.getType()) {
                info.setStatus(status);
            } else {
                return JsonResult.failure(ResultCode.UPDATE_STATUS_ERROR);
            }
        } else if (status == AppStatus.OFF_SHELF.getType()) {
            if (info.getStatus() == AppStatus.ON_SHELF.getType()) {
                info.setStatus(status);
            } else {
                return JsonResult.failure(ResultCode.UPDATE_STATUS_ERROR);
            }
        } else if (status == AppStatus.REVIEW_FAILED.getType() ||
                status == AppStatus.REVIEW_SUCCESS.getType()) {
            Long latestVersionId = appVersionMapper.getLatestAppVersionId(app_id);
            AppVersion appVersion = appVersionMapper.selectByPrimaryKey(latestVersionId);
            if (appVersion.getVersionNo().equals(version)) {
                return JsonResult.failure(ResultCode.APP_NEED_LATEST);
            } else {
                info.setStatus(status);
            }
        }
        appInfoMapper.updateByPrimaryKey(info);
        return JsonResult.success();
    }

    /**
     * 根据Id和输入的信息修改appInfo
     * @param appInfo
     * @return
     */
    @Override
    public boolean updateAppInfo(AppInfo appInfo) {
        int count = appInfoMapper.updateByPrimaryKeySelective(appInfo);
        if(count == 1){
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public int deleteApp(long appId) {
        /*检查 app 存在*/
        if (!appInfoMapper.existsWithPrimaryKey(appId))
            return -1;
        /*删除所有 version */
        Example example = new Example(AppVersion.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("appId", appId);
        int versionRes = appVersionMapper.deleteByExample(example);
        /*删除 app */
        int infoRes = appInfoMapper.deleteByPrimaryKey(appId);
        return versionRes + infoRes;
    }

    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    @Override
    public List<AppInfo> queryAuditingApp(String apkName, int pageNum) {
        //启动分页
        PageHelper.startPage(pageNum, 5);
        //根据条件查询数据
        Example example = new Example(AppInfo.class);
        example.createCriteria().andLike("apkName", "%" + apkName + "%");
        List<AppInfo> appInfos = appInfoMapper.selectByExample(example);
        return appInfos;
    }

    @Override
    public AppInfo queryAppInfoById(long appId) {
        return appInfoMapper.selectByPrimaryKey(appId);
    }

    @Override
    public List<AppCategory> queryLevelNameByParentId(Long parentId) {
        Example example = new Example(AppCategory.class);
        example.createCriteria().andEqualTo("parentId",parentId);
        List<AppCategory> appInfo = appCategoryMapper.selectByExample(example);
        return appInfo;
    }

    /**
     * 管理员查询所有的指定页码的未审核的app
     * @param pageNum
     * @return
     */
    @Override
    public List<AppInfoVo> findAppListInfo(int pageNum) {
        //启动分页
        PageHelper.startPage(pageNum,5);
        //根据条件查询数据
        Example appInfoExample = new Example(AppInfo.class);
        appInfoExample.createCriteria().andEqualTo("status",1);
        List<AppInfo> appInfos = appInfoMapper.selectByExample(appInfoExample);
        //封装为对象返回
        List<AppInfoVo> appInfoVos = new ArrayList<>();
        for(AppInfo app : appInfos){
            AppInfoVo appInfoVo = new AppInfoVo();
            appInfoVo.setId(app.getId());
            appInfoVo.setSoftwareName(app.getSoftwareName());
            appInfoVo.setApkName(app.getApkName());
            appInfoVo.setSoftwareSize(app.getSoftwareSize());
            appInfoVo.setPlatformName(AppPlatform.getValue(app.getPlatformId()));
            appInfoVo.setCategoryLevel1(appCategoryMapper.selectByPrimaryKey(app.getCategoryLevel1()).getCategoryName());
            appInfoVo.setCategoryLevel2(appCategoryMapper.selectByPrimaryKey(app.getCategoryLevel2()).getCategoryName());
            appInfoVo.setCategoryLevel3(appCategoryMapper.selectByPrimaryKey(app.getCategoryLevel3()).getCategoryName());
            appInfoVo.setVersionNo(appVersionMapper.selectByPrimaryKey(app.getVersionId()).getVersionNo());
            appInfoVo.setStatus(AppStatus.getValue(app.getStatus()));
            appInfoVos.add(appInfoVo);
        }
        return appInfoVos;
    }

    @Override
    public int queryAll() {
        //根据条件查询数据
        Example appInfoExample = new Example(AppInfo.class);
        appInfoExample.createCriteria().andEqualTo("status",1);
        int count = appInfoMapper.selectCountByExample(appInfoExample);
        return count;
    }
}
