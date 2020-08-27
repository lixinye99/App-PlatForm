package com.ncusoft.app_platform.model.enumeration;

/**
 * @author Arno Clare
 */
public enum AppPublishStatus {
    NOT_PUBLISH(1, "不发布"),
    PUBLISH(2, "发布"),
    PRE_PUBLISH(3, "预发布");

    private final int type;
    private final String msg;

    AppPublishStatus(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    /**
     * 通过 msg 查找 type
     * @return 若未查到则返回 -1
     */
    public static int getValue(String msg) {
        for (AppPublishStatus e : AppPublishStatus.values()) {
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
        for (AppPublishStatus e : AppPublishStatus.values()) {
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
