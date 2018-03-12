package com.spring.boot.apidoc.strategy.showpage;

import com.spring.boot.apidoc.entity.Item;
import com.spring.boot.apidoc.entity.ItemDoc;
import com.spring.boot.apidoc.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

/**
 * 负责与具体的策略类交互
 * 实现具体的算法和直接的客户端覅用分离了，使得算法可以独立煜客户端的变化
 * @author yuderen
 * @version 2018/3/6 10:26
 */
@Service
public class ItemPageContext {

    @Autowired
    private ItemService itemService;
    @Autowired
    private RegularPage regularPage;
    @Autowired
    private SinglePage singlePage;

    public String showItemPageInfo(ItemDoc itemDoc, ModelMap modelMap){
        Item item = itemService.getItemByItemId(itemDoc.getItemId());
        ItemPageStrategr itemPageStrategr = getItemPageStrategr(item);
        modelMap.addAttribute("item", item);
        itemDoc.setStatus(1);
        return itemPageStrategr.getItemPage(itemDoc,modelMap);
    }

    private ItemPageStrategr getItemPageStrategr(Item item){
        if ("1".equals(item.getItemType())){
            return regularPage;
        } else if ("2".equals(item.getItemType())){
            return singlePage;
        }
        throw new RuntimeException("错误的项目类型！");
    }

}
