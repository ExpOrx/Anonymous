package cn.online.anonymous.id;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.online.anonymous.Config;
import cn.online.anonymous.tools.MD5Tool;

/**
 * Created by 16611 on 2016/9/25.
 * 获取本地联络人
 */
public class MyContacts {
    public static String getContactsJSONString(Context context){
        //获取手机联系人
        Cursor cursor=context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject=null;
        String phoneNum=null;
        while(cursor.moveToNext()){
            //只获取电话号码
            /*public final @Nullable Cursor query(@NonNull Uri uri, @Nullable String[] projection,
            @Nullable String selection, @Nullable String[] selectionArgs,
            @Nullable String sortOrder) {
            return query(uri, projection, selection, selectionArgs, sortOrder, null);
            }*/
            phoneNum=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            if (phoneNum.charAt(0)=='+'&&phoneNum.charAt(1)=='8'&&phoneNum.charAt(2)=='6'){
                phoneNum=phoneNum.substring(3);
            }

            System.out.println(phoneNum);//打印出手机号
            jsonObject = new JSONObject();

            try {
                jsonObject.put(Config.KEY_PHONE_MD5, MD5Tool.md5(phoneNum));//md5加密电话号码
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }
}
