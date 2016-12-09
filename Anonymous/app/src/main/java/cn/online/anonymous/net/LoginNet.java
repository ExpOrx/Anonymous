package cn.online.anonymous.net;

import org.json.JSONException;
import org.json.JSONObject;

import cn.online.anonymous.Config;

/**
 * Created by 16611 on 2016/9/29.
 */
public class LoginNet {
    public LoginNet(String phone_md5, String code, final SuccessCallBack successCallBack, final FailCallBack failCallBack){
        new NetConnection(Config.SERVER_URL,HttpMethod.POST, new NetConnection.SuccessCallBack() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    switch (jsonObject.getInt(Config.KEY_STATUS)){
                        case Config.RESULT_STATUS_SUCCESS:
                            if(successCallBack!=null){
                                successCallBack.success(jsonObject.getString(Config.KEY_TOKE));
                            }
                            break;
                        default:
                            if(failCallBack!=null){
                                failCallBack.fail();
                            }
                            break;
                    }

                } catch (JSONException e) {
                    //有JSON异常也是失败
                    if(failCallBack!=null){
                        failCallBack.fail();
                    }
                    e.printStackTrace();
                }
            }
        }, new NetConnection.FailCallBack() {
            @Override
            public void onFail() {
               if(failCallBack!=null){
                   failCallBack.fail();
               }
            }
        },Config.KEY_ACTION,Config.ACTION_LOGIN,Config.KEY_PHONE_MD5,phone_md5,Config.KEY_CODE,code);
    }

    public static interface SuccessCallBack{
        void success(String token);
    }

    public static interface FailCallBack{
        void fail();
    }
}
