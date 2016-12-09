package cn.online.anonymous.net;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.online.anonymous.Config;

/**
 * Created by John on 2016/10/19.
 */

public class Timeline {
    public Timeline(String phone_md5, String token, int page, String perpage, final SuccessCallBack successCallBack, final FailCallBack failCallBack){
        new NetConnection(Config.SERVER_URL,HttpMethod.POST, new NetConnection.SuccessCallBack() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    switch (jsonObject.getInt(Config.KEY_STATUS)){
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successCallBack!=null){
                                List<Message> messages = new ArrayList<>();
                                JSONArray magArray = jsonObject.getJSONArray(Config.KEY_TIMELINE);
                                JSONObject msgobj=null;
                                for (int i = 0; i <messages.size() ; i++) {
                                    msgobj = magArray.getJSONObject(i);
                                    messages.add(new Message(msgobj.getString(Config.KEY_MSG_ID),msgobj.getString(Config.KEY_MSG),msgobj.getString(Config.KEY_PHONE_MD5)));
                                }
                                successCallBack.success(jsonObject.getInt(Config.KEY_PAGE),jsonObject.getInt(Config.KEY_PERPAGE),messages);
                            }
                            break;
                        case Config.RESULT_STATUS_OVERTIME:
                            if (failCallBack!=null)
                                failCallBack.fail(Config.RESULT_STATUS_OVERTIME);
                            break;
                        default:
                            if (failCallBack!=null)
                                failCallBack.fail(Config.RESULT_STATUS_OVERTIME);
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
                failCallBack.fail(Config.RESULT_STATUS_OVERTIME);
            }
        },Config.KEY_ACTION,Config.ACTION_TIMELINE, Config.KEY_PHONE_MD5,phone_md5, Config.KEY_TOKE,token,Config.KEY_PAGE,page+"",Config.KEY_PERPAGE,perpage);

    }

    public static interface SuccessCallBack{
        void success(int page, int perpage, List<Message> timeline);
    }

    public static interface FailCallBack{
        void fail(int errorCode);
    }
}
