package com.spring.boot.apidoc.interceptor;

import com.spring.boot.apidoc.config.ItemVistCfg;
import com.spring.boot.apidoc.service.ItemService;
import com.spring.boot.apidoc.util.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yuderen
 * @version 2018/3/6 14:12
 */
@Aspect
@Component
public class ItemVisitInterceptor {

    @Autowired
    private ItemService itemService;

    @Around("itemPwdVisit()")
    public Object itemPwdCheck(ProceedingJoinPoint joinPoint){
        HttpServletRequest request = getHttpServletRequest();
        try {
            if (checkTokenValid(request)){
                return joinPoint.proceed();
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        String referUrl = request.getRequestURI();
        String itemId = request.getParameter("itemId");
        request.setAttribute("itemId",itemId);
        request.setAttribute("referUrl",referUrl);
        return "item/pwd";
    }

    @Around("itemDocEdit()")
    public Object itemDocEditCheck(ProceedingJoinPoint joinPoint){
        HttpServletRequest request = getHttpServletRequest();
        Long itemId = Long.parseLong(request.getParameter("itemId"));
        if (itemService.isItemMemberEditor(itemId)){
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return "redirect:/";
    }

    @Pointcut("@annotation(com.spring.boot.apidoc.config.ItemPwdVisit)")
    public void itemPwdVisit(){}

    @Pointcut("@annotation(com.spring.boot.apidoc.config.ItemDocEdit)")
    public void itemDocEdit(){}

    private HttpServletRequest getHttpServletRequest(){
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes)ra;
        HttpServletRequest request = sra.getRequest();
        return request;
    }

    private boolean checkTokenValid(HttpServletRequest request){
        String itemId = request.getParameter("itemId");
        String token = CookieUtils.getCookieString(request, ItemVistCfg.ITEM_VISIT_TOKEN + itemId);
        if (StringUtils.isNotBlank(token)){
            String sourceToken = itemService.getItemVisitToken(Long.parseLong(itemId));
            if (token.equals(sourceToken)){
                return true;
            }
        }
        return false;
    }

}
