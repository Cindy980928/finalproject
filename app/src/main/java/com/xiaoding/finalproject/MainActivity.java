package com.xiaoding.finalproject;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragAndDropPermissions;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity{
    private Button helpme;
    private Button helpothers;
    private TextView name;
    private TextView id;
    private ImageView headimg;
    String sname;
    String sid;
    private String path;
    private String url;
    private DrawerLayout mDrawerLayout;
    private StudentMessage stu;
    private String objid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        NavigationView navView=(NavigationView)findViewById(R.id.nav_view);//获取滑动菜单实例
        ActionBar actionBar=getSupportActionBar();//获取ActionBar实例
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        }
        setnavheadertext();//自定义函数，设置滑动窗口内显示的信息
        //给菜单项项添加监听事件
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //点击菜单项的事件处理
                switch (item.getItemId()){
                    case R.id.myorder:
                        Log.v("MainActivity","点击了我的订单");
                        Intent intent2=new Intent(MainActivity.this,MyPublish.class);
                        startActivity(intent2);
                        break;
                    case R.id.myinfo:
                        Intent intent = new Intent(MainActivity.this,MyInfo.class);
                        startActivity(intent);
                        break;
                    case R.id.myreceive:
                        Intent intent3=new Intent(MainActivity.this,MyReceive.class);
                        startActivity(intent3);
                        break;
                    case R.id.quit:
                        User.UserMobile="";
                        User.UserId="";
                        Intent intent4=new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent4);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        NavigationView navigationView = findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);
        headimg=(ImageView)view.findViewById(R.id.icon_img);
        BmobQuery<StudentMessage> query1 = new BmobQuery<>();
        query1.addWhereEqualTo("StuID", User.UserId);
        query1.findObjects(new FindListener<StudentMessage>() {
            @Override
            public void done(List<StudentMessage> object, BmobException e) {
                if (e == null) {
                    url=object.get(0).getImageUrl();
                    setIcon();
                }
                else{

                }
            }
        });

        //点击头像可以换头像
        headimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
                intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
                intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
                startActivityForResult(intent, 1);
            }
        });

        //第一：默认初始化
        Bmob.initialize(this, "333f2a29619a018bd0f62d1b42de83c5");
        helpme = (Button) findViewById(R.id.helpme);
        helpothers = (Button) findViewById(R.id.helpothers);
        helpothers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Helpothers.class);
                startActivity(intent);
            }
        });
        helpme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MainActivity.this,Helpme.class);
                startActivity(intent1);
            }
        });
    }
//调用Glide核心类：下面一句话就已经可以加载图片了
    void setIcon(){
        Glide.with(this).load(url).into(headimg);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            path = getImagePath(uri, null);
            ContentResolver cr = this.getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));

                /* 将Bitmap设定到ImageView */
                headimg.setImageBitmap(bitmap);
                BmobQuery<StudentMessage> query = new BmobQuery<>();
                query.addWhereEqualTo("StuID", User.UserId);
                query.findObjects(new FindListener<StudentMessage>() {
                    @Override
                    public void done(List<StudentMessage> object, BmobException e) {
                        if (e == null) {
                            objid=object.get(0).getObjectId();
                            final BmobFile file=new BmobFile(new File(path));
                            file.upload(new UploadFileListener(){
                                @Override
                                public void done(BmobException e) {
                                    if(e==null){
                                        StudentMessage stu=new StudentMessage();
                                        stu.setValue("image",file);
                                        stu.setValue("imageUrl",file.getFileUrl());
                                        stu.setRegist_state(1);//不知道为什么，执行update之后注册状态被改为0了，死活找不出原因
                                        stu.update(objid,new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if(e==null)
                                                    Log.v("Image","上传成功");
                                                else
                                                    Log.v("Image","上传失败");
                                            }
                                        });
                                    }else{
                                        Log.v("Image","161行");
                                    }
                                }
                            });
                        } else {
                            Log.e("BMOB", e.toString());
                        }
                    }
                });

            } catch (FileNotFoundException e) {
                Log.e("qwe", e.getMessage(),e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private String getImagePath(Uri uri, String seletion) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, seletion, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            }
            cursor.close();

        }
        return path;
    }


    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }

    public void setnavheadertext(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);
        id=(TextView)view.findViewById(R.id.navheader_id);
        name=(TextView)view.findViewById(R.id.navheader_name);
        BmobQuery<StudentMessage> query1 = new BmobQuery<>();
        query1.addWhereEqualTo("StuID", User.UserId);
        query1.findObjects(new FindListener<StudentMessage>() {
            @Override
            public void done(List<StudentMessage> object, BmobException e) {
                if (e == null) {
                    sname=object.get(0).getStuName().toString();
                    sid=object.get(0).getStuID().toString();

                    Log.v("MainActivity","name="+sname);
                    name.setText(sname);
                    id.setText(sid);
                } else {
                    Log.e("BMOB", e.toString());
                }
            }
        });

    }

}
