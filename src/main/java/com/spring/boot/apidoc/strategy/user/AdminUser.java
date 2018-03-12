package com.spring.boot.apidoc.strategy.user;

import com.spring.boot.apidoc.entity.Result;
import com.spring.boot.apidoc.entity.SysUser;
import com.spring.boot.apidoc.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yuderen
 * @version 2018/3/12 15:04
 */
@Service
public class AdminUser implements UserStrategy {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public Result updateUserPassword(SysUser user, SysUser target, String new_password) {
        return sysUserService.changePassword(user,target,new_password);
    }
}
