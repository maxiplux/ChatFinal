package net.juanfrancisco.blog.chatfinal.ui.adapters;

/**
 * Created by juan on 8/12/17.
 */


import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.juanfrancisco.blog.chatfinal.R;
import net.juanfrancisco.blog.chatfinal.models.ChatMessage;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<ChatMessage> {

    private Activity activity;
    private List<ChatMessage> messages;

    public MessageAdapter(Activity context, int resource, List<ChatMessage> objects)
    {
        super(context, resource, objects);
        this.activity = context;
        this.messages = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        int layoutResource = 0; // determined by view type
        ChatMessage chatMessage = getItem(position);
        int viewType = getItemViewType(position);


        layoutResource = R.layout.item_chat_left;


        if (!chatMessage.isMine())
        {
            layoutResource = R.layout.item_chat_right;


        }




            convertView = inflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder(convertView);
            holder.msg.setText(chatMessage.getContent());

            convertView.setTag(holder);



        Log.w("lotengo-adapter-idresource", String.valueOf(layoutResource)+"--"+chatMessage.toString());

        //set message content


        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        // return the total number of view types. this value should never change
        // at runtime
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        // return a value between 0 and (getViewTypeCount - 1)
        return position % 2;
    }

    private class ViewHolder {
        private TextView msg;

        public ViewHolder(View v) {
            msg = (TextView) v.findViewById(R.id.txt_msg);
        }
    }
}

