package com.ncusoft.app_platform.model;

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
public class DevUser extends BaseModel {
    private String devCode;
    private String devName;
    private String devPassword;
    private String devEmail;
    private String devInfo;

    public DevUser(String devCode, String devPassword) {
        this.devCode = devCode;
        this.devPassword = devPassword;
    }

    public DevUser(long uid) {
        super(uid);
    }

    public DevUser(Long uid){
        super(uid);
    }

    public DevUser(Long id, String devCode, String devPassword) {
        super(id);
        this.devCode = devCode;
        this.devPassword = devPassword;
    }
}
