package com.spring.boot.apidoc.repository;

import com.spring.boot.apidoc.entity.ItemDoc;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author yuderen
 * @version 2018/3/1 14:55
 */
public interface ItemDocRepository extends CrudRepository<ItemDoc, Long> {

    List<ItemDoc> findAllByItemId(Long itemId);

}
