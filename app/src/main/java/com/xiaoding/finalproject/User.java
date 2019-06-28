package com.xiaoding.finalproject;

public class User {
    public static String UserId="";
    public static String UserMobile="";
    public static boolean isLogin(){
        if(UserId=="")
            return false;
        else
            return true;
    }
}
