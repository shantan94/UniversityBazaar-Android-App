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

public class ClubAdapter extends ArrayAdapter<Club> {
    private final Context context;
    private final ArrayList<Club> values;

    public ClubAdapter(Context context, ArrayList<Club> values){
        super(context, R.layout.activity_club_list, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View clubView = inflater.inflate(R.layout.activity_club_list, parent, false);
        TextView name = (TextView) clubView.findViewById(R.id.name);
        TextView userid = (TextView) clubView.findViewById(R.id.userid);
        TextView description = (TextView) clubView.findViewById(R.id.description);
        userid.setText(values.get(position).getUserid());
        name.setText(values.get(position).getName());
        description.setText(values.get(position).getDescription());
        return clubView;
    }
}
