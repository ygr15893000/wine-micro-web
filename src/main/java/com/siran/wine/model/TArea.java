package com.siran.wine.model;

import java.io.Serializable;

/**
 * Created by 唐正川 on 2017/6/27.
 */
public class TArea implements Serializable{

    private String provId;
    private String provName;
    private String areaId;
    private String areaName;

    public String getProvId() {
        return provId;
    }

    public void setProvId(String provId) {
        this.provId = provId;
    }

    public String getProvName() {
        return provName;
    }

    public void setProvName(String provName) {
        this.provName = provName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
