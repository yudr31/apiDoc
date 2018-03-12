package com.spring.boot.apidoc.strategy.showpage;

import com.spring.boot.apidoc.entity.ItemDoc;
import com.spring.boot.apidoc.entity.ItemDocMenu;
import com.spring.boot.apidoc.service.ItemDocService;
import com.spring.boot.apidoc.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author yuderen
 * @version 2018/3/6 10:24
 */
@Service
public class RegularPage implements ItemPageStrategr {

    @Autowired
    private ItemDocService itemDocService;

    @Override
    public String getItemPage(ItemDoc itemDoc, ModelMap modelMap) {
        ItemDoc showDoc = itemDocService.getItemDocByDocId(itemDoc.getDocId());
        List<ItemDocMenu> itemDocList = itemDocService.getItemDocMenuList(itemDoc);
        if (null == showDoc && !CollectionUtils.isEmpty(itemDocList)){
            showDoc = itemDocList.get(0);
        }
        String docMenu =  PageUtils.getMenuString(itemDocList,showDoc);
        modelMap.addAttribute("showDoc", showDoc);
        modelMap.addAttribute("docMenu", docMenu);
        return "item/regularPage";
    }
}
