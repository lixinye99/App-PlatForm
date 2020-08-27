package com.ncusoft.app_platform;

import com.alibaba.fastjson.JSONObject;
import com.ncusoft.app_platform.model.AppInfo;
import com.ncusoft.app_platform.model.Bo.AppInfoSearchBo;
import com.ncusoft.app_platform.model.Vo.AppInfoVo;
import com.ncusoft.app_platform.model.enumeration.AppPlatform;
import com.ncusoft.app_platform.service.AppInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 8:12 2020/7/6
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestCRUD {

    @Resource
    AppInfoService appInfoService;

    @Test
    public void test1(){
        AppInfo appInfo = new AppInfo();
        appInfo.setSoftwareName("测试分页qweqw");
        appInfo.setApkName("阿克苏了解的adasd拉伸");
        appInfo.setUpdateDate(new Date());
        appInfo.setDevId(Long.parseLong("1"));
        appInfo.setCategoryLevel1(Long.valueOf(1));
        appInfo.setCategoryLevel2(Long.valueOf(2));
        appInfo.setCategoryLevel3(Long.valueOf(3));
        appInfo.setSupportROM("华为");
        appInfo.setPlatformId(1);
        appInfo.setInterfaceLanguage("英文");
        appInfo.setSoftwareSize(Double.valueOf(20));
        appInfo.setStatus(1);
        appInfo.setDownloads(Long.valueOf(23));
        appInfo.setVersionId(Long.valueOf(16));
        appInfo.setPlatformId(AppPlatform.MOBILE.getType());
        int insert = appInfoService.addAppInfo(appInfo);
        System.out.println(insert);
    }

    @Test
    public void test2(){
        boolean b = appInfoService.queryAPKNameExist("com.bithack.apparayus");
        System.out.println(b);
    }

    @Test
    public void test3(){
        Long id = appInfoService.queryCategoryIdByName("全部游戏");
        System.out.println(id);
    }

    @Test
    public void test4(){
        AppInfoSearchBo info = new AppInfoSearchBo();
        info.setAppName("机械世界");
        info.setAppStatus("待审核");
        info.setCategoryLevel1("全部游戏");
        info.setCategoryLevel2("益智游戏");
        info.setCategoryLevel3("物理");
        info.setPlatformName("手机");
        AppInfoVo appInfo = appInfoService.searchByInfo(info, Long.valueOf(16));
        System.out.println(appInfo);
    }

    @Test
    public void test5(){
        List<String> strings = appInfoService.queryLevelOne();
        System.out.println(strings);
    }

    @Test
    public void test6(){
        List<String> list = appInfoService.queryLevel("益智游戏");
        System.out.println(list);
    }

    @Test
    public void test7(){
        List<AppInfoVo> appListInfoByUserId = appInfoService.findAppListInfoByUserId(Long.valueOf(1), 1);
        for (AppInfoVo appInfoVo : appListInfoByUserId) {
            System.out.println(appInfoVo);
        }
    }

    @Test
    public void test8(){
        int total = appInfoService.queryPageTotal((long) 1);
        System.out.println(total);
    }

    @Test
    public void test9(){
        JSONObject jsonObject = appInfoService.readyForSearchOrAdd();
        for (Object value : jsonObject.values()) {
            System.out.println(value);
        }
    }

    @Test
    public void test10(){
        AppInfo appInfo = new AppInfo();
        appInfo.setId((long) 22);
        appInfo.setSoftwareName("测试分页修改");
        appInfo.setApkName("修改app信息");
        appInfo.setUpdateDate(new Date());
        appInfo.setDevId(Long.parseLong("1"));
        appInfo.setCategoryLevel1(Long.valueOf(1));
        appInfo.setCategoryLevel2(Long.valueOf(2));
        appInfo.setCategoryLevel3(Long.valueOf(3));
        appInfo.setSupportROM("苹果");
        appInfo.setPlatformId(2);
        appInfo.setInterfaceLanguage("英文和中文");
        appInfo.setSoftwareSize(Double.valueOf(25));
        appInfo.setStatus(2);
        appInfo.setDownloads(Long.valueOf(23));
        appInfo.setVersionId(Long.valueOf(16));
        appInfo.setPlatformId(AppPlatform.MOBILE.getType());
        boolean b = appInfoService.updateAppInfo(appInfo);
        System.out.println(b);
    }


}
