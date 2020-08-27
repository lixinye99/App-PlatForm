package com.ncusoft.app_platform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;

/**
 * @author Arno Clare
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AppInfo extends BaseModel {
    private String softwareName;
    @Column(name = "apk_name")
    private String apkName;
    @Column(name = "support_rom")
    private String supportROM;
    private String interfaceLanguage;
    private Double softwareSize;
    private Date updateDate;
    // 外键 - DevUser
    private Long devId;
    private String appInfo;
    private Integer status;
    private Date onSaleDate;
    private Date offSaleDate;
    private Integer platformId;
    // 外键 - AppCategory
    private Long categoryLevel1;
    private Long categoryLevel2;
    private Long categoryLevel3;
    private Long downloads;
    @Column(name = "logo_pic_path")
    private String logoPicPath;
    private String logoLocPath;
    // 外键 - AppVersion
    private Long versionId;

    public AppInfo(Long id) {
        super(id);
    }
}
