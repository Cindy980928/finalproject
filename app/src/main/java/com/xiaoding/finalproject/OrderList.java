package com.xiaoding.finalproject;

import android.util.Log;

import java.text.SimpleDateFormat;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

public class OrderList extends BmobObject{
    private String package_size;
    private int money;
    private String address;
    private String package_id;
    private String StuID_send;
    private String order_id;
    private int order_state;
    private String NickName;
    private String distributed_StuID;

    public String getDistributed_StuID() {
        return distributed_StuID;
    }

    public void setDistributed_StuID(String distributed_StuID) {
        this.distributed_StuID = distributed_StuID;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public int getOrder_state() {
        return order_state;
    }

    public void setOrder_state(int order_state) {
        this.order_state = order_state;
    }

    public String getPackage_id() {
        return package_id;
    }

    public void setPackage_id(String package_id) {
        this.package_id = package_id;
    }

    public String getStuID_send() {
        return StuID_send;
    }

    public void setStuID_send(String stuID_send) {
        StuID_send = stuID_send;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getPackage_size() {
        return package_size;
    }

    public void setPackage_size(String package_size) {
        this.package_size = package_size;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
