package uta.ubs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by shantan on 4/26/2018.
 */

public class ClubMembersAdapter extends ArrayAdapter<MyNegotiations> {
    private final Context context;
    private final ArrayList<MyNegotiations> values;

    public ClubMembersAdapter(Context context, ArrayList<MyNegotiations> values){
        super(context, R.layout.activity_my_negotiations, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View clubMembersView = inflater.inflate(R.layout.activity_club_members_list, parent, false);
        TextView userid = (TextView) clubMembersView.findViewById(R.id.memberid);
        userid.setText(values.get(position).getUserid());
        return clubMembersView;
    }
}
