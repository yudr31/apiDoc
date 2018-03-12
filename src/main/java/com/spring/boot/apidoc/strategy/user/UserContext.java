package com.spring.boot.apidoc.strategy.user;

import com.spring.boot.apidoc.config.shiro.ShiroRealm;
import com.spring.boot.apidoc.entity.Result;
import com.spring.boot.apidoc.entity.SysUser;
import com.spring.boot.apidoc.service.BaseService;
import com.spring.boot.apidoc.service.SysUserService;
import com.spring.boot.apidoc.util.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yuderen
 * @version 2018/3/12 15:06
 */
@Service
public class UserContext extends BaseService{

    @Autowired
    private AdminUser adminUser;
    @Autowired
    private GeneralUser generalUser;
    @Autowired
    private SysUserService sysUserService;

    public Result updateUserPassword(SysUser user, String new_password){
        UserStrategy userStrategy = getUserStrategy();
        SysUser target = sysUserService.findByUserName(user.getUsername());
        if(null == target){
            return errorResult("用户不存在");
        }
        return userStrategy.updateUserPassword(user,target,new_password);
    }

    public UserStrategy getUserStrategy(){
        ShiroRealm.Principal principal = SystemUtils.getPrincipal();
        String userName = principal == null ? "" : principal.getUserName();
        if ("admin".equals(userName)){
            return adminUser;
        }
        return generalUser;
    }

}
