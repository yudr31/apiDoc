package com.spring.boot.apidoc.repository;

import com.spring.boot.apidoc.entity.SysUser;
import org.springframework.data.repository.CrudRepository;

/**
 * @author yuderen
 * @version 2018/3/7 10:18
 */
public interface SysUserRepository extends CrudRepository<SysUser,Long>{

    /**通过username查找用户信息;*/
    SysUser findByUsername(String username);

}
