package cn.online.anonymous.net;

import org.json.JSONException;
import org.json.JSONObject;

import cn.online.anonymous.Config;

/**
 * Created by 16611 on 2016/9/25.
 */
public class GetCode {
    public GetCode(String phone,final SuccessCallBack successCallBack,final FailCallBack failCallBack){
        /**
         * 操作网络连接来获取验证码
         * */

        new NetConnection(Config.SERVER_URL,HttpMethod.POST, new NetConnection.SuccessCallBack() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);//JSON数据解析
                    switch (jsonObject.getInt(Config.KEY_STATUS)){
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successCallBack!=null){
                                successCallBack.onSuccess();
                            }
                            break;
                        default:
                            //获取失败和Token超时无效而失败
                            if (failCallBack!=null){
                                failCallBack.onFail();
                            }
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    if (failCallBack!=null){
                        failCallBack.onFail();
                    }
                }
            }
        },new NetConnection.FailCallBack(){
            @Override
            public void onFail() {
                if (failCallBack!=null){
                    failCallBack.onFail();
                }
            }
        },Config.KEY_ACTION,Config.ACTION_GET_CODE,Config.KEY_PHONE_NUM,phone);//Config.KEY_ACTION,Config.ACTION_GET_CODE,Config.KEY_PHONE_NUM是String...


    }

    //获取成功接口
    public static interface SuccessCallBack{
        void onSuccess();

    }

    //获取失败接口
    public static interface FailCallBack{
        void onFail();

    }
}
