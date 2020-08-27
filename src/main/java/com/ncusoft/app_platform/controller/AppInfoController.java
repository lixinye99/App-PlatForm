package com.ncusoft.app_platform.controller;

import com.alibaba.fastjson.JSONObject;
import com.ncusoft.app_platform.model.AppCategory;
import com.ncusoft.app_platform.model.AppInfo;
import com.ncusoft.app_platform.model.Bo.AppInfoFormBo;
import com.ncusoft.app_platform.model.Bo.AppInfoSearchBo;
import com.ncusoft.app_platform.model.Vo.AppInfoFormVo;
import com.ncusoft.app_platform.model.Vo.AppInfoVo;
import com.ncusoft.app_platform.selfannotation.LoginToken;
import com.ncusoft.app_platform.service.AppInfoService;
import com.ncusoft.app_platform.service.UploadService;
import com.ncusoft.app_platform.utils.JsonResult;
import com.ncusoft.app_platform.utils.ResultCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 17:13 2020/7/4
 */
@RestController
@RequestMapping("/app")
public class AppInfoController {

    @Resource
    AppInfoService appInfoService;

    @Resource
    UploadService uploadService;

    @GetMapping("/list/{pageNum}")
    @LoginToken
    public JsonResult queryAll(@PathVariable int pageNum, HttpServletRequest request){
        //从request中获取登录的用户id,判断传入的userId是否存在，不存在提示未登录，存在就查询出信息返回
        if(null == request.getAttribute("userId")){
            return JsonResult.failure(ResultCode.USER_NOT_LOGGED_IN);
        }
        String userId = request.getAttribute("userId").toString();
        //查询本页要显示的数据
        List<AppInfoVo> app = appInfoService.findAppListInfoByUserId(Long.parseLong(userId), pageNum);
        //查询总数
        int totalCount = appInfoService.queryPageTotal(Long.parseLong(userId));
        //查询一级标题、分类和所属平台信息
        JSONObject jsonObject = appInfoService.readyForSearchOrAdd();
        //封装为json对象回传
        jsonObject.put("appInfoList",app);
        jsonObject.put("totalCount",totalCount);
        return JsonResult.success(jsonObject);
    }

    @GetMapping("/allList/{pageNum}")
    public JsonResult ManagerAll(@PathVariable int pageNum, HttpServletRequest request){
        //从request中获取登录的用户id,判断传入的userId是否存在，不存在提示未登录，存在就查询出信息返回
        //查询本页要显示的数据
        List<AppInfoVo> app = appInfoService.findAppListInfo(pageNum);
        //查询所有未审核的app总数
        int totalCount = appInfoService.queryAll();
        //查询一级标题、分类和所属平台信息
        JSONObject jsonObject = appInfoService.readyForSearchOrAdd();
        //封装为json对象回传
        jsonObject.put("appInfoList",app);
        jsonObject.put("totalCount",totalCount);
        return JsonResult.success(jsonObject);
    }


    /**
     * 查询子级列表
     * @param parentLevel
     * @return
     */
    @PostMapping("/searchLevel")
    public JsonResult queryLevel(@RequestBody String parentLevel){
        JSONObject jsonObject = JSONObject.parseObject(parentLevel);
        List<String> sonLevel = appInfoService.queryLevel(jsonObject.getString("parentLevel"));
        return JsonResult.success(sonLevel);
    }

    /**
     * 输入条件查询AppInfo
     * @param info
     * @return
     */
    @PostMapping("/search")
    @LoginToken
    public JsonResult queryAppByInfo(@RequestBody AppInfoSearchBo info,HttpServletRequest request){
        //从request中获取登录的用户id,判断传入的userId是否存在，不存在提示未登录，存在就查询出信息返回
        if(null == request.getAttribute("userId")){
            return JsonResult.failure(ResultCode.USER_NOT_LOGGED_IN);
        }
        String userId = request.getAttribute("userId").toString();
        AppInfoVo appInfo = appInfoService.searchByInfo(info, Long.valueOf(userId));
        if(null == appInfo){
            return JsonResult.failure(ResultCode.RESULE_DATA_NONE);
        }
        return JsonResult.success(appInfo);
    }

    /**
     * 添加一个AppIfo
     * @param insertBo
     * @return
     */
    @PostMapping("/addAppInfo")
    @LoginToken
    public JsonResult insertToAppInfo(HttpServletRequest request,
                                      AppInfoFormBo insertBo) throws IOException {
        //从request中获取登录的用户id,判断传入的userId是否存在，不存在提示未登录，存在就查询出信息返回
        if(null == request.getAttribute("userId")){
            return JsonResult.failure(ResultCode.USER_NOT_LOGGED_IN);
        }
        String userId = request.getAttribute("userId").toString();
        //如果apkName已经被使用了直接返回
        boolean flag = appInfoService.queryAPKNameExist(insertBo.getApkName());
        if(flag){
            return JsonResult.failure(ResultCode.APKNAME_IS_EXIST);
        }
        //把bo类型转换为pojo类型，准备插入
        AppInfo appInfo = CommonCode(request,insertBo);
        appInfo.setVersionId(Long.valueOf(16));
        appInfo.setDevId(Long.valueOf(userId));
        //插入数据库
        int result = appInfoService.addAppInfo(appInfo);
        if(result == 1){
            return JsonResult.success(ResultCode.SUCCESS);
        }
        return JsonResult.failure(ResultCode.INSERT_ERROR);
    }

    /**
     * 插入页面前查找一级标题、状态和平台信息进行渲染
     * @return
     */
    @GetMapping("/ready")
    public JsonResult readyToInsert(){
        JSONObject jsonObject = appInfoService.readyForSearchOrAdd();
        return JsonResult.success(jsonObject);
    }


    /**
     * 更新页面初始化数据渲染
     * @param appId
     * @return
     */
    @GetMapping("/readyForUpdate/{appId}")
    public JsonResult readyForUpdate(@PathVariable String appId){
        if(StringUtils.isBlank(appId)){
            return JsonResult.failure(ResultCode.DATA_IS_WRONG);
        }
        JSONObject jsonObject = new JSONObject();
        //查询该app的基本信息
        AppInfo appInfo = appInfoService.queryAppInfoById(Long.parseLong(appId));
        //根据基本信息中的一级分类查询所有的二级子分类名
        List<AppCategory> twoList = appInfoService.queryLevelNameByParentId(appInfo.getCategoryLevel1());
        List<String> twoNames = twoList.stream().map(AppCategory::getCategoryName).collect(Collectors.toList());
        jsonObject.put("LevelTwoNameList",twoNames);
        //根据二级标题查找所有的三级标题
        List<AppCategory> threeList = appInfoService.queryLevelNameByParentId(appInfo.getCategoryLevel2());
        List<String> threeNames= threeList.stream().map(AppCategory::getCategoryName).collect(Collectors.toList());
        jsonObject.put("LevelThreeNameList",threeNames);

        AppInfoFormVo appInfoFormVo = appInfoService.readyForUpdate(Long.parseLong(appId));
        jsonObject.put("appInfo",appInfoFormVo);
        return JsonResult.success(jsonObject);
    }

    /**
     * 处理更新app的请求
     * @param
     * @return
     */
    @PutMapping("/update/{appId}")
    public JsonResult updateAppInfo(HttpServletRequest request,
                                    AppInfoFormBo updateBo,
                                    @PathVariable String appId) throws IOException {
        //把bo类型转为数据库表中的类型，准备更新
        AppInfo appInfo = CommonCode(request, updateBo);
        appInfo.setId(Long.parseLong(appId));
        //更新表中数据
        boolean result = appInfoService.updateAppInfo(appInfo);
        if(!result){
            return JsonResult.failure(ResultCode.UPDATE_ERROR);
        }
        return JsonResult.success(ResultCode.SUCCESS);
    }

    @LoginToken
    @GetMapping("/appStatus")
    public JsonResult updateAppStatus(@RequestParam Integer appId, @RequestParam Integer status) {
        return appInfoService.updateAppStatus(appId, status, null);
    }

    @LoginToken
    @GetMapping("/auditingApp")
    public JsonResult queryApp(@RequestParam String apkName, @RequestParam Integer pageNum) {
         return JsonResult.success(appInfoService.queryAuditingApp(apkName, pageNum));
    }

    public AppInfo CommonCode(HttpServletRequest request,AppInfoFormBo appInfoFormBo) throws IOException {
        //把bo对象变成数据表对象实现insert
        AppInfo appInfo =  appInfoFormBo.changeToAppInfo();
        appInfo.setCategoryLevel1(appInfoService.queryCategoryIdByName(appInfoFormBo.getCategoryLevel1()));
        appInfo.setCategoryLevel2(appInfoService.queryCategoryIdByName(appInfoFormBo.getCategoryLevel2()));
        appInfo.setCategoryLevel3(appInfoService.queryCategoryIdByName(appInfoFormBo.getCategoryLevel3()));
        //把图片上传到七牛云,返回可访问的文件路径
        String path = uploadService.getPath(request,appInfoFormBo.getImgFile());
        appInfo.setLogoPicPath(path);
        appInfo.setUpdateDate(new Date());
        return appInfo;
    }

    /**
     * @author Arno Clare
     */
    @GetMapping("/read")
    public JsonResult getOneAppInfoFormVo(@RequestParam String appId) {
        AppInfoFormVo appInfoFormVo = appInfoService.readyForUpdate(Long.parseLong(appId));
        if (appInfoFormVo == null) {
            // 异常 返回 50001 - 数据未找到
            return JsonResult.failure(ResultCode.RESULE_DATA_NONE);
        }
        return JsonResult.success(appInfoFormVo);
    }

    /**
     * @author Arno Clare
     */
    @GetMapping("/read/latestversionid")
    public JsonResult getLatestVersionId(@RequestParam String appId) {
        AppInfo appInfo = appInfoService.queryAppInfoById(Long.parseLong(appId));
        if (appInfo == null) {
            // 异常 返回 50001 - 数据未找到
            return JsonResult.failure(ResultCode.RESULE_DATA_NONE);
        }
        return JsonResult.success(appInfo.getVersionId());
    }

    /**
     * @author Arno Clare
     */
    @GetMapping("/delete")
    public JsonResult deleteApp(@RequestParam String appId) {
        final int deleteRes = appInfoService.deleteApp(Long.parseLong(appId));
        if (-1 == deleteRes) {
            return JsonResult.failure(ResultCode.PARAM_IS_INVALID);
        } else if (0 == deleteRes) {
            return JsonResult.failure(ResultCode.DELETE_ERROR);
        }
        return JsonResult.success();
    }
}
