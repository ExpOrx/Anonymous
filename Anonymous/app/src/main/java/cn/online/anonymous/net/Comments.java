package cn.online.anonymous.net;


/**
 * Created by John on 2016/12/2.
 */

public class Comments{
    private String content=null,phone_md5=null;
    public Comments(String content,String phone_md5){
       this.content=content;
       this.phone_md5=phone_md5;
    }

    public String getContent() {
        return content;
    }

    public String getPhone_md5() {
        return phone_md5;
    }
}
