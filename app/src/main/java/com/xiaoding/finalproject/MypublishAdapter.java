package com.xiaoding.finalproject;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoding.finalproject.OrderList;
import com.xiaoding.finalproject.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import static cn.bmob.v3.Bmob.getApplicationContext;

public class MypublishAdapter extends ArrayAdapter<OrderList> {
    private int money;
    private String orderid;
    private String pksize;
    private String name;
    private String address;
    private String publishtime;
    private String receiverName;
    private int resourceId;
    private String pkcode;
    private ViewHolder viewHolder;
    Context mcontext;
    private List<OrderList> list;
    public MypublishAdapter(Context context,int textViewResourceId, List<OrderList> list){
        super(context,textViewResourceId,list);
        this.mcontext=context;
        resourceId=textViewResourceId;
        this.list=list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OrderList orderList=getItem(position);
        View view =LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

        if (convertView == null){
//            view =LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.mp_orderid = (TextView) view.findViewById(R.id.mp_orderid);
            viewHolder.mp_money = (TextView)view.findViewById(R.id.mp_money);
            viewHolder.mp_pksize=(TextView)view.findViewById(R.id.mp_pksize);
            viewHolder.mp_name=(TextView)view.findViewById(R.id.mp_nikename);
            viewHolder.mp_time=(TextView)view.findViewById(R.id.mp_time);
            viewHolder.mp_address=(TextView)view.findViewById(R.id.mp_address);
            viewHolder.mp_receiver=(TextView)view.findViewById(R.id.mp_receiver);
            viewHolder.mp_pkcode=(TextView)view.findViewById(R.id.mp_pkcode);
            viewHolder.recyclerView=(RecyclerView)view.findViewById(R.id.list_two);
            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        money = orderList.getMoney();
        address = orderList.getAddress();
        pksize = orderList.getPackage_size();
        orderid=orderList.getOrder_id();
        name=orderList.getNickName();
        pkcode=orderList.getPackage_id();
        receiverName=orderList.getDistributed_StuID();
        publishtime=orderList.getCreatedAt();
        viewHolder.mp_orderid.setText("订单号： "+orderid);
        viewHolder.mp_money.setText("酬金： ￥"+money);
        viewHolder.mp_pksize.setText("包裹大小： "+pksize);
        viewHolder.mp_pkcode.setText("取件码： "+pkcode);
        viewHolder.mp_name.setText("收货人昵称： "+name);
        viewHolder.mp_address.setText("收货地址： "+address);
        viewHolder.mp_time.setText("发布时间： "+publishtime);
        if(receiverName!=null)
            viewHolder.mp_receiver.setText("接单人学号： "+receiverName);
        LinearLayoutManager lm=new LinearLayoutManager(mcontext);
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        viewHolder.recyclerView.setLayoutManager(lm);
        viewHolder.recyclerView.setAdapter(new MyHorizontalAdapter(orderid,mcontext));

        return view;
    }


    class ViewHolder{
        TextView mp_orderid;
        TextView mp_money;
        TextView mp_pksize;
        TextView mp_name;
        TextView mp_pkcode;
        TextView mp_address;
        TextView mp_time;
        TextView mp_receiver;
//        MyListView myListView;
        RecyclerView recyclerView;

    }

}
