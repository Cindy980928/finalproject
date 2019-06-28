package com.xiaoding.finalproject;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

import static cn.bmob.v3.b.From.e;

public class MyHorizontalAdapter extends RecyclerView.Adapter<MyHorizontalAdapter.ViewHolder>{
    private List<String> list;
    private View view;
    private String orderid;
    private Context context;
    private ImageView receiverImg;
    protected String[] receive_stuid=new String[100];
    MyHorizontalAdapter(){}
    MyHorizontalAdapter(String orderid,Context context){
        this.orderid=orderid;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view=View.inflate(parent.getContext(),R.layout.mypublish_listview_item_item,null);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        MyAsyncTask myAsyncTask=new MyAsyncTask(holder.tv,context);
        myAsyncTask.execute(orderid,""+position);
        receiverImg=(ImageView)view.findViewById(R.id.stu_receive);
        receiverImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("dgc","点击了子listview的item"+receive_stuid[position]);
                Intent intent = new Intent();
                intent.putExtra("receiverid",receive_stuid[position]);
                intent.putExtra("orderid",orderid);
                Log.v("dgc","receiverid"+receive_stuid[position]);
                intent.setClass(context,ReceiverInfo.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView tv;
        public ViewHolder(View itemView){
            super(itemView);
            tv=(ImageView) itemView.findViewById(R.id.stu_receive);

        }
    }





        class MyAsyncTask extends AsyncTask<String,String,Void> {
        private ImageView imv;
        private Context context;
        private String imgurl;
        private int position;
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
            position=Integer.parseInt(params[1]);
            Log.v("dgc",position+"");
            BmobQuery<OrderDetail> query = new BmobQuery<>();
            query.addWhereEqualTo("order_id", params[0]);
            Log.v("dgc","orderid:"+params[0]);
            query.findObjects(new FindListener<OrderDetail>() {
                @Override
                public void done(List<OrderDetail> object, BmobException e) {
                    if (e == null) {
                        receive_stuid[position] =object.get(position).getStuID_receiveorder();
                        Log.v("dgc",receive_stuid[position]);
                        BmobQuery<StudentMessage> query1 = new BmobQuery<>();
                        query1.addWhereEqualTo("StuID", receive_stuid[position]);
                        query1.findObjects(new FindListener<StudentMessage>() {
                            @Override
                            public void done(List<StudentMessage> object, BmobException e) {
                                if (e == null) {
                                    imgurl=object.get(0).getImageUrl();
                                    Log.v("dgc",imgurl);
                                    publishProgress(imgurl);
                                }
                                else{
                                    Log.v("dgc","127:"+e.toString());
                                }
                            }
                        });
                    }
                    else{
                        Log.v("dgc","133:"+e.toString());
                    }
                }
            });
            return null;
        }

    }

}