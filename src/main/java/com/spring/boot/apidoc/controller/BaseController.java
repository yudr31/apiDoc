package com.spring.boot.apidoc.controller;

import com.alibaba.fastjson.JSONObject;
import com.spring.boot.apidoc.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yuderen
 * @version 2018/3/5 11:35
 */
public class BaseController {

    private Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 客户端返回JSON字符串
     * @param response
     * @param object
     * @return
     */
    protected String renderString(HttpServletResponse response, Object object) {
        return renderString(response, JSONObject.toJSONString(object), "application/json");
    }

    /**
     * 客户端返回字符串
     * @param response
     * @param string
     * @return
     */
    protected String renderString(HttpServletResponse response, String string, String type) {
        try {
            response.reset();
            response.setContentType(type);
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public Result successResult(Object object){
        Result result = new Result(200,"成功");
        result.setObject(object);
        return result;
    }

    public Result errorResult(String message){
        Result result = new Result(300,message);
        return result;
    }

    public void renderJson(HttpServletResponse response, String text) {
        render(response, "application/json;charset=UTF-8", text);
    }

    public void render(HttpServletResponse response, String contentType, String text) {
        response.setContentType(contentType);
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);

        try {
            response.getWriter().write(text);
        } catch (IOException var4) {
            logger.error(var4.getMessage(), var4);
        }

    }

}
