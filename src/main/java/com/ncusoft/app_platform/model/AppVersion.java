package com.ncusoft.app_platform.model;

import com.ncusoft.app_platform.model.enumeration.AppPublishStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Arno Clare
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AppVersion extends BaseModel {
    // 外键 - AppInfo
    private Long appId;
    private String versionNo;
    private String versionInfo;
    private Integer publishStatus;
    private String downloadLink;
    private Double versionSize;
    private String apkLocPath;
    private String apkFileName;

    public AppVersion(Long appId) {
        this.appId = appId;
    }

    public AppVersion(Long appId, String versionNo) {
        this.appId = appId;
        this.versionNo = versionNo;
    }

    public AppVersion(Long appId, String versionNo, Integer publishStatus) {
        this.appId = appId;
        this.versionNo = versionNo;
        this.publishStatus = publishStatus;
    }

    public AppVersion(Long id, Long appId) {
        super(id);
        this.appId = appId;
    }

    public AppVersion(Long id, Long appId, String versionNo) {
        super(id);
        this.appId = appId;
        this.versionNo = versionNo;
    }

    public AppVersion(Long id, Long appId, String versionNo, Integer publishStatus) {
        super(id);
        this.appId = appId;
        this.versionNo = versionNo;
        this.publishStatus = publishStatus;
    }
}
