package com.ncusoft.app_platform.model.enumeration;

/**
 * @author monkJay
 */
public enum  UserType {
    ADMIN("admin", "管理员"),
    DEVELOPER("developer", "开发者");

    private final String type;
    private final String msg;

    UserType(String type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }
}
