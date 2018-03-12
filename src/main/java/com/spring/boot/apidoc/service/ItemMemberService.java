package com.spring.boot.apidoc.service;

import com.spring.boot.apidoc.entity.ItemMember;
import com.spring.boot.apidoc.repository.ItemMemberRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuderen
 * @version 2018/3/12 8:55
 */
@Service
public class ItemMemberService {

    @Autowired
    private ItemMemberRepository itemMemberRepository;

    public List<ItemMember> getItemMemberListByItemId(ItemMember itemMember){
        return itemMemberRepository.findAllByItemId(itemMember.getItemId());
    }

    public List<ItemMember> getItemMemberListByItemIdAndUserName(ItemMember itemMember){
        return itemMemberRepository.findAllByItemIdAndUserName(itemMember.getItemId(),itemMember.getUserName());
    }

    public ItemMember saveItemMember(ItemMember itemMember){
        if (StringUtils.isNotBlank(itemMember.getUserName())){
            return itemMemberRepository.save(itemMember);
        }
        return itemMember;
    }

    public void deleteItemMemberById(Long id){
        itemMemberRepository.delete(id);
    }

}
