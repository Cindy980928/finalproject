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

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class LoginActivity extends AppCompatActivity {
    private Button loginbtn;
    private TextView regist;
    private EditText loginid;
    private EditText loginpwd;
    private String id;
    private String pwd;
    private String md5pwd;
    private String mobile;
    private int state;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Bmob.initialize(this, "333f2a29619a018bd0f62d1b42de83c5");
        loginbtn=(Button)findViewById(R.id.login_btn);
        regist=(TextView)findViewById(R.id.regist_textbtn);
        loginid=(EditText)findViewById(R.id.loginStuid);
        loginpwd=(EditText)findViewById(R.id.loginPwd);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id=loginid.getText().toString();
                pwd=loginpwd.getText().toString();
                md5pwd=MD5.getMD5(pwd);
                Log.v("LoginActivity",""+id);
                if(id.equals("") || pwd.equals(""))
                {
                    Toast.makeText(LoginActivity.this, "输入不能为空", Toast.LENGTH_LONG).show();
                }
                else {
                    BmobQuery<StudentMessage> userquery1 = new BmobQuery<>();
                    userquery1.addWhereEqualTo("StuID", id);
                    BmobQuery<StudentMessage> userquery2 = new BmobQuery<>();
                    userquery2.addWhereEqualTo("StuPwd", md5pwd);
                    List<BmobQuery<StudentMessage>> andQuerys = new ArrayList<BmobQuery<StudentMessage>>();
                    andQuerys.add(userquery1);
                    andQuerys.add(userquery2);
                    BmobQuery<StudentMessage> query = new BmobQuery<StudentMessage>();
                    query.and(andQuerys);
                    query.findObjects(new FindListener<StudentMessage>() {
                        @Override
                        public void done(List<StudentMessage> object, BmobException e) {
                            if(e==null){
                                User.UserId=id;
                                User.UserMobile=object.get(0).getMobile().toString();
                                Log.v("LoginActivity","size:"+object.size());
                                if(object.size()>0) {
                                    Log.v("LoginActivity","登录状态"+object.get(0).getRegist_state());
                                    state=object.get(0).getRegist_state();//注册的状态，0为失败，1为成功，2为待审核
                                    if(state==1){
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                    else if(state==2)
                                        Toast.makeText(LoginActivity.this, "当前账号还在审核中", Toast.LENGTH_LONG).show();
                                    else
                                        Toast.makeText(LoginActivity.this, "当前账号注册失败", Toast.LENGTH_LONG).show();
                                }
                                else
                                    Toast.makeText(LoginActivity.this, "账号密码不匹配", Toast.LENGTH_LONG).show();
                            }else{
                                Log.e("LoginActivity", "e:"+e.toString());
                                Log.v("LoginActivity","message:"+e.getMessage());
                                Toast.makeText(LoginActivity.this, "失败", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegistAcitivity.class);
                startActivity(intent);
            }
        });

    }
}
