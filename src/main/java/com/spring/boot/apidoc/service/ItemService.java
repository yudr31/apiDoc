package com.spring.boot.apidoc.service;

import com.spring.boot.apidoc.config.ItemVistCfg;
import com.spring.boot.apidoc.config.shiro.ShiroRealm;
import com.spring.boot.apidoc.entity.Item;
import com.spring.boot.apidoc.entity.ItemMember;
import com.spring.boot.apidoc.repository.ItemMemberRepository;
import com.spring.boot.apidoc.repository.ItemRepository;
import com.spring.boot.apidoc.util.DESUtil;
import com.spring.boot.apidoc.util.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author yuderen
 * @version 2018/3/1 14:57
 */
@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemMemberRepository itemMemberRepository;

    public List<Item> getItemList(){
        return (List<Item>) itemRepository.findAll();
    }

    public Item saveItem(Item item){
        return itemRepository.save(item);
    }

    public Item getItemByItemId(Long itemId){
        return itemRepository.findOne(itemId);
    }

    public String getItemVisitToken(Long itemId){
        Item item = itemRepository.findOne(itemId);
        String sourceToken = ItemVistCfg.ITEM_VISIT_TOKEN + "|" + item.getItemName() + "|" + item.getItemId() + "|";
        return DESUtil.encryptDes(sourceToken,item.getPassword(),"UTF-8");
    }

    public Boolean isItemMemberEditor(Long itemId){
        ShiroRealm.Principal principal = SystemUtils.getPrincipal();
        if (null != principal){
            Item item = itemRepository.findOne(itemId);
            List<ItemMember> itemMemberList = itemMemberRepository.findAllByItemIdAndUserName(itemId,principal.getUserName());
            if (item.getCreateUser() == principal.getId() || !CollectionUtils.isEmpty(itemMemberList)){
                return true;
            }
        }
        return false;
    }

}
