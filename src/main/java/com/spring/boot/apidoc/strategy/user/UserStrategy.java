package com.spring.boot.apidoc.strategy.user;

import com.spring.boot.apidoc.entity.Result;
import com.spring.boot.apidoc.entity.SysUser;

/**
 * @author yuderen
 * @version 2018/3/12 15:01
 */
public interface UserStrategy {

    Result updateUserPassword(SysUser user, SysUser target, String new_password);

}
