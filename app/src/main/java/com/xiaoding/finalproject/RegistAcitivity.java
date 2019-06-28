package com.xiaoding.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegistAcitivity extends AppCompatActivity {
    private TextView registname;
    private TextView registid;
    private TextView registmobile;
    private TextView registpwd;
    private Button registbtn;
    private String input_pwd="";
    private String md5_pwd="";
    private String input_name="";
    private String input_id="";
    private String input_mobile="";
    private StudentMessage student;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist_activity);
        Bmob.initialize(this, "333f2a29619a018bd0f62d1b42de83c5");
        registid=(TextView)findViewById(R.id.rregist_id);
        registname=(TextView)findViewById(R.id.rregist_name);
        registmobile=(TextView)findViewById(R.id.rregist_mobile);
        registbtn=(Button)findViewById(R.id.registcommitbtn);
        registpwd=(EditText)findViewById(R.id.rregist_pwd);
        Log.v("RegistActivity","**************");

        registbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取注册输入信息
                input_id=registid.getText().toString();
                input_pwd=registpwd.getText().toString();
                input_mobile=registmobile.getText().toString();
                input_name=registname.getText().toString();
                md5_pwd=MD5.getMD5(input_pwd);
                Log.v("RegistActivity", " input_id" + input_id+"input_mobilie"+input_mobile+"input_name"+input_name);

                String url="http://bmob-cdn-23175.b0.upaiyun.com/2018/12/26/e1ce2bca16fd4822b6b74ae18c9e2423.jpg";
                student = new StudentMessage();
                student.setStuName(input_name);
                student.setMobile(input_mobile);
                student.setStuID(input_id);
                student.setStuPwd(md5_pwd);
                student.setImageUrl(url);
                student.setRegist_state(2);//注册状态，2表示审核中
                student.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        Log.v("RegistActivity", "&&&&&jinlaile");
                        if (e == null) {
                            Toast.makeText(RegistAcitivity.this, "申请注册提交成功，请等待管理员审核", Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(RegistAcitivity.this,LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Log.v("RegistActivity", "&&&&" + e.toString());
                        }
                    }
                });

            }
        });
    }
}
