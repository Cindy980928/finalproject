package com.xiaoding.finalproject;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static cn.bmob.v3.Bmob.getApplicationContext;

public class MyAdapter extends ArrayAdapter<OrderList> {
    private int money;
    private String address;
    private String size;
    private String orderid;
    private int resourceId;
    private String sendPerson;
    private Context context;
    private String url;
    private ImageView img;
    public MyAdapter(Context context,int textViewResourceId, List<OrderList> list){
        super(context,textViewResourceId,list);
        resourceId=textViewResourceId;
        this.context=context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        OrderList orderList=getItem(position);
        View view;
        final ViewHolder viewHolder;
        if (convertView == null){
            view =LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.pk_address = (TextView)view.findViewById(R.id.pk_address);
            viewHolder.pk_size = (TextView)view.findViewById(R.id.pk_size);
            viewHolder.pk_money=(TextView)view.findViewById(R.id.pk_money);
            viewHolder.receiveimg=(ImageButton)view.findViewById(R.id.receiveimg);
            viewHolder.icon=(ImageView)view.findViewById(R.id.icon);
            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        money = orderList.getMoney();
        address = orderList.getAddress().toString();
        orderid = orderList.getOrder_id().toString();
//        Log.v("Helpothers","订单号依次是"+orderid);
        size =orderList.getPackage_size().toString();
        sendPerson=orderList.getStuID_send().toString();

        viewHolder.pk_address.setText("收货地址： "+address);
        viewHolder.pk_size.setText("包裹大小： "+size);
        viewHolder.pk_money.setText("酬金： ￥"+money);
        viewHolder.receiveimg.setImageResource(R.drawable.receive);

        MyAsyncTask imageTask=new MyAsyncTask(viewHolder.icon,context);
        imageTask.execute(sendPerson);////通过调用execute方法开始处理异步任务.相当于线程中的start方法.

        //给接单ImageButton设置监听事件

        viewHolder.receiveimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mitemListener.onRbtnClick(position);
                OrderList orderList1=getItem(position);
                orderid=orderList1.getOrder_id();
                mitemListener.getOrderID(orderid);
            }
        });
        return view;
    }



    /**
     * 图片按钮的监听接口
     */
    public interface onItemRbtnListener {
        void onRbtnClick(int i);
        void getOrderID(String oid);
    }

    private onItemRbtnListener mitemListener;

    public void setOnItemClickListener(onItemRbtnListener itemListener) {
        this.mitemListener = itemListener;
    }

    class ViewHolder{
        TextView pk_size;
        TextView pk_money;
        TextView pk_address;
        ImageButton receiveimg;
        ImageView icon;
    }

    class MyAsyncTask extends AsyncTask<String,String,Void>{
        private ImageView imv;
        private String imgurl;
        private String id;
        private Context context;
        public MyAsyncTask(ImageView imv,Context context){
            this.imv=imv;
            this.context=context;
        }
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Log.v("xiancheng",values[0]);
            Glide.with(context)
                    .load(values[0])
                    .into(imv);
        }

        @Override
        protected Void doInBackground(String... params) {
            id=params[0];
            BmobQuery<StudentMessage> query1 = new BmobQuery<>();
            query1.addWhereEqualTo("StuID", id);
            query1.findObjects(new FindListener<StudentMessage>() {
                @Override
                public void done(List<StudentMessage> object, BmobException e) {
                    if (e == null) {
                        imgurl =object.get(0).getImageUrl();
                        publishProgress(imgurl);
                    }
                    else{

                    }
                }
            });
            return null;
        }

    }
}
