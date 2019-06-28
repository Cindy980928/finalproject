package com.xiaoding.finalproject;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

public class MyReceiveAdapter extends ArrayAdapter<OrderList> {
    private int money;
    private String orderid;
    private String pksize;
    private String sendperson;
    private String name;
    private String mobile;
    private String address;
    private String publishtime;
    private int resourceId;
    private String pkcode;
    private ViewHolder viewHolder;
    Context mcontext;
    private List<OrderList> list;
    public MyReceiveAdapter(Context context,int textViewResourceId, List<OrderList> list){
        super(context,textViewResourceId,list);
        mcontext=context;
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
            viewHolder.mr_orderid = (TextView) view.findViewById(R.id.mr_orderid);
            viewHolder.mr_money = (TextView)view.findViewById(R.id.mr_money);
            viewHolder.mr_pksize=(TextView)view.findViewById(R.id.mr_pksize);
            viewHolder.mr_name=(TextView)view.findViewById(R.id.mr_nikename);
            viewHolder.mr_time=(TextView)view.findViewById(R.id.mr_time);
            viewHolder.mr_address=(TextView)view.findViewById(R.id.mr_address);
            viewHolder.mr_pkcode=(TextView)view.findViewById(R.id.mr_pkcode);
            viewHolder.mr_mobile=(TextView)view.findViewById(R.id.mr_mobile);
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
        sendperson=orderList.getStuID_send();
        publishtime=orderList.getCreatedAt();
        viewHolder.mr_orderid.setText("订单号： "+orderid);
        viewHolder.mr_money.setText("酬金： ￥"+money);
        viewHolder.mr_pksize.setText("包裹大小： "+pksize);
        viewHolder.mr_pkcode.setText("取件码： "+pkcode);
        viewHolder.mr_name.setText("收货人昵称： "+name);
        viewHolder.mr_address.setText("收货地址： "+address);
        viewHolder.mr_time.setText("发布时间： "+publishtime);
        MyAsyncTask imageTask=new MyAsyncTask(viewHolder.mr_mobile,mcontext);
        imageTask.execute(sendperson);

        return view;
    }


    class ViewHolder{
        TextView mr_orderid;
        TextView mr_money;
        TextView mr_pksize;
        TextView mr_name;
        TextView mr_pkcode;
        TextView mr_address;
        TextView mr_time;
        TextView mr_mobile;

    }

    class MyAsyncTask extends AsyncTask<String,String,Void> {
        private TextView tv;
        private String sendpersonid;
        private Context context;
        private String phone;
        public MyAsyncTask(TextView tv,Context context){
            this.tv=tv;
            this.context=context;
        }
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Log.v("xiancheng",values[0]);
            tv.setText("收货人电话： "+values[0]);
        }

        @Override
        protected Void doInBackground(String... params) {
            sendpersonid=params[0];
            BmobQuery<StudentMessage> query1 = new BmobQuery<>();
            query1.addWhereEqualTo("StuID", sendpersonid);
            query1.findObjects(new FindListener<StudentMessage>() {
                @Override
                public void done(List<StudentMessage> object, BmobException e) {
                    if (e == null) {
                        phone =object.get(0).getMobile();
                        publishProgress(phone);
                    }
                    else{

                    }
                }
            });
            return null;
        }

    }

}
