package cn.online.anonymous.net;

import org.json.JSONException;
import org.json.JSONObject;

import cn.online.anonymous.Config;

/**
 * Created by John on 2016/12/2.
 */

public class Publish {
    public Publish(String  phone_md5,String token,String msg, final SuccessCallBack successCallBack,final FailCallBack failCallBack){
        new NetConnection(Config.SERVER_URL,HttpMethod.POST, new NetConnection.SuccessCallBack() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    switch (jsonObject.getInt(Config.KEY_STATUS)){
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successCallBack!=null){
                                successCallBack.success();
                            }
                            break;
                        case Config.RESULT_STATUS_OVERTIME:
                            if (failCallBack!=null)
                                failCallBack.fail(Config.RESULT_STATUS_OVERTIME);
                            break;
                        default:
                            if (failCallBack!=null)
                                failCallBack.fail(Config.RESULT_STATUS_FAIL);
                            break;
                    }
                } catch (JSONException e) {
                    if (failCallBack!=null)
                        failCallBack.fail(Config.RESULT_STATUS_OVERTIME);
                    e.printStackTrace();
                }
            }
        }, new NetConnection.FailCallBack() {
            @Override
            public void onFail() {
                failCallBack.fail(Config.RESULT_STATUS_FAIL);
            }
        },Config.KEY_ACTION,Config.ACTION_PUBLISH,
                Config.KEY_PHONE_MD5,phone_md5,
                Config.KEY_TOKE,token,
                Config.KEY_MSG,msg);

    }

    public static interface SuccessCallBack{
        void success();
    }

    public static interface FailCallBack{
        void fail(int errorCode);
    }
}
