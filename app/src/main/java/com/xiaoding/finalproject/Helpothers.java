package com.xiaoding.finalproject;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class Helpothers extends AppCompatActivity{
    private List<OrderList> list=new ArrayList<>();
    private ListView listView;
    MyAdapter myAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helpothers_activity);
        View view = LayoutInflater.from(this).inflate(R.layout.orderlist_item,null);
        listView=(ListView)findViewById(R.id.list_view);
        queryData();
    }

    public void queryData(){
        BmobQuery<OrderList> bmobQuery = new BmobQuery<OrderList>();
        bmobQuery.order("-createdAt");
        bmobQuery.addWhereEqualTo("order_state",0);
        bmobQuery.findObjects(new FindListener<OrderList>() {
            @Override
            public void done(List<OrderList> object1, BmobException e) {
                if(e==null){
                    list=object1;
                    Log.v("Helpothers",""+object1.size());
                    myAdapter = new MyAdapter(Helpothers.this,R.layout.orderlist_item,list);
                    listView.setAdapter(myAdapter);
                    receiveOrder();

                }else{
                    Log.v("Helpothers","失败："+e.getMessage()+","+e.getErrorCode());
                }

            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }

    //接单
    public void receiveOrder() {
        myAdapter.setOnItemClickListener(new MyAdapter.onItemRbtnListener() {
            @Override
            public void onRbtnClick(int i) {
                Log.v("Helpothers", "点击了接单按钮" + i);
            }

            @Override
            public void getOrderID(String oid) {
                Log.v("Helpothers", "订单号：" + oid);
                OrderDetail detail = new OrderDetail();
                detail.setOrder_id(oid);
                detail.setStuID_receiveorder(User.UserId);
                detail.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if (e == null) {
                            Toast.makeText(Helpothers.this, "已接单，请等待发布方确认", Toast.LENGTH_LONG).show();
                        } else {
                            Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                        }
                    }
                });
            }
        });
    }


}
