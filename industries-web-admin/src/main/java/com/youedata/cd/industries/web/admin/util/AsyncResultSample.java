package com.youedata.cd.industries.web.admin.util;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/28.
 */
public class AsyncResultSample{
    private String message;
    private int status;
    private Map<String,Object> data = new HashMap<String,Object>();

    public AsyncResultSample() {
        this.status = HttpStatus.OK.value();
        this.message= "成功";
    }

    public AsyncResultSample(HttpStatus status, String message) {
        this.status = status.value();
        this.message= message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public AsyncResultSample(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public void putData(String key, Object value) {
        this.data.put(key, value);
    }

    public Map<String, Object> getData() {
        return data;
    }

    public String getMessage() {
        return this.message;
    }

    public int getStatus() {
        return this.status;
    }
}
