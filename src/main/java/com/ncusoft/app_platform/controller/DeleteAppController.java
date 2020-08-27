package com.ncusoft.app_platform.controller;

import com.ncusoft.app_platform.selfannotation.LoginToken;
import com.ncusoft.app_platform.service.AppInfoService;
import com.ncusoft.app_platform.utils.JsonResult;
import com.ncusoft.app_platform.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Arno Clare
 * @date 22:53 2020/7/5
 */
@RestController
@LoginToken
@RequestMapping("/app")
public class DeleteAppController {

    private final AppInfoService appInfoService;

    @Autowired
    public DeleteAppController(AppInfoService appInfoService) {
        this.appInfoService = appInfoService;
    }

    @RequestMapping("/delete/{appId}")
    public JsonResult deleteApp(@PathVariable String appId) {
        // TODO 验证删除权限
        /*删除 app*/
        int deleteRes = appInfoService.deleteApp(Long.parseLong(appId));
        /*返回删除结果*/
        if (deleteRes == -1) {
            return JsonResult.failure(ResultCode.PARAM_IS_INVALID);
        } else if (deleteRes == 0) {
            return JsonResult.failure(ResultCode.DELETE_ERROR);
        }
        return JsonResult.success();
    }
}
