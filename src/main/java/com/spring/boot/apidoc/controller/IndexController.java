package com.spring.boot.apidoc.controller;

import com.spring.boot.apidoc.config.shiro.ShiroRealm;
import com.spring.boot.apidoc.entity.Item;
import com.spring.boot.apidoc.service.ItemService;
import com.spring.boot.apidoc.util.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author yuderen
 * @version 2018/2/26 14:21
 */
@Controller
public class IndexController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/")
    public String index(ModelMap modelMap){
        ShiroRealm.Principal principal = SystemUtils.getPrincipal();
        List<Item> itemList = itemService.getItemList();
        modelMap.addAttribute("user",principal == null ? "" : principal);
        modelMap.addAttribute("itemList",itemList);
        return "index";
    }

}
