package com.spring.boot.apidoc.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author yuderen
 * @version 2018/3/12 8:44
 */
@Entity(name = "ecsys_item_member")
@EntityListeners(AuditingEntityListener.class)
public class ItemMember extends BaseBean {

    @Id
    @GeneratedValue
    private Long id;
    private Long itemId;
    private String userName;
    private Integer auth = 1;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAuth() {
        return auth;
    }

    public void setAuth(Integer auth) {
        this.auth = auth;
    }
}
