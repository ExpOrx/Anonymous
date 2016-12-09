package cn.online.anonymous.activity;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.online.anonymous.Config;
import cn.online.anonymous.R;
import cn.online.anonymous.net.Comments;
import cn.online.anonymous.net.GetComment;
import cn.online.anonymous.net.PubComment;
import cn.online.anonymous.tools.MD5Tool;

/**
 * Created by 16611 on 2016/9/25.
 * 消息展示
 */
public class AcyMessage extends ListActivity {

    private String msg=null;
    private String msgid=null;
    private String phone_md5=null;
    private String token = null;
    private TextView tvMessage = null;
    private TextView etComment = null;
    private AcyMessageCommentsListAdapter adapter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);

        adapter = new AcyMessageCommentsListAdapter(this);
        setListAdapter(adapter);

        tvMessage = (TextView) findViewById(R.id.textMessage);
        etComment = (TextView) findViewById(R.id.addComment);

        Intent data = getIntent();

        msg=data.getStringExtra(Config.KEY_MSG);
        msgid=data.getStringExtra(Config.KEY_MSG_ID);
        phone_md5 = data.getStringExtra(Config.KEY_PHONE_MD5);
        token=data.getStringExtra(Config.KEY_TOKE);

        tvMessage.setText(msg);

        getComments();

        findViewById(R.id.btn_addComment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etComment.getText())){
                    Toast.makeText(AcyMessage.this,R.string.content_is_not_empty,Toast.LENGTH_LONG);
                    return;
                }
                final ProgressDialog pd = ProgressDialog.show(AcyMessage.this,getResources().getString(R.string.connecting),getResources().getString(R.string.connecting_to_server));//进度条显示连接状态

                new PubComment(MD5Tool.md5(Config.getCachedPhoneum(AcyMessage.this)),token,etComment.getText().toString(), msgid, new PubComment.SuccessCallBack() {
                    @Override
                    public void success() {
                        pd.dismiss();
                        etComment.setText("");
                        getComments();//重新加载列表
                    }
                }, new PubComment.FailCallBack() {
                    @Override
                    public void fail(int errorCode) {
                        pd.dismiss();
                        if (errorCode==Config.RESULT_STATUS_OVERTIME){
                            startActivity(new Intent(AcyMessage.this,AcyLogin.class));
                            finish();
                        }else {
                            Toast.makeText(AcyMessage.this,R.string.fail_to_publish_comment,Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    private void getComments() {
        final ProgressDialog pd = ProgressDialog.show(this,getResources().getString(R.string.connecting),getResources().getString(R.string.connecting_to_server));//进度条显示连接状态
        new GetComment(phone_md5, token, 1, 20, msgid, new GetComment.SuccessCallBack() {
            @Override
            public void success(String msgid, int page, int perpage, List<Comments> comments) {
               pd.dismiss();
               adapter.clear();//先清空，后刷新加载
               adapter.addAll(comments);
            }
        }, new GetComment.FailCallBack() {
            @Override
            public void fail(int errorCode) {
                if (errorCode== Config.RESULT_STATUS_OVERTIME){
                    startActivity(new Intent(AcyMessage.this,AcyLogin.class));//token过期，实现跳转登陆页面
                    finish();
                }else{
                    Toast.makeText(AcyMessage.this,R.string.please_again_login,Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
