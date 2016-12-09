package cn.online.anonymous.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cn.online.anonymous.Config;
import cn.online.anonymous.R;
import cn.online.anonymous.net.Publish;

/**
 * Created by 16611 on 2016/9/25.
 */
public class AcyPubMessage extends Activity {
    private EditText editText=null;
    private String  phone_md5 =null;
    private String  token =null;
    private static final int NEED_FRESH=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pubmessage);
        editText = (EditText)findViewById(R.id.etMessageContent);
        phone_md5=getIntent().getStringExtra(Config.KEY_PHONE_MD5);
        token = getIntent().getStringExtra(Config.KEY_TOKE);
        findViewById(R.id.sendmessage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(editText.getText())){
                    Toast.makeText(AcyPubMessage.this,R.string.message_is_not_empty,Toast.LENGTH_SHORT).show();
                }else {
                    new Publish(phone_md5,token,editText.getText().toString(), new Publish.SuccessCallBack() {
                        @Override
                        public void success() {
                            setResult(NEED_FRESH);
                            finish();
                        }
                    }, new Publish.FailCallBack() {
                        @Override
                        public void fail(int errorCode) {
                            if (errorCode==Config.RESULT_STATUS_OVERTIME){
                                startActivity(new Intent(AcyPubMessage.this,AcyLogin.class));//token过期，实现跳转登陆页面
                                finish();
                            }else {
                                Toast.makeText(AcyPubMessage.this,R.string.fail_set_message,Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

    }
}
