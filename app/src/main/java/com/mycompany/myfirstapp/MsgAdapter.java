package com.mycompany.myfirstapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tb.emoji.EmojiUtil;

import java.io.IOException;
import java.util.List;

/**
 * Created by lenovo on 2016/7/4.
 */
public class MsgAdapter extends ArrayAdapter<Msg> {
    private int resourceId;
    public MsgAdapter(Context context, int textViewResourceId, List<Msg> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Msg msg = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.leftLayout = (LinearLayout) view.findViewById
                    (R.id.left_layout);
            viewHolder.rightLayout = (LinearLayout) view.findViewById
                    (R.id.right_layout);
            viewHolder.centerLayout=(LinearLayout)view.findViewById
                    (R.id.center_layout);
            viewHolder.leftMsg = (TextView) view.findViewById(R.id.left_msg);
            viewHolder.rightMsg = (TextView) view.findViewById(R.id.right_msg);
            viewHolder.centerMsg = (TextView) view.findViewById(R.id.center_msg);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        if (msg.getType() == Msg.TYPE_RECEIVED) {
// 如果是收到的消息，则显示左边的消息布局，将右边的消息布局隐藏
            viewHolder.leftLayout.setVisibility(View.VISIBLE);
            viewHolder.rightLayout.setVisibility(View.GONE);
            viewHolder.centerLayout.setVisibility(View.GONE);
            /*viewHolder.leftMsg.setText(msg.getContent());*/

            try {
                EmojiUtil.handlerEmojiText(viewHolder.leftMsg, msg.getContent(), getContext());
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if(msg.getType() == Msg.TYPE_SENT) {
// 如果是发出的消息，则显示右边的消息布局，将左边的消息布局隐藏
            viewHolder.rightLayout.setVisibility(View.VISIBLE);
            viewHolder.leftLayout.setVisibility(View.GONE);
            viewHolder.centerLayout.setVisibility(View.GONE);
            viewHolder.rightMsg.setText(msg.getContent());

            try {
                EmojiUtil.handlerEmojiText(viewHolder.rightMsg, msg.getContent(), getContext());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if(msg.getType()==Msg.TYPE_SYS){
            viewHolder.rightLayout.setVisibility(View.GONE);
            viewHolder.leftLayout.setVisibility(View.GONE);
            viewHolder.centerLayout.setVisibility(View.VISIBLE);
            try {
                EmojiUtil.handlerEmojiText(viewHolder.centerMsg, msg.getContent(), getContext());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return view;
    }
    class ViewHolder {
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        LinearLayout centerLayout;
        TextView leftMsg;
        TextView rightMsg;
        TextView centerMsg;
    }
}