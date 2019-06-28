package com.xiaoding.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Helpme extends AppCompatActivity{
    private EditText pcode;
    private EditText psize;
    private EditText paddress;
    private EditText pmoney;
    private Button sendbtn;
    private EditText pnickname;
    private String code;
    private String size;
    private String address;
    private int money;
    private OrderList order;
    private String nickname;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helpme_activity);
        pcode=(EditText)findViewById(R.id.packagecode);
        psize=(EditText)findViewById(R.id.packagesize);
        paddress=(EditText)findViewById(R.id.myaddress);
        pmoney=(EditText)findViewById(R.id.mongeygive);
        sendbtn=(Button)findViewById(R.id.sendbtn);
        pnickname=(EditText)findViewById(R.id.mynickname);

        order=new OrderList();
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOrderId();
                //获取注册输入信息
                code=pcode.getText().toString();
                size=psize.getText().toString();
                address=paddress.getText().toString();
                nickname=pnickname.getText().toString();
                money=Integer.parseInt(pmoney.getText().toString());
                if(code.equals("")||size.equals("")||address.equals("")||nickname.equals("")||pmoney.getText().equals(""))
                    Toast.makeText(Helpme.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
                else{
                    order.setPackage_size(size);
                    order.setMoney(money);
                    order.setAddress(address);
                    order.setPackage_id(code);
                    order.setOrder_id(getOrderId());
                    order.setStuID_send(User.UserId);
                    order.setNickName(nickname);
                    order.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if (e == null) {
                                Toast.makeText(Helpme.this, "发布成功", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(Helpme.this,MainActivity.class);
                                startActivity(intent);
                            } else {
                                Log.v("Helpme", "&&&&" + e.toString());
                            }
                        }
                    });
                }

            }
        });
    }
    private String getOrderId(){
        //订单号设置为日期+时间+学号后4位
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate =  new Date(System.currentTimeMillis());
        String datestr = formatter.format(curDate);
        String id=User.UserId;
        int idlength=id.length();
        String str=id.substring(idlength-3,idlength);
        String orderid=datestr+str;
        Log.v("Helpme","***"+orderid);
        return orderid;
    }
}
