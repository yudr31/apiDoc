package com.spring.boot.apidoc.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author yuderen
 * @version 2018/3/1 14:39
 */
@Entity(name = "ecsys_item_doc")
@EntityListeners(AuditingEntityListener.class)
public class ItemDoc extends BaseBean{

    @Id
    @GeneratedValue
    private Long docId;
    private Long itemId;
    private Long parentId;
    private String docName;
    private Integer sort;
    @Lob
    private String editContent;
    @Lob
    private String showContent;


    public Long getDocId() {
        return docId;
    }

    public void setDocId(Long docId) {
        this.docId = docId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getEditContent() {
        return editContent;
    }

    public void setEditContent(String editContent) {
        this.editContent = editContent;
    }

    public String getShowContent() {
        return showContent;
    }

    public void setShowContent(String showContent) {
        this.showContent = showContent;
    }

}
