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
public class BackendUser extends BaseModel {
    private String userCode;
    private String userName;
    private String userPassword;
//    // 用处不明
//    private Long userType;

    public BackendUser(String userCode, String userPassword) {
        this.userCode = userCode;
        this.userPassword = userPassword;
    }

    public BackendUser(long id) {
        super(id);
    }

    public BackendUser(Long id) {
        super(id);
    }

    public BackendUser(Long id, String userCode, String userPassword) {
        super(id);
        this.userCode = userCode;
        this.userPassword = userPassword;
    }
}
