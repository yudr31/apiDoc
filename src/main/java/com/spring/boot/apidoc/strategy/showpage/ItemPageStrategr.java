package com.spring.boot.apidoc.strategy.showpage;

import com.spring.boot.apidoc.entity.ItemDoc;
import org.springframework.ui.ModelMap;

/**
 * @author yuderen
 * @version 2018/3/6 10:08
 */
public interface ItemPageStrategr {

    String getItemPage(ItemDoc itemDoc, ModelMap modelMap);

}
