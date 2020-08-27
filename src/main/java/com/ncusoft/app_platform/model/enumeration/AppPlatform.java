package com.ncusoft.app_platform.model.enumeration;

/**
 * @author Arno Clare
 */
public enum AppPlatform {
    MOBILE(1, "手机"),
    TABLET(2, "平板"),
    COMMON(3, "通用");

    private final int type;
    private final String msg;

    AppPlatform(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    /**
     * 通过 msg 查找 type
     * @return 若未查到则返回 -1
     */
    public static int getValue(String msg) {
        for (AppPlatform e : AppPlatform.values()) {
            if (e.getMsg().equals(msg))
                return e.getType();
        }
        return -1;
    }

    /**
     * 通过 type 查找 msg
     * @return 若未查到则返回 null
     */
    public static String getValue(int type) {
        for (AppPlatform e : AppPlatform.values()) {
            if (e.getType() == type)
                return e.getMsg();
        }
        return null;
    }

    public int getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }
}
