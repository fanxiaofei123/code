package com.youedata.cd.common.http;

/**
 * Created by honshe on 2016/5/31.
 */
public class ResponseResult<T> {

    private int code;
    private String msg;
    private T data;

    public ResponseResult() {
        this.code = 200;
    }

    public ResponseResult(HttpStatus httpStatus) {
        this.code = httpStatus.value();
        this.msg = httpStatus.getReasonPhrase();
    }

    public ResponseResult(HttpStatus httpStatus,T data) {
        this.code = httpStatus.value();
        this.msg = httpStatus.getReasonPhrase();
        this.data = data;
    }

    public ResponseResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
