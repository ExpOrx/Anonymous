package cn.online.anonymous.net;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import cn.online.anonymous.Config;


/**
 * Created by 16611 on 2016/9/23.
 * 网络链接，与服务器进行通信的基类
 */
public class NetConnection {
    //内容为String...kvs字符串可变参数
    public NetConnection(final String url, final HttpMethod method, final SuccessCallBack successCallBack,
                         final FailCallBack failCallBack, final String ...kvs){
         new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                StringBuffer paramsStr = new StringBuffer();
                for (int i = 0; i <kvs.length ; i+=2) {
                    paramsStr.append(kvs[i]).append("=").append("&");//以键值对的方式存取数据 每对键值对之间用&分开
                }

                try {
                    URLConnection urlConnerction=null;
                    switch (method){
                        case POST:
                            urlConnerction=new URL(url).openConnection();
                            urlConnerction.setDoOutput(true);//给服务器说明操作正确
                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(urlConnerction.getOutputStream(),
                                    Config.CHAESET));
                            writer.write(paramsStr.toString());//POST以流的方式写入服务器
                            writer.flush();
                            break;
                        default:
                            urlConnerction=new URL(url+"?"+paramsStr.toString()).openConnection();//直接在url里面加参数传递
                            break;
                    }

                    System.out.println("url"+urlConnerction.getURL());//连接的URL
                    System.out.print("Data"+urlConnerction.getDate());//传送的数据

                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnerction.getInputStream(),
                            Config.CHAESET));
                    String line=null;//以一行的方式读取数据
                    StringBuffer result = new StringBuffer();//贮存读取的结果
                    while ((line=reader.readLine())!=null){
                        result.append(line);
                    }

                    System.out.println("Result"+result);//都出来的结果

                    return result.toString();
                } catch (IOException e) {
                        e.printStackTrace();
                    }
            return null;
            }

            /**
             * 在doInBackground(Void... params)执行后执行，执行的参数是doInBackground的return结果
             * 其目的是执行主线程
             * */
            @Override
            protected void onPostExecute(String result) {
                 if(result!=null){
                     if (successCallBack!=null){//成功
                         successCallBack.onSuccess(result);
                     }
                 }else{
                     //失败
                     if(failCallBack!=null){
                         failCallBack.onFail();
                     }
                 }
                 super.onPostExecute(result);
            }
         }.execute();
    }

    //回掉函数成功接口
    public static interface SuccessCallBack{
        void onSuccess(String result);//服务器传递的内容是字符串类型

    }

    //回掉函数失败接口
    public static interface FailCallBack{
        void onFail();//服务器传递的内容是字符串类型

    }
}
