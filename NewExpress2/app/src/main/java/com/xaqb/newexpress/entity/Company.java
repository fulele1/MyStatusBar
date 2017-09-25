package com.xaqb.newexpress.entity;

/**
 * Created by lenovo on 2017/3/15.
 */

public class Company {
    private String comID;
    private String comName;
    private String comTotalName;

    public String getComID() {
        return comID;
    }

    public void setComID(String comID) {
        this.comID = comID;
    }

    private String comAddress;

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getComTotalName() {
        return comTotalName;
    }

    public void setComTotalName(String comTotalName) {
        this.comTotalName = comTotalName;
    }

    public String getComAddress() {
        return comAddress;
    }

    public void setComAddress(String comAddress) {
        this.comAddress = comAddress;
    }
}
