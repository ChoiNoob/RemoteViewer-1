package com.damintsev.client.model;

import com.sencha.gxt.data.shared.TreeStore;

import java.util.List;

/**
 * User: Damintsev Andrey
 * Date: 18.07.13
 * Time: 22:10
 */
public class MenuItem implements TreeStore.TreeNode {

    private String name;
    private List<MenuItem> childrens;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getChildren() {
        return childrens;
    }

    public Object getData() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
