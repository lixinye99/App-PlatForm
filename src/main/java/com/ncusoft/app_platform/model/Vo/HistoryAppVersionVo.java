package com.ncusoft.app_platform.model.Vo;

import com.ncusoft.app_platform.model.enumeration.AppPublishStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import java.util.Date;

/**
 * @author Arno Clare
 * @date 22:31 2020/7/5
 */
@Data
@NoArgsConstructor
@ToString
public class HistoryAppVersionVo {
    // 用于获取版本信息以进行修改
    private Long versionId;
    private String softwareName;
    private String versionNo;
    private Double versionSize;
    private Integer publishStatus;
    private String publishStatusName;
    private String apkFileName;
    private String downloadLink;
    private Date modifyDate;

    public void setPublishStatus(Integer publishStatus) {
        this.publishStatus = publishStatus;
        this.publishStatusName = AppPublishStatus.getValue(publishStatus);
    }
}
