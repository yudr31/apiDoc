package com.spring.boot.apidoc.entity;

/**
 * @author yuderen
 * @version 2018/3/7 10:21
 */
public class Result {

    private Integer status =200;//默认成功
    private String message;
    private Object object;

    public Result(Integer status,String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

}
