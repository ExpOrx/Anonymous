package cn.online.anonymous.activity;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;

import java.util.List;

import cn.online.anonymous.Config;
import cn.online.anonymous.R;
import cn.online.anonymous.id.MyContacts;
import cn.online.anonymous.net.Message;
import cn.online.anonymous.net.Timeline;
import cn.online.anonymous.net.UploadContacts;
import cn.online.anonymous.tools.MD5Tool;

/**
 * Created by 16611 on 2016/9/25.
 * 消息列表
 */
public class AcyTimeline extends ListActivity {
    private static String phone=null;
    private String phone_md5=null;
    private String token=null;
    private AcyTimelineMessageListAdapter adapter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline);
        adapter = new AcyTimelineMessageListAdapter(this);
        phone = getIntent().getStringExtra(Config.KEY_PHONE_NUM);
        token = getIntent().getStringExtra(Config.KEY_TOKE);
        phone_md5 = MD5Tool.md5(phone);
        final ProgressDialog pd = ProgressDialog.show(this,getResources().getString(R.string.connecting),getResources().getString(R.string.connecting_to_server));//进度条显示连接状态
        new UploadContacts(phone_md5, token, MyContacts.getContactsJSONString(this), new UploadContacts.SuccessCallBack() {
            @Override
            public void success() {
                pd.dismiss();
                loadMessage();
            }
        }, new UploadContacts.FailCallBack() {
            @Override
            public void fail(int errorCode) {
                pd.dismiss();
                if (errorCode==Config.RESULT_STATUS_OVERTIME){
                    startActivity(new Intent(AcyTimeline.this,AcyLogin.class));//token过期，实现跳转登陆页面
                    finish();
                }else{
                    loadMessage();
                }
            }
        });
    }
    final ProgressDialog pd = ProgressDialog.show(this,getResources().getString(R.string.connecting),getResources().getString(R.string.connecting_to_server));//进度条显示连接状态
    private void loadMessage(){
        System.out.print(">>>>>>>>LoadMessage<<<<<<<<<<<");
        new Timeline(MD5Tool.md5(phone),token,1, "20", new Timeline.SuccessCallBack() {
            @Override
            public void success(int page, int perpage, List<Message> timeline) {
                pd.dismiss();
                //setListAdapter(timeline);
                adapter.addAll(timeline);
            }
        }, new Timeline.FailCallBack() {
            @Override
            public void fail(int errorCode) {
                pd.dismiss();
            }
        });

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Message msg = adapter.getItem(position);
        Intent i = new Intent(this,AcyMessage.class);
        i.putExtra(Config.KEY_MSG,msg.getMsg());
        i.putExtra(Config.KEY_MSG_ID,msg.getMsgID());
        i.putExtra(Config.KEY_PHONE_MD5,msg.getPhone_md5());
        i.putExtra(Config.KEY_TOKE,token);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.show_publish_message:
                Intent i = new Intent(AcyTimeline.this,AcyLogin.class);
                i.putExtra(Config.KEY_PHONE_MD5,phone_md5);
                i.putExtra(Config.KEY_TOKE,token);
                startActivity(i) ;
                break;
            default:
                break;
        }
        return true;
    }
}
