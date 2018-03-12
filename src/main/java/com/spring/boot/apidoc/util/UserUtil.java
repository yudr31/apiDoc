package com.spring.boot.apidoc.util;

import com.spring.boot.apidoc.entity.SysUser;
import com.spring.boot.apidoc.repository.SysUserRepository;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author yuderen
 * @version 2018/3/8 16:49
 */
public class UserUtil implements TemplateMethodModelEx {

    @Autowired
    private static SysUserRepository sysUserRepository;

    @Override
    public Object exec(List list) throws TemplateModelException {
        if (list.size() < 2) return "";
        String method = String.valueOf(list.get(0));    //第一个参数为方法名
        String userId = String.valueOf(list.get(1));  //第二个参数为字典类型

        StringBuffer stringBuffer = new StringBuffer();
        switch (method){
            case "getUserName":
                return getUserName(Long.parseLong(userId));
            default:
                return stringBuffer.toString();
        }
    }

    public static String getUserName(Long userId){
        if (null == sysUserRepository){
            sysUserRepository = SpringUtil.getBean(SysUserRepository.class);
        }
        SysUser user = sysUserRepository.findOne(userId);
        return user.getUsername();
    }
}
