package com.spring.boot.apidoc.strategy.showpage;

import com.spring.boot.apidoc.entity.ItemDoc;
import com.spring.boot.apidoc.service.ItemDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author yuderen
 * @version 2018/3/6 10:15
 */
@Service
public class SinglePage implements ItemPageStrategr {

    @Autowired
    private ItemDocService itemDocService;

    @Override
    public String getItemPage(ItemDoc itemDoc, ModelMap modelMap) {
        List<ItemDoc> itemDocList = itemDocService.getItemDocListByItemId(itemDoc.getItemId());
        ItemDoc showDoc = CollectionUtils.isEmpty(itemDocList) ? new ItemDoc() : itemDocList.get(0);
        modelMap.addAttribute("showDoc", showDoc);
        return "item/singlePage";
    }
}
