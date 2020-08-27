package com.ncusoft.app_platform.model.Bo;

import com.ncusoft.app_platform.model.AppVersion;
import com.ncusoft.app_platform.model.enumeration.AppPublishStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Arno Clare
 * @date 11:47 2020/7/6
 */
@Data
@NoArgsConstructor
@ToString
public class AppVersionFormBo {
    private Long versionId;
    private Long appId;
    private Long createBy;
    private Long modifyBy;

    private String versionNo;
    private Double versionSize;
    private String publishStatusName;
    private String versionInfo;
    private MultipartFile apkFile;
    private String apkFileName;
    private String apkFilePath;

    /**
     * 将字段相应的存入 AppVersion
     * file 需要另行处理
     * @return 存有相应字段的 AppVersion
     */
    public AppVersion toAppVersion() {
        AppVersion appVersion = new AppVersion();
        appVersion.setId(versionId);
        appVersion.setAppId(appId);
        appVersion.setVersionNo(versionNo);
        appVersion.setVersionSize(versionSize);
        appVersion.setPublishStatus(AppPublishStatus.getValue(publishStatusName));
        appVersion.setVersionInfo(versionInfo);
        appVersion.setCreateBy(createBy);
        appVersion.setModifyBy(modifyBy);

        appVersion.setApkFileName(apkFile.getOriginalFilename());
        appVersion.setDownloadLink(apkFilePath);
        appVersion.setApkLocPath(apkFilePath);

        return appVersion;
    }

    public static AppVersionFormBo toAppVersionFormBo(AppVersion appVersion) {
        AppVersionFormBo appVersionFormBo = new AppVersionFormBo();
        appVersionFormBo.setVersionId(appVersion.getId());
        appVersionFormBo.setAppId(appVersion.getAppId());
        appVersionFormBo.setVersionNo(appVersion.getVersionNo());
        appVersionFormBo.setVersionSize(appVersion.getVersionSize());
        appVersionFormBo.setPublishStatusName(AppPublishStatus.getValue(appVersion.getPublishStatus()));
        appVersionFormBo.setVersionInfo(appVersion.getVersionInfo());

        appVersionFormBo.setApkFileName(appVersion.getApkFileName());
        appVersionFormBo.setApkFilePath(appVersion.getApkLocPath());

        return appVersionFormBo;
    }

    /**
     * 检查是否有表单字段为 null 或 empty
     * @return true - 存在空字段, false - 不存在空字段
     */
    public boolean isFormFieldBlank() {
        return appId == null
            || StringUtils.isBlank(versionNo)
            || versionSize == null
            || StringUtils.isBlank(publishStatusName)
            || StringUtils.isBlank(versionInfo)
        ;
    }

    /**
     * 检查文件是否符合 apk 格式
     * 检查文件是否符合 500M 以内
     * @return true - 符合, false - 不符合
     */
    public boolean fileCheck() {
        if (apkFile != null) {
            String originalFilename = apkFile.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            return suffix.equalsIgnoreCase(".apk") && apkFile.getSize() <= 500 * 1024 * 1024;
        }
        return false;
    }
}
