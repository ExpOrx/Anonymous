package cn.online.anonymous;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 16611 on 2016/9/25.
 */
public class Config {

    public  static final String SERVER_URL="未知";
    public  static final String ACTION_GET_CODE="send_pass";
    public  static final String ACTION_LOGIN="login";

    public  static final String KEY_ACTION="action";
    public  static final String KEY_PHONE_NUM="phone";
    public  static final String KEY_PHONE_MD5="phone_md5";
    public  static final String KEY_STATUS="status";

    public  static final int RESULT_STATUS_SUCCESS=1;//获取成功
    public  static final int RESULT_STATUS_FAIL=0;//获取失败
    public  static final int RESULT_STATUS_OVERTIME=2;//Token超时

    public  static final String KEY_TOKE="toke";
    public  static final String APP_ID="cn.online.anonymous";

    public static final String CHAESET ="utf-8" ;//编码方式
    public static final String  KEY_CODE ="code" ;

    public static final String ACTION_UPLOAD_CONTACTS ="upload_contancts" ;
    public static final String KEY_TIMELINE ="timeline" ;
    public static final String KEY_MSG_ID = "msgid";
    public static final String KEY_MSG ="msg" ;
    public static final String GET_COMMENT ="get_comment" ;
    public static final String KEY_COMMENTS ="comments" ;
    public static final String KEY_CONTENT ="content" ;
    public static final String ACTION_PUBCOMMENT ="pub_comment" ;
    public static final String ACTION_PUBLISH = "publish";
    public static String KEY_CONTACTS = "contacts";
    public static String ACTION_TIMELINE="timeline";
    public static String KEY_PAGE = "page";
    public static String KEY_PERPAGE = "perpage";


    /**
     * 返回Token(登陆令牌)
     * */
    public static  String getCachedToken(Context context){
        return  context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_TOKE,null);
    }

    /**
     * 缓存Token
     * */
    public static  void achedToken(Context context,String token){
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_TOKE,token);
        editor.commit();
    }

    /**
     * 返回电话号码(登陆令牌)
     * */
    public static  String getCachedPhoneum(Context context){
        return  context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(KEY_PHONE_NUM,null);
    }

    /**
     * 缓存电话号码
     * */
    public static  void achedPhoneum(Context context,String phoneNum){
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_PHONE_NUM,phoneNum);
        editor.commit();
    }
}
