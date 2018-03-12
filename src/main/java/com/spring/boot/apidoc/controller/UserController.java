package com.spring.boot.apidoc.controller;

import com.alibaba.fastjson.JSONObject;
import com.spring.boot.apidoc.config.shiro.ShiroRealm;
import com.spring.boot.apidoc.entity.Result;
import com.spring.boot.apidoc.entity.SysUser;
import com.spring.boot.apidoc.service.SysUserService;
import com.spring.boot.apidoc.strategy.user.UserContext;
import com.spring.boot.apidoc.util.SystemUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yuderen
 * @version 2018/3/1 14:12
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private UserContext userContext;

    @RequestMapping(value="/login", method= RequestMethod.GET)
    public String login(HttpServletRequest request, ModelMap modelMap){
        String referUrl = request.getParameter("refer_url");
        String docId = request.getParameter("docId");
        if (StringUtils.isNotBlank(docId)){
            referUrl = referUrl + "&docId=" + docId;
        }
        modelMap.addAttribute("referUrl",referUrl);
        return"user/login";
    }

    @RequestMapping("/redirect")
    public void redirect(HttpServletResponse response){
        renderJson(response, JSONObject.toJSONString(successResult("")));
    }

    @RequestMapping(value="/login", method= RequestMethod.POST)
    public void login(HttpServletRequest request, HttpServletResponse response){
        // 登录失败从request中获取shiro处理的异常信息。
        // shiroLoginFailure:就是shiro异常类的全类名.
        String exception = (String) request.getAttribute("shiroLoginFailure");
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                renderJson(response, JSONObject.toJSONString(errorResult("账号不存在")));
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                renderJson(response, JSONObject.toJSONString(errorResult("密码不正确")));
            } else if ("kaptchaValidateFailed".equals(exception)) {
                renderJson(response, JSONObject.toJSONString(errorResult("验证码错误")));
            } else {
                renderJson(response, JSONObject.toJSONString(errorResult("未知错误")));
            }
            return;
        }
        renderJson(response, JSONObject.toJSONString(successResult("")));
    }

    @RequestMapping(value = "/register", method= RequestMethod.GET)
    public String register(HttpServletRequest request, ModelMap modelMap){
        String referUrl = request.getParameter("refer_url");
        String docId = request.getParameter("docId");
        if (StringUtils.isNotBlank(docId)){
            referUrl = referUrl + "&docId=" + docId;
        }
        modelMap.addAttribute("referUrl",referUrl);
        return "user/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(HttpServletResponse response, SysUser user){
        SysUser sysUser = sysUserService.findByUserName(user.getUsername());
        if (sysUser != null){
            renderJson(response, JSONObject.toJSONString(errorResult("用户名已存在")));
            return;
        }
        boolean success = sysUserService.registerUser(user);
        if (success){
            renderJson(response, JSONObject.toJSONString(successResult("")));
        }
    }

    @RequestMapping(value = "/setting", method= RequestMethod.GET)
    public String setting(ModelMap modelMap){
        ShiroRealm.Principal principal = SystemUtils.getPrincipal();
        SysUser sysUser = sysUserService.findByUserName(principal.getUserName());
        modelMap.addAttribute("user",sysUser);
        return "user/setting";
    }

    @RequestMapping(value = "/setting", method= RequestMethod.POST)
    public void setting(SysUser user, HttpServletRequest request, HttpServletResponse response){
        String new_password = request.getParameter("new_password");
        Result result = userContext.updateUserPassword(user,new_password);
        renderJson(response, JSONObject.toJSONString(result));
    }

}
