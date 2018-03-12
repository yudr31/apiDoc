package com.spring.boot.apidoc.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author yuderen
 * @version 2018/3/8 15:38
 */
@Entity(name = "ecsys_doc_history")
@EntityListeners(AuditingEntityListener.class)
public class DocHistory extends BaseBean {

    @Id
    @GeneratedValue
    private Long hisId;
    private Long docId;
    private Long itemId;
    private Long parentId;
    private String docName;
    private Integer sort;
    @Lob
    private String editContent;
    @Lob
    private String showContent;

    public DocHistory() {

    }

    public DocHistory(ItemDoc itemDoc) {
        this.docId = itemDoc.getDocId();
        this.itemId = itemDoc.getItemId();
        this.parentId = itemDoc.getParentId();
        this.docName = itemDoc.getDocName();
        this.sort = itemDoc.getSort();
        this.editContent = itemDoc.getEditContent();
        this.showContent = itemDoc.getShowContent();
    }

    public ItemDoc getItemDoc(){
        ItemDoc itemDoc = new ItemDoc();
        itemDoc.setDocId(this.docId);
        itemDoc.setItemId(this.itemId);
        itemDoc.setParentId(this.parentId);
        itemDoc.setDocName(this.docName);
        itemDoc.setSort(this.sort);
        itemDoc.setEditContent(this.editContent);
        itemDoc.setShowContent(this.showContent);
        return itemDoc;
    }

    public Long getHisId() {
        return hisId;
    }

    public void setHisId(Long hisId) {
        this.hisId = hisId;
    }

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
