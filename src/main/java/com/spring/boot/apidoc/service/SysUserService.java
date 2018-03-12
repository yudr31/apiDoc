package com.spring.boot.apidoc.service;

import com.spring.boot.apidoc.entity.Result;
import com.spring.boot.apidoc.entity.SysUser;
import com.spring.boot.apidoc.repository.SysUserRepository;
import com.spring.boot.apidoc.util.MD5Utils;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuderen
 * @version 2018/3/7 10:17
 */
@Service
public class SysUserService extends BaseService{

    @Autowired
    private SysUserRepository sysUserRepository;

    public List<SysUser> findUserList(){
        return (List<SysUser>) sysUserRepository.findAll();
    }

    public SysUser findByUserName(String userName){
        return sysUserRepository.findByUsername(userName);
    }

    public Result changePassword(SysUser user, SysUser target, String passwordNew){
        Object pwdNew = new SimpleHash("MD5",passwordNew,ByteSource.Util.bytes(target.getCredentialsSalt()),3);
        target.setPassword(pwdNew.toString());
        SysUser sysUser = sysUserRepository.save(target);
        if (null != sysUser){
            return successResult(sysUser);
        }
        return errorResult("系统错误！");
    }

    /**
     * 注册用户
     * @param user
     * @return
     */
    public boolean registerUser(SysUser user){
        user.setSalt(Hex.encodeToString(MD5Utils.generateSalt(16)));
        Object password = new SimpleHash("MD5",user.getPassword(),ByteSource.Util.bytes(user.getCredentialsSalt()),3);
        user.setPassword(password.toString());
        SysUser sysUser = sysUserRepository.save(user);
        if (null != sysUser){
            return true;
        }
        return false;
    }

}
