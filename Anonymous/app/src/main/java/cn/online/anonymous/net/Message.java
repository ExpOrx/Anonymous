package cn.online.anonymous.net;

/**
 * Created by John on 2016/10/19.
 */

public class Message {
    private String msg = null;
    private String msgID = null;
    private String phone_md5 = null;

    public String getMsg() {
        return msg;
    }

    public String getMsgID() {
        return msgID;
    }

    public String getPhone_md5() {
        return phone_md5;
    }

    public Message(String msg, String msgID, String phone_md5){
        this.msg=msg;
        this.msgID=msgID;
        this.phone_md5=phone_md5;
    }

}
