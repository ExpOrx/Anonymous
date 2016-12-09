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
import cn.online.anonymous.net.Comments;


/**
 * Created by John on 2016/12/2.
 */

public class AcyMessageCommentsListAdapter extends BaseAdapter {
    private List<Comments> comments = new ArrayList<>();
    private Context context=null;

    public AcyMessageCommentsListAdapter(Context context){
        this.context=context;
    }

    public Context getContext() {
        return context;
    }
    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_message_cell,null);
            convertView.setTag(new AcyMessageCommentsListAdapter.ListCell((TextView) convertView.findViewById(R.id.tvCellLable)));
        }
        ListCell listCell= (ListCell) convertView.getTag();
        Comments message = (Comments) getItem(position);
        listCell.getTextView().setText( message.getContent());
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
    /**
     * 添加数据
     * */
    public void addAll(List<Comments> data){
        this.comments.addAll(data);
        notifyDataSetChanged();//刷新UI
    }

    /**
     * 清空数据
     * */
    public void clear(){
        this.comments.clear();
        notifyDataSetChanged();
    }
}
