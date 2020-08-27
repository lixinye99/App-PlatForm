package com.ncusoft.app_platform.model.Vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 20:10 2020/7/4
 */
@Data
@NoArgsConstructor
public class AppInfoVo {

    private Long id;
    private String softwareName;
    private String apkName;
    private double softwareSize;
    private String status;
    private String platformName;
    // 外键 - AppCategory
    private String categoryLevel1;
    private String categoryLevel2;
    private String categoryLevel3;
    private long downloads;
    // 外键 - AppVersion
    private String versionNo;

}

