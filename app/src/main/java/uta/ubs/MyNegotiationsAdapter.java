package uta.ubs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by shantan on 3/29/2018.
 */

public class MyNegotiationsAdapter extends ArrayAdapter<MyNegotiations> {
    private final Context context;
    private final ArrayList<MyNegotiations> values;

    public MyNegotiationsAdapter(Context context, ArrayList<MyNegotiations> values){
        super(context, R.layout.activity_my_negotiations, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mynegotiationsView = inflater.inflate(R.layout.activity_my_negotiations, parent, false);
        TextView message = (TextView) mynegotiationsView.findViewById(R.id.message);
        TextView userid = (TextView) mynegotiationsView.findViewById(R.id.userid);
        userid.setText(values.get(position).getUserid());
        message.setText(values.get(position).getMessage());
        return mynegotiationsView;
    }
}
