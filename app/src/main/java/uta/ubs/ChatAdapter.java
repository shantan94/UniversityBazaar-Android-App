package uta.ubs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shantan on 2/15/2018.
 */

public class ChatAdapter extends ArrayAdapter<Message> {
    private final Context context;
    private final ArrayList<Message> values;
    private final String id;

    public ChatAdapter(Context context, ArrayList<Message> values, String id){
        super(context, R.layout.activity_chat_list, values);
        this.context = context;
        this.values = values;
        this.id = id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        System.out.println(id);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(values.get(position).getUserid().equals(id)) {
            View chatView = inflater.inflate(R.layout.activity_chat_list, parent, false);
            TextView message = (TextView) chatView.findViewById(R.id.message);
            TextView userid = (TextView) chatView.findViewById(R.id.userid);
            TextView date = (TextView) chatView.findViewById(R.id.date);
            message.setText(values.get(position).getMessage());
            userid.setText(values.get(position).getUserid());
            date.setText(values.get(position).getDate());
            return chatView;
        }
        else{
            View chatView = inflater.inflate(R.layout.activity_chat_list1, parent, false);
            TextView message = (TextView) chatView.findViewById(R.id.message);
            TextView userid = (TextView) chatView.findViewById(R.id.userid);
            TextView date = (TextView) chatView.findViewById(R.id.date);
            message.setText(values.get(position).getMessage());
            userid.setText(values.get(position).getUserid());
            date.setText(values.get(position).getDate());
            return chatView;
        }
    }
}
