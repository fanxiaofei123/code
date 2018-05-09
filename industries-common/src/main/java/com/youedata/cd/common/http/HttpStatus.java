package com.youedata.cd.common.http;

/**
 * Created by honshe on 2016/5/31.
 */
public enum HttpStatus {
    OK(200, "成功"),
    NOT_FOUND(404, "没有找到相关资源"),
    INTERNAL_SERVER_ERROR(500, "服务器错误");
    private final int value;
    private final String reasonPhrase;

    HttpStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int value() {
        return this.value;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    public String toString() {
        return Integer.toString(this.value);
    }

    public static HttpStatus valueOf(int statusCode) {
        HttpStatus[] arr$ = values();
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            HttpStatus status = arr$[i$];
            if(status.value == statusCode) {
                return status;
            }
        }

        throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
    }

}
