package com.spring.boot.apidoc.repository;

import com.spring.boot.apidoc.entity.DocHistory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author yuderen
 * @version 2018/3/8 16:03
 */
public interface DocHistoryRepository extends CrudRepository<DocHistory, Long> {

    List<DocHistory> findAllByDocId(Long docId);

}
