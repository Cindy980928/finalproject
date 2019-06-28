package com.xiaoding.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class ReceiverInfo extends AppCompatActivity {
    private TextView idtv;
    private TextView nametv;
    private TextView sextv;
    private TextView mobiletv;
    private TextView signtv;
    private ImageView head;
    private Button confirm;
    private String receiverid;
    private String orderid;
    private String id;
    private String name;
    private String sex;
    private String mobile;
    private String headingUrl;
    private String sign;
    private String objid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receiver_activity);
        idtv=(TextView)findViewById(R.id.receiver_id);
        nametv=(TextView)findViewById(R.id.receiver_name);
        sextv=(TextView)findViewById(R.id.receiver_sex);
        mobiletv=(TextView)findViewById(R.id.receiver_mobile);
        signtv=(TextView)findViewById(R.id.receiver_sign);
        head=(ImageView)findViewById(R.id.receiver_img);
        confirm=(Button)findViewById(R.id.confirm);
        Intent intent = getIntent();
        receiverid = intent.getStringExtra("receiverid");
        orderid=intent.getStringExtra("orderid");
        Log.v("ReceiverInfo",receiverid);
        BmobQuery<StudentMessage> query = new BmobQuery<>();
        query.addWhereEqualTo("StuID",receiverid);
        query.findObjects(new FindListener<StudentMessage>() {
            @Override
            public void done(List<StudentMessage> object, BmobException e) {
                if (e == null) {
                    Log.v("ReceiverInfo","查询成功");
                    id=object.get(0).getStuID();
                    name=object.get(0).getStuName();
                    sex=object.get(0).getSex();
                    mobile=object.get(0).getMobile();
                    sign=object.get(0).getSign();
                    headingUrl=object.get(0).getImageUrl();
                    Glide.with(ReceiverInfo.this)
                            .load(headingUrl)
                            .into(head);

                    idtv.setText(id);
                    nametv.setText(name);
                    mobiletv.setText(mobile);
                    if(sex!="")
                        sextv.setText(sex);
                    else
                        sextv.setText("不明雌雄");
                    if(sign!="")
                        signtv.setText(sign);
                    else
                        signtv.setText("这个人没有个性签名");
                }
                else{
                    Log.v("ReceiverInfo","出错了"+e.toString());
                }
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmobQuery<OrderList> query = new BmobQuery<>();
                query.addWhereEqualTo("order_id",orderid);
                query.findObjects(new FindListener<OrderList>() {
                      @Override
                      public void done(List<OrderList> object, BmobException e) {
                          if (e == null) {
                              objid=object.get(0).getObjectId();
                              OrderList order=new OrderList();
                              order.setValue("money",object.get(0).getMoney());//Bmob云的bug，老是更新我没改过的数据，这里如果不这样写会把money改为0
                              order.setValue("distributed_StuID",receiverid);
                              order.setValue("order_state",1);
                              order.update(objid, new UpdateListener() {
                                  @Override
                                  public void done(BmobException e) {
                                      if(e==null){
                                          Toast.makeText(ReceiverInfo.this, "已确认"+name+"为接单人", Toast.LENGTH_SHORT).show();
                                          Intent intent=new Intent(ReceiverInfo.this,MyPublish.class);
                                          startActivity(intent);
                                          Log.i("ReceiverInfo","更新成功");
                                      }else{
                                          Log.i("ReceiverInfo","更新失败："+e.getMessage()+","+e.getErrorCode());
                                      }
                                  }

                              });
                          } else {
                          }
                      }
                  });

            }
        });
    }
}
