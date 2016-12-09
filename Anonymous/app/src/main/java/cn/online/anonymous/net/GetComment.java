package cn.online.anonymous.net;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

import cn.online.anonymous.Config;

/**
 * Created by John on 2016/12/2.
 */

public class GetComment {
    public GetComment(String phone_md5, String token, int page, int perpage, String msdid, final SuccessCallBack successCallBack,final FailCallBack failCallBack  ){
        new NetConnection(Config.SERVER_URL,HttpMethod.POST, new NetConnection.SuccessCallBack() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    //
                    switch (jsonObject.getInt(Config.KEY_STATUS)){
                        case Config.RESULT_STATUS_SUCCESS:
                            if(successCallBack!=null){
                                List<Comments> comments = new ArrayList<>();
                                JSONArray jsonArray=jsonObject.getJSONArray(Config.KEY_COMMENTS);
                                JSONObject obj=null;
                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    obj=jsonArray.getJSONObject(i);
                                    comments.add(new Comments(obj.getString(Config.KEY_CONTENT),obj.getString(Config.KEY_PHONE_MD5)));
                                }
                                successCallBack.success(jsonObject.getString(Config.KEY_MSG_ID),jsonObject.getInt(Config.KEY_PAGE),jsonObject.getInt(Config.KEY_PERPAGE),comments);
                            }
                            break;
                        case Config.RESULT_STATUS_OVERTIME:
                            break;
                        default:
                            if(failCallBack!=null){
                                failCallBack.fail(Config.RESULT_STATUS_FAIL);
                            }
                            break;
                    }
                } catch (JSONException e) {
                    if(failCallBack!=null){
                        failCallBack.fail(Config.RESULT_STATUS_FAIL);
                    }
                    e.printStackTrace();
                }
            }
        }, new NetConnection.FailCallBack() {
            @Override
            public void onFail() {
                if(failCallBack!=null){
                    failCallBack.fail(Config.RESULT_STATUS_FAIL);
                }
            }
        },Config.KEY_ACTION,Config.GET_COMMENT,
          Config.KEY_TOKE,token,
          Config.KEY_PHONE_MD5,phone_md5,
          Config.KEY_PAGE,page+"",
          Config.KEY_PERPAGE,perpage+"",
          Config.KEY_MSG_ID,msdid);
    }

    public static interface SuccessCallBack{
        void success(String msgid,int page, int perpage, List<Comments> comments);
    }

    public static interface FailCallBack{
        void fail(int errorCode);
    }
}
