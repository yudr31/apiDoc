package com.spring.boot.apidoc.repository;

import com.spring.boot.apidoc.entity.ItemMember;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author yuderen
 * @version 2018/3/12 8:50
 */
public interface ItemMemberRepository extends CrudRepository<ItemMember, Long> {

    List<ItemMember> findAllByItemId(Long itemId);

    List<ItemMember> findAllByItemIdAndUserName(Long id, String userName);

}
