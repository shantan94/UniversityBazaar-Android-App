package uta.ubs;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by shantan on 2/25/2018.
 */

public class ItemAdapter extends ArrayAdapter<Item> {
    private final Context context;
    private final ArrayList<Item> values;

    public ItemAdapter(Context context, ArrayList<Item> values){
        super(context, R.layout.activity_item_list, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.activity_item_list, parent, false);
        ImageView image = (ImageView) itemView.findViewById(R.id.image);
        TextView item_name = (TextView) itemView.findViewById(R.id.item_name);
        TextView description = (TextView) itemView.findViewById(R.id.description);
        TextView price = (TextView) itemView.findViewById(R.id.price);
        TextView userid = (TextView) itemView.findViewById(R.id.userid);
        try {
            Picasso.with(context).load("https://s3-us-west-2.amazonaws.com/item-bucket/" + values.get(position).getImage()).into(image);
            item_name.setText(values.get(position).getItemname());
            description.setText(values.get(position).getDescription());
            price.setText(values.get(position).getPrice());
            userid.setText(values.get(position).getUserid());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return itemView;
    }
}
