package com.spring.boot.apidoc.entity;

import java.util.List;

/**
 * @author yuderen
 * @version 2018/3/2 15:52
 */
public class ItemDocMenu extends ItemDoc {

    private Integer state;
    private List<ItemDocMenu> children;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public List<ItemDocMenu> getChildren() {
        return children;
    }

    public void setChildren(List<ItemDocMenu> children) {
        this.children = children;
    }



}
