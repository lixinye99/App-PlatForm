package com.ncusoft.app_platform;

import com.ncusoft.app_platform.controller.AppVersionController;
import com.ncusoft.app_platform.model.Bo.AppVersionFormBo;
import com.ncusoft.app_platform.service.AppVersionService;
import com.ncusoft.app_platform.service.impl.AppVersionServiceImpl;
import com.ncusoft.app_platform.utils.JsonResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author Arno Clare
 * @date 10:12 2020/7/6
 */
@SpringBootTest
public class AppVersionControllerTest {
    AppVersionController controller;
    AppVersionService appVersionService;

    @Autowired
    public AppVersionControllerTest(AppVersionController controller, AppVersionService appVersionService) {
        this.controller = controller;
        this.appVersionService = appVersionService;
    }

    @Test
    public void listHistoryAppVersionTest() {
//        JsonResult result = controller.listHistoryAppVersion("12");
//        System.out.println(result);
    }

    @Test
    public void addAppVersionTest() {
//        AppVersionFormBo appVersionFormBo = appVersionService.getAppVersionFormBo(16);
//        appVersionFormBo.setVersionSize(233.5);
//        appVersionFormBo.setVersionId(null);
//        appVersionFormBo.setVersionNo("1.1.4");
//        appVersionFormBo.setVersionInfo("test");
//        appVersionFormBo.setApkFile(null);
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        request.setCharacterEncoding("UTF-8");
//        request.setAttribute("userId", "16");
//        JsonResult result = controller.addAppVersion("16", appVersionFormBo, request);
//        System.out.println(result);
    }
}
