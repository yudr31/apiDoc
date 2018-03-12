package com.spring.boot.apidoc.util;

import com.spring.boot.apidoc.config.shiro.ShiroRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;


/**
 * @author yuderen
 * @version 2018/3/7 10:21
 */
public class SystemUtils {

    public static final String DEFAULT_ERROR_KEY_ATTRIBUTE_NAME = "shiroLoginFailure";
    public static final String DEFAULT_USERNAME_PARAM = "username";
    public static final String DEFAULT_PASSWORD_PARAM = "password";
    public static final String DEFAULT_REMEMBER_ME_PARAM = "rememberMe";

    /**
     * 获取授权主要对象
     */
    public static Subject getSubject(){
        return SecurityUtils.getSubject();
    }

    public static void getUrlList(){
//        ResourcesUtil
    }

    /**
     * 获取当前登录者对象
     */
    public static ShiroRealm.Principal getPrincipal(){
        try{
            Subject subject = SecurityUtils.getSubject();
            ShiroRealm.Principal principal = (ShiroRealm.Principal)subject.getPrincipal();
            if (null != principal){
                return principal;
            }
        } catch (UnavailableSecurityManagerException e) {

        } catch (InvalidSessionException e){

        }
        return null;
    }

}
