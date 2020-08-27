package com.ncusoft.app_platform.controller;

import com.ncusoft.app_platform.model.AppInfo;
import com.ncusoft.app_platform.model.AppVersion;
import com.ncusoft.app_platform.model.Bo.AppVersionFormBo;
import com.ncusoft.app_platform.model.Vo.HistoryAppVersionVo;
import com.ncusoft.app_platform.selfannotation.LoginToken;
import com.ncusoft.app_platform.service.AppInfoService;
import com.ncusoft.app_platform.service.AppVersionService;
import com.ncusoft.app_platform.service.UploadService;
import com.ncusoft.app_platform.utils.JsonResult;
import com.ncusoft.app_platform.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author Arno Clare
 * @date 17:52 2020/7/4
 */
@RestController
@RequestMapping("/appversion")
public class AppVersionController {

    private final AppVersionService appVersionService;
    private final UploadService uploadService;
    private final AppInfoService appInfoService;

    @Autowired
    public AppVersionController(AppVersionService appVersionService, UploadService uploadService, AppInfoService appInfoService) {
        this.appVersionService = appVersionService;
        this.uploadService = uploadService;
        this.appInfoService = appInfoService;
    }

    /**
     * 返回历史版本列表
     * @return ResultCode.SUCCESS - 成功, ResultCode.RESULE_DATA_NONE - 数据未找到
     */
    @GetMapping
    public JsonResult listHistoryAppVersion(@RequestParam String appId) {
        /*获取列表*/
        List<HistoryAppVersionVo> historyAppVersionVoList =
                appVersionService.listHistoryAppVersionVO(Long.parseLong(appId));

        if (historyAppVersionVoList == null) {
            // 异常 返回 50001 - 数据未找到
            return JsonResult.failure(ResultCode.RESULE_DATA_NONE);
        }

        // 倒置 list
        Collections.reverse(historyAppVersionVoList);
        return JsonResult.success(historyAppVersionVoList);
    }

    /**
     * 新增版本信息
     * @param appVersionFormBo 表单自动组装对象
     * @return ResultCode.SUCCESS - 成功, ResultCode.PARAM_IS_BLANK - 参数为空, ResultCode.SAVE_ERROR - 保存错误, ResultCode.PARAM_IS_INVALID - 参数无效
     */
    @LoginToken
    @PostMapping
    public JsonResult addAppVersion(@RequestParam String appId, AppVersionFormBo appVersionFormBo,
                                    HttpServletRequest request) {
        // TODO 检查权限
        /*字段处理*/
        // 填充 appId
        appVersionFormBo.setAppId(Long.valueOf(appId));
        // 填充 createBy 和 modifyBy
        appVersionFormBo.setCreateBy(Long.valueOf((String) request.getAttribute("userId")));
        appVersionFormBo.setModifyBy(appVersionFormBo.getCreateBy());
        /*格式验证*/
        // 非空验证
        if (appVersionFormBo.isFormFieldBlank() || appVersionFormBo.getApkFile() == null) {
            return JsonResult.failure(ResultCode.PARAM_IS_BLANK);
        } else if (!appVersionFormBo.fileCheck()) {
            return JsonResult.failure(ResultCode.PARAM_TYPE_BIND_ERROR);
        }

        /*上传文件*/
        try {
            appVersionFormBo.setApkFileName(appVersionFormBo.getApkFile().getOriginalFilename());
            String filePath = uploadService.getPath(request, appVersionFormBo.getApkFile());
            appVersionFormBo.setApkFilePath(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return JsonResult.failure(ResultCode.SAVE_ERROR);
        }

        /*保存对象*/
        int saveRes = appVersionService.save(appVersionFormBo);
        if (saveRes == 0) {
            return JsonResult.failure(ResultCode.SAVE_ERROR);
        } else if (saveRes == -1) {
            return JsonResult.failure(ResultCode.PARAM_IS_INVALID);
        }
        // 更新最新版本
        int updateRes = appVersionService.updateLatestVersion(Long.parseLong(appId));
        if (updateRes == 0) {
            JsonResult jsonResult = new JsonResult();
            jsonResult.setCode(ResultCode.UPDATE_ERROR.code());
            jsonResult.setMsg("新增版本信息成功，但 app 最新版本信息更新失败");
            return jsonResult;
        }

        return JsonResult.success();
    }

    /**
     * 获取 AppVersion 至前端表单用于修改
     * @return ResultCode.SUCCESS - 成功, ResultCode.RESULE_DATA_NONE - 数据未找到
     */
    @GetMapping("/version")
    public JsonResult getAppVersionToForm(@RequestParam String versionId) {
        /*获取 AppVersionFormBo */
        AppVersionFormBo appVersionFormBo = appVersionService.getAppVersionFormBo(Long.parseLong(versionId));
        if (null == appVersionFormBo)
            return JsonResult.failure(ResultCode.RESULE_DATA_NONE);
        return JsonResult.success(appVersionFormBo);
    }

    @LoginToken
    @PostMapping("/version")
    public JsonResult updateAppVersion(@RequestParam String appId, @RequestParam String versionId, AppVersionFormBo appVersionFormBo,
                                       HttpServletRequest request) {
        // TODO 检查权限
        /*字段处理*/
        // 填充 versionId
        appVersionFormBo.setVersionId(Long.valueOf(versionId));
        // 填充 modifyBy
        appVersionFormBo.setModifyBy(Long.valueOf((String) request.getAttribute("userId")));
        // 非空验证
        if (appVersionFormBo.isFormFieldBlank()) {
            return JsonResult.failure(ResultCode.PARAM_IS_BLANK);
        }

        if (appVersionFormBo.getApkFile() != null) {
            if (!appVersionFormBo.fileCheck()) {
                return JsonResult.failure(ResultCode.PARAM_TYPE_BIND_ERROR);
            }
            try {
                String filePath = uploadService.getPath(request, appVersionFormBo.getApkFile());
                appVersionFormBo.setApkFilePath(filePath);
            } catch (IOException e) {
                e.printStackTrace();
                return JsonResult.failure(ResultCode.SAVE_ERROR);
            }
        }

        /*更新对象*/
        int updateRes = appVersionService.updateAppVersion(appVersionFormBo);
        if (updateRes == 0) {
            return JsonResult.failure(ResultCode.UPDATE_ERROR);
        } else if (updateRes == -1) {
            return JsonResult.failure(ResultCode.PARAM_IS_INVALID);
        }

        return JsonResult.success();
    }

    @LoginToken
    @PutMapping("/appStatus")
    public JsonResult updateAppStatus(@RequestParam Integer appId, @RequestParam Integer status,
                                      @RequestParam String versionNo) {
        return appInfoService.updateAppStatus(appId, status, versionNo);
    }
}
