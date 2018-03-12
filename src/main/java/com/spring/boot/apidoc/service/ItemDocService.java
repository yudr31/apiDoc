package com.spring.boot.apidoc.service;

import com.spring.boot.apidoc.entity.ItemDoc;
import com.spring.boot.apidoc.entity.ItemDocMenu;
import com.spring.boot.apidoc.mapper.ItemDocDao;
import com.spring.boot.apidoc.repository.ItemDocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuderen
 * @version 2018/3/1 15:19
 */
@Service
public class ItemDocService {

    @Autowired
    private ItemDocDao itemDocDao;
    @Autowired
    private ItemDocRepository itemDocRepository;

    public List<ItemDocMenu> getItemDocMenuList(ItemDoc itemDoc){
        return itemDocDao.findAllRecursion(itemDoc);
    }

    public List<ItemDoc> getItemDocListByItemId(Long itemId){
        return itemDocRepository.findAllByItemId(itemId);
    }

    public ItemDoc saveItemDoc(ItemDoc itemDoc){
        return itemDocRepository.save(itemDoc);
    }

    public ItemDoc getItemDocByDocId(Long docId){
        if (null == docId){
            return null;
        }
        return itemDocRepository.findOne(docId);
    }

    public Integer updateMenu(ItemDoc itemDoc){
        return itemDocDao.updateItemDoc(itemDoc);
    }

    public Integer removeItemDoc(ItemDoc itemDoc){
        Integer removeCount = 0, updateCount = 0;
        ItemDoc targetDoc = itemDocRepository.findOne(itemDoc.getDocId());
        if (null != targetDoc){
            targetDoc.setStatus(0);
            removeCount = itemDocDao.updateDocStatus(targetDoc);
            if (removeCount > 0){
                updateCount = itemDocDao.updateChildDocParentId(targetDoc);
            }
        }
        return removeCount + updateCount;
    }

    public Integer updateDocStatus(ItemDoc itemDoc){
        return itemDocDao.updateDocStatus(itemDoc);
    }

}
