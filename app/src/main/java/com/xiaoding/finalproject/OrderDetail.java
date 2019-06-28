package com.xiaoding.finalproject;

import cn.bmob.v3.BmobObject;

public class OrderDetail extends BmobObject{
    private String StuID_receiveorder;
    private String order_id;

    public String getStuID_receiveorder() {
        return StuID_receiveorder;
    }

    public void setStuID_receiveorder(String stuID_receiveorder) {
        StuID_receiveorder = stuID_receiveorder;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
