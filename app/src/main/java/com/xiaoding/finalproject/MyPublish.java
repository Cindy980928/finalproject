package com.xiaoding.finalproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class MyPublish extends AppCompatActivity {
    private ListView listView;
    private MypublishAdapter mypublishAdapter;
    private List<OrderList> infolist=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_publish);
        listView=(ListView)findViewById(R.id.mypublis_list_view);
        BmobQuery<OrderList> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereEqualTo("StuID_send", User.UserId);
        categoryBmobQuery.findObjects(new FindListener<OrderList>() {
            @Override
            public void done(List<OrderList> object, BmobException e) {
                if (e == null) {
                    infolist=object;
                    Log.v("MyPublish",""+object.size());
                    mypublishAdapter = new MypublishAdapter(MyPublish.this,R.layout.mypublish_listview_item,infolist);
                    listView.setAdapter(mypublishAdapter);
                } else {
                    Log.e("MyPublish", e.toString());
                }
            }
        });

    }



}
