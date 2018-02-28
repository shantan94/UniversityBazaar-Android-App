package uta.ubs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by shantan on 2/28/2018.
 */

public class LendActivityDetails extends AppCompatActivity {
    TextView itemname;
    TextView description;
    TextView price;
    TextView userid;
    ImageView image;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend_details);
        Intent current = this.getIntent();
        context = this;
        Bundle b = current.getExtras();
        itemname = (TextView) findViewById(R.id.item_name);
        description = (TextView) findViewById(R.id.description);
        price = (TextView) findViewById(R.id.price);
        userid = (TextView) findViewById(R.id.userid);
        image = (ImageView) findViewById(R.id.image);
        itemname.setText(b.getString("itemname"));
        description.setText(b.getString("description"));
        price.setText(b.getString("price"));
        userid.setText(b.getString("userid"));
        Picasso.with(context).load("https://s3-us-west-2.amazonaws.com/item-bucket/" + b.getString("imageid")).into(image);
    }
}
