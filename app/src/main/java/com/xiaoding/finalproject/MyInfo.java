package com.xiaoding.finalproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class MyInfo extends AppCompatActivity {
    private TextView id;
    private TextView name;
    private EditText mobile;
    private EditText sex;
    private EditText sign;
    private Button resetbtn;
    private String sid;
    private String sname;
    private String smobile;
    private String ssex;
    private String ssign;
    private String newmmobile;
    private String objid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myinfo_activity);
        id=(TextView)findViewById(R.id.myinfo_account);
        name=(TextView)findViewById(R.id.myinfo_name);
        mobile=(EditText) findViewById(R.id.myinfo_mobile);
        sex=(EditText)findViewById(R.id.sex);
        sign=(EditText)findViewById(R.id.sign);
        resetbtn=(Button)findViewById(R.id.myinfo_reset);
        BmobQuery<StudentMessage> query1 = new BmobQuery<>();
        query1.addWhereEqualTo("StuID", User.UserId);
        query1.findObjects(new FindListener<StudentMessage>() {
            @Override
            public void done(List<StudentMessage> object, BmobException e) {
                if (e == null) {
                    Log.v("MyInfo","查询成功");
                    objid=object.get(0).getObjectId();
                    sname=object.get(0).getStuName();
                    sid=object.get(0).getStuID();
                    smobile=object.get(0).getMobile();
                    ssign=object.get(0).getSign();
                    ssex=object.get(0).getSex();
                    id.setText(sid);
                    name.setText(sname);
                    mobile.setText(smobile);
                    if(ssex!=null)
                        sex.setText(ssex);
                    if(ssign!=null)
                        sign.setText(ssign);
                }
                else{
                    Log.v("MyInfo","查询失败"+e.toString());
                }
            }
        });
        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newmmobile=mobile.getText().toString();
                ssex=sex.getText().toString();
                ssign=sign.getText().toString();
                StudentMessage stu = new StudentMessage();
                stu.setValue("mobile", newmmobile);
                stu.setValue("sex",ssex);
                stu.setValue("sign",ssign);
                stu.setValue("regist_state",1);
                stu.update(objid, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Log.v("MyInfo", "更新成功");
                            Toast.makeText(MyInfo.this, "修改成功", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Log.v("MyInfo", "更新成功");
                    }
                });
            }

        });
    }
}
