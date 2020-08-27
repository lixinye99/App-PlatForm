package com.ncusoft.app_platform.controller;

import com.ncusoft.app_platform.model.AppInfo;
import com.ncusoft.app_platform.model.enumeration.AppStatus;
import com.ncusoft.app_platform.selfannotation.LoginToken;
import com.ncusoft.app_platform.service.AppInfoService;
import com.ncusoft.app_platform.service.AppVersionService;
import com.ncusoft.app_platform.utils.JsonResult;
import com.ncusoft.app_platform.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Arno Clare
 * @date 10:41 2020/7/8
 */
@RestController
@LoginToken
@RequestMapping("/appverify")
public class VerifyController {
    private final AppInfoService appInfoService;

    @Autowired
    public VerifyController(AppInfoService appInfoService) {
        this.appInfoService = appInfoService;
    }

    @LoginToken
    @GetMapping("/on")
    public JsonResult verifyOn(@RequestParam String appId) {
        AppInfo appInfo = appInfoService.queryAppInfoById(Long.parseLong(appId));
        appInfo.setStatus(AppStatus.REVIEW_SUCCESS.getType());
        if (!appInfoService.updateAppInfo(appInfo))
            return JsonResult.failure(ResultCode.UPDATE_ERROR);
        return JsonResult.success();
    }

    @LoginToken
    @GetMapping("/off")
    public JsonResult verifyOff(@RequestParam String appId) {
        AppInfo appInfo = appInfoService.queryAppInfoById(Long.parseLong(appId));
        appInfo.setStatus(AppStatus.REVIEW_FAILED.getType());
        if (!appInfoService.updateAppInfo(appInfo))
            return JsonResult.failure(ResultCode.UPDATE_ERROR);
        return JsonResult.success();
    }
}
