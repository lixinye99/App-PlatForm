package com.ncusoft.app_platform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Arno Clare
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdPromotion extends BaseModel {
    // 外键 - AppInfo
    private Long appId;
    private String adPicPath;
    private Long adPV;
    private Long carouselPosition;
    private Date startTime;
    private Date endTime;

    public AdPromotion(Long appId) {
        this.appId = appId;
    }

    public AdPromotion(Long id, Long appId) {
        super(id);
        this.appId = appId;
    }
}
