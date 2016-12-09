package cn.online.anonymous.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.online.anonymous.R;
import cn.online.anonymous.net.Message;

/**
 * Created by John on 2016/10/19.
 */

public class AcyTimelineMessageListAdapter extends BaseAdapter {
    private List<Message> data = new ArrayList<>();
    private Context context=null;
    public AcyTimelineMessageListAdapter(Context context){
        this.context=context;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Message getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 添加数据
     * */
    public void addAll(List<Message> data){
        this.data.addAll(data);
        notifyDataSetChanged();//刷新UI
    }

    /**
     * 清空数据
     * */
    public void clear(){
        this.data.clear();
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_timeline_cell,null);
            convertView.setTag(new ListCell((TextView) convertView.findViewById(R.id.tvCellLable)));
        }
        ListCell listCell= (ListCell) convertView.getTag();
        Message message = getItem(position);
        listCell.getTextView().setText(message.getMsg());
        return convertView;
    }
    private static class ListCell{
        private TextView textView=null;
        public ListCell(TextView textView){
            this.textView=textView;
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
