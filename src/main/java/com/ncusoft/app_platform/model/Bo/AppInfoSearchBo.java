package com.ncusoft.app_platform.model.Bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 8:41 2020/7/5
 */
@Data
@NoArgsConstructor
public class AppInfoSearchBo {
    private String appName;
    private String appStatus;
    private String categoryLevel1;
    private String categoryLevel2;
    private String categoryLevel3;
    private String platformName;
}
