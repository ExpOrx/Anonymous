package cn.online.anonymous;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import cn.online.anonymous.activity.AcyLogin;
import cn.online.anonymous.activity.AcyTimeline;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*查看本地有没有Token，如果有，进入消息目录，如果没有，进入登陆页面*/
        String token=Config.getCachedToken(this);
        String phone_num=Config.getCachedPhoneum(this);
        if (token!=null&&phone_num!=null){
            Intent intent = new Intent(this,AcyTimeline.class);
            intent.putExtra(Config.KEY_TOKE,token);//传入token数据
            intent.putExtra(Config.KEY_PHONE_NUM,phone_num);//传入数据
            startActivity(intent);
        }else{
            startActivity(new Intent(this, AcyLogin.class));
        }
    }
}
