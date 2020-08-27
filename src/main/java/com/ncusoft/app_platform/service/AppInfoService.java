package com.ncusoft.app_platform.service;

import com.alibaba.fastjson.JSONObject;
import com.ncusoft.app_platform.model.AppCategory;
import com.ncusoft.app_platform.model.AppInfo;
import com.ncusoft.app_platform.model.Bo.AppInfoSearchBo;
import com.ncusoft.app_platform.model.Vo.AppInfoFormVo;
import com.ncusoft.app_platform.model.Vo.AppInfoVo;
import com.ncusoft.app_platform.utils.JsonResult;

import java.util.List;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 17:16 2020/7/4
 */
public interface AppInfoService {
    /**
     * 根据登录的userId查询用户提交的所有app
     * @param userId
     * @param pageNum
     * @return
     */
    List<AppInfoVo> findAppListInfoByUserId(Long userId, int pageNum);

    /**
     * 根据用户id查询该用户app的总页数
     * @param userId
     * @return
     */
    int queryPageTotal(Long userId);

    /**
     * 查询一级标题
     * @return
     */
    List<String> queryLevelOne();

    /**
     * 查找子级标题
     * @param parentLevel
     * @return
     */
    List<String> queryLevel(String parentLevel);

    /**
     * 根据输入的条件查询appInfo
     * @param info
     * @param userId
     * @return
     */
    AppInfoVo searchByInfo(AppInfoSearchBo info,Long userId);

    /**
     * 根据分类的名字查找对应的id
     * @param name
     * @return
     */
    Long queryCategoryIdByName(String name);

    /**
     * 插入一条appInfo数据
     * @param appInfo
     * @return
     */
    int addAppInfo(AppInfo appInfo);

    /**
     * app 的上下架操作
     * @param status app 状态
     */
    JsonResult updateAppStatus(int app_id, int status, String version);

    /**
     * 判断apkName是不是已经被使用了
     * @param apkName
     * @return
     */
    boolean queryAPKNameExist(String apkName);

    /**
     * 查询一级列表、状态和所属平台数据
     * @return
     */
    JSONObject readyForSearchOrAdd();

    /**
     * appInfo修改前查询要修改的app进行前端页面渲染
     * @param appId
     * @return
     */
    AppInfoFormVo readyForUpdate(long appId);


    /**
     * 根据Id和输入的信息修改appInfo
     * @param appInfo
     * @return
     */
    boolean updateAppInfo(AppInfo appInfo);

    /**
     * 删除 app
     * 包括删除所有的 version
     * @author Arno Clare
     * @param appId app_id
     * @return -1 - 异常|如 app 不存在(10001 - "参数无效"), 0 - 删除错误(30005 - "删除发生错误"), >0 - 成功
     */
    int deleteApp(long appId);

    /**
     * 查询审核中的app
     * @param apkName apk 名称（模糊查询）
     * @param pageNum 页数
     * @return
     */
    List<AppInfo> queryAuditingApp(String apkName, int pageNum);

    /**
     * 查询 appInfo
     * @author Arno Clare
     * @param appId app_id
     * @return AppInfo/null
     */
    AppInfo queryAppInfoById(long appId);

    /**
     * 根据父级分类的id获取所有的子级分类名字
     * @param parentId
     * @return
     */
    List<AppCategory> queryLevelNameByParentId(Long parentId);

    /**
     * 管理员查询所有的指定页码的未审核的app
     * @param pageNum
     * @return
     */
    List<AppInfoVo> findAppListInfo(int pageNum);

    /**
     * 查询未审核的app总数
     * @return
     */
    int queryAll();
}
