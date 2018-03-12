package com.spring.boot.apidoc.config.jpa;

import com.spring.boot.apidoc.config.shiro.ShiroRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.data.domain.AuditorAware;

/**
 * @author yuderen
 * @version 2018/3/8 14:39
 */
public class SpringSecurityAuditorAware implements AuditorAware<Long> {

    @Override
    public Long getCurrentAuditor() {
        Subject subject = SecurityUtils.getSubject();
        ShiroRealm.Principal principal = (ShiroRealm.Principal)subject.getPrincipal();
        return principal.getId();
    }
}
