package com.xiaoding.finalproject;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class StudentMessage extends BmobObject {
    private String StuName;
    private String sex;
    private String StuID;
    private int regist_state;
    private String mobile;
    private String StuPwd;
    private BmobFile Image;
    private String imageUrl;
    private String sign;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BmobFile getImage() {
        return Image;
    }

    public void setImage(BmobFile image) {
        Image = image;
    }

    public String getStuPwd() {
        return StuPwd;
    }

    public void setStuPwd(String stuPwd) {
        StuPwd = stuPwd;
    }

    public String getStuName() {
        return StuName;
    }

    public void setStuName(String stuName) {
        StuName = stuName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStuID() {
        return StuID;
    }

    public void setStuID(String stuID) {
        StuID = stuID;
    }

    public int getRegist_state() {
        return regist_state;
    }

    public void setRegist_state(int regist_state) {
        this.regist_state = regist_state;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
