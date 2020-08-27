package com.ncusoft.app_platform.model.enumeration;

/**
 * @author Arno Clare
 */
public enum AppStatus {
    REVIEW_WAIT(1, "待审核"),
    REVIEW_SUCCESS(2, "审核通过"),
    REVIEW_FAILED(3, "审核未通过"),
    ON_SHELF(4, "上架"),
    OFF_SHELF(5, "下架");

    private final int type;
    private final String msg;

    AppStatus(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    /**
     * 通过 msg 查找 type
     * @return 若未查到则返回 -1
     */
    public static int getValue(String msg) {
        for (AppStatus e : AppStatus.values()) {
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
        for (AppStatus e : AppStatus.values()) {
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
