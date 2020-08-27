package com.ncusoft.app_platform.controller;

import com.ncusoft.app_platform.model.enumeration.AppPublishStatus;
import com.ncusoft.app_platform.utils.JsonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author Arno Clare
 * @date 14:08 2020/7/7
 */
@RestController
@RequestMapping("/enum")
public class EnumRestController {
    /**
     * 返回发布状态列表
     */
    @GetMapping("/publishstatus")
    public JsonResult listPublishStatus() {
        List<String> statusList = new ArrayList<>();
        for (AppPublishStatus e : AppPublishStatus.values())
            statusList.add(e.getMsg());

        return JsonResult.success(statusList);
    }
}
