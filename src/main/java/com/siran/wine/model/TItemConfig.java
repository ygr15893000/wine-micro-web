package com.siran.wine.model;

import java.io.Serializable;

/**
 * Created by 唐正川 on 2017/6/14.
 * 商品类别配置表
 */
public class TItemConfig implements Serializable {

    private int id ;
    private String name;//商品类别
    private byte status;//1:启用 2:禁用

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
}
