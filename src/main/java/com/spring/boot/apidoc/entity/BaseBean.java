package com.spring.boot.apidoc.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author yuderen
 * @version 2018/3/8 14:28
 */
@MappedSuperclass
public abstract class BaseBean {

    private Integer status = 1; // 0-已删除, 其他-根据具体业务定义
    @CreatedBy
    @Column(nullable = false, updatable = false)
    private Long createUser;
    @LastModifiedBy
    private Long updateUser;
    @LastModifiedDate
    private Long updateTime;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Long createTime;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

}
