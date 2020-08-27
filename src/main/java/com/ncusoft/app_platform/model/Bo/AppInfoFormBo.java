package com.ncusoft.app_platform.model.Bo;

import com.ncusoft.app_platform.model.AppInfo;
import com.ncusoft.app_platform.model.enumeration.AppPlatform;
import com.ncusoft.app_platform.model.enumeration.AppStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 10:25 2020/7/5
 */
@Data
@NoArgsConstructor
public class AppInfoFormBo {
    private String softwareName;
    private String apkName;
    private String supportROM;
    private String interfaceLanguage;
    private Double softwareSize;
    private Long downloads;
    private String platformName;
    private String categoryLevel1;
    private String categoryLevel2;
    private String categoryLevel3;
    private String appInfo;
    private MultipartFile imgFile;

    public AppInfo changeToAppInfo(){
        AppInfo appInfo = new AppInfo();
        appInfo.setSoftwareName(this.getSoftwareName());
        appInfo.setApkName(this.getApkName());
        appInfo.setSupportROM(this.getSupportROM());
        appInfo.setInterfaceLanguage(this.getInterfaceLanguage());
        appInfo.setSoftwareSize(this.getSoftwareSize());
        appInfo.setDownloads(this.getDownloads());
        appInfo.setPlatformId(AppPlatform.getValue(this.getPlatformName()));
        appInfo.setStatus(AppStatus.REVIEW_WAIT.getType());
        appInfo.setAppInfo(this.getAppInfo());
        return appInfo;
    }

}
