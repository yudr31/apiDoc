package com.spring.boot.apidoc.repository;

import com.spring.boot.apidoc.entity.Item;
import org.springframework.data.repository.CrudRepository;

/**
 * @author yuderen
 * @version 2018/3/1 14:54
 */
public interface ItemRepository extends CrudRepository<Item, Long> {

}
