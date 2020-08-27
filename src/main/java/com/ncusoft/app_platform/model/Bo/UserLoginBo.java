package com.ncusoft.app_platform.model.Bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 前端传来的登录信息的封装类
 * @author monkJay
 */
@Data
@NoArgsConstructor
public class UserLoginBo {
    private String devName;
    private String devPassword;
    private String userType;
}
