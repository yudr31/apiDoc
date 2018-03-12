package com.spring.boot.apidoc.mapper;

import com.spring.boot.apidoc.entity.ItemDoc;
import com.spring.boot.apidoc.entity.ItemDocMenu;

import java.util.List;

/**
 * @author yuderen
 * @version 2018/3/1 14:59
 */
public interface ItemDocDao {

    List<ItemDoc> findItemDocList(ItemDoc itemDoc);

    List<ItemDocMenu> findAllRecursion(ItemDoc itemDoc);

    Integer updateItemDoc(ItemDoc itemDoc);

    Integer updateDocStatus(ItemDoc itemDoc);

    Integer updateChildDocParentId(ItemDoc itemDoc);

}
