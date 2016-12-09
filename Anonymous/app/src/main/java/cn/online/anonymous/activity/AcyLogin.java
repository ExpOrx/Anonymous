package cn.online.anonymous.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cn.online.anonymous.Config;
import cn.online.anonymous.R;
import cn.online.anonymous.net.GetCode;
import cn.online.anonymous.net.LoginNet;
import cn.online.anonymous.tools.MD5Tool;

/**
 * Created by 16611 on 2016/9/23.
 * 登陆
 */
public class AcyLogin extends Activity {
    private EditText edPhone=null;
    private EditText edCode=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        edPhone= (EditText) findViewById(R.id.editPhone);
        edCode=(EditText)findViewById(R.id.editPhoneCode);
        findViewById(R.id.btn_Login).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /*验证码不能为空*/
                if (TextUtils.isEmpty(edCode.getText())){

                    Toast.makeText(AcyLogin.this,R.string.phone_no_empty,Toast.LENGTH_LONG).show();
                    return;

                }

                //进度提示
                final ProgressDialog progressDialog = ProgressDialog.show(AcyLogin.this,getResources().getString(R.string.connecting),getResources().getString(R.string.connecting_to_server));
                new GetCode(edPhone.getText().toString(), new GetCode.SuccessCallBack() {
                    @Override
                    public void onSuccess() {
                        progressDialog.dismiss();
                        Toast.makeText(AcyLogin.this,R.string.success_get_code,Toast.LENGTH_LONG).show();
                    }
                }, new GetCode.FailCallBack() {
                    @Override
                    public void onFail() {
                        progressDialog.dismiss();
                        Toast.makeText(AcyLogin.this,R.string.fail_get_code,Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        findViewById(R.id.btn_Login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*电话号码不能为空*/
                if (TextUtils.isEmpty(edPhone.getText())){

                    Toast.makeText(AcyLogin.this,R.string.phone_no_empty,Toast.LENGTH_LONG).show();
                    return;

                }
                /*验证码不能为空*/
                if (TextUtils.isEmpty(edCode.getText())){

                    Toast.makeText(AcyLogin.this,R.string.phone_no_empty,Toast.LENGTH_LONG).show();
                    return;

                }

                new LoginNet(MD5Tool.md5(edPhone.getText().toString()),edCode.getText().toString(), new LoginNet.SuccessCallBack() {
                    @Override
                    public void success(String token) {
                        //Toast.makeText(AcyLogin.this,R.string.success_to_login,Toast.LENGTH_LONG).show();
                        Config.achedToken(AcyLogin.this,token);//缓存token
                        Config.achedPhoneum(AcyLogin.this,edPhone.getText().toString());
                        Intent i = new Intent(AcyLogin.this,AcyTimeline.class);//从登陆页面跳转到消息界面
                        i.putExtra(Config.KEY_TOKE,token);
                        i.putExtra(Config.KEY_PHONE_NUM,edPhone.getText().toString());
                        startActivity(i);
                        finish();//终结当前的Activity
                    }
                }, new LoginNet.FailCallBack() {
                    @Override
                    public void fail() {
                        Toast.makeText(AcyLogin.this,R.string.fail_to_login,Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


    }

}
