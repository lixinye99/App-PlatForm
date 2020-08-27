package com.ncusoft.app_platform.model.Vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 12:07 2020/7/6
 */
@Data
@NoArgsConstructor
public class AppInfoFormVo {
    private String softwareName;
    private String APKName;
    private String supportROM;
    private String interfaceLanguage;
    private Double softwareSize;
    private Long downloads;
    private String platformName;
    private String categoryLevel1;
    private String categoryLevel2;
    private String categoryLevel3;
    private String status;
    private String appInfo;
    private String imagePath;
}
