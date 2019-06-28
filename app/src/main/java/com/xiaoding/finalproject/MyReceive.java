package com.xiaoding.finalproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MyReceive extends AppCompatActivity {
    private ListView listView;
    private List<OrderList> mylist=new ArrayList<>();
    private MyReceiveAdapter myReceiveAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myreceive_activity);
        listView=(ListView)findViewById(R.id.myreceive_list_view);
        BmobQuery<OrderList> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereEqualTo("distributed_StuID", User.UserId);
        categoryBmobQuery.findObjects(new FindListener<OrderList>() {
            @Override
            public void done(List<OrderList> object, BmobException e) {
                if (e == null) {
                    mylist=object;
                    Log.v("MyReceive",""+object.size());
                    myReceiveAdapter = new MyReceiveAdapter(MyReceive.this,R.layout.myreceive_listview_item,mylist);
                    listView.setAdapter(myReceiveAdapter);
                } else {
                    Log.e("MyReceive", e.toString());
                }
            }
        });
    }
}
