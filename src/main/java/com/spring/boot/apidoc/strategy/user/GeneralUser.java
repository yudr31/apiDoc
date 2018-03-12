package com.spring.boot.apidoc.strategy.user;

import com.spring.boot.apidoc.entity.Result;
import com.spring.boot.apidoc.entity.SysUser;
import com.spring.boot.apidoc.service.BaseService;
import com.spring.boot.apidoc.service.SysUserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yuderen
 * @version 2018/3/12 15:05
 */
@Service
public class GeneralUser extends BaseService implements UserStrategy {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public Result updateUserPassword(SysUser user, SysUser target, String new_password) {
        Object pwd = new SimpleHash("MD5",user.getPassword(), ByteSource.Util.bytes(target.getCredentialsSalt()),3);
        if(pwd.toString().equals(target.getPassword())){
            return sysUserService.changePassword(user,target,new_password);
        }
        return errorResult("原始密码不正确");
    }
}
