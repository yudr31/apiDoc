package com.spring.boot.apidoc.service;

import com.spring.boot.apidoc.entity.DocHistory;
import com.spring.boot.apidoc.repository.DocHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuderen
 * @version 2018/3/8 16:04
 */
@Service
public class DocHistoryService {

    @Autowired
    private DocHistoryRepository docHistoryRepository;

    public List<DocHistory> getAllListByDocId(Long docId){
        return docHistoryRepository.findAllByDocId(docId);
    }

    public DocHistory saveDocHistory(DocHistory docHistory){
        return docHistoryRepository.save(docHistory);
    }

    public DocHistory getDocHistoryByHisId(Long hisId){
        return docHistoryRepository.findOne(hisId);
    }

}
