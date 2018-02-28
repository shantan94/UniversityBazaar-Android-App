package uta.ubs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by shantan on 2/25/2018.
 */

public class ExchangeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lv;
    ItemService is;
    ArrayList<Item> list;
    ItemAdapter ia;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_page);
        if(Build.VERSION.SDK_INT>9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        lv = (ListView) findViewById(R.id.textView2);
        list = new ArrayList<>();
        is = new ItemService();
        list = is.getItems("Exchange");
        Log.d("here", list.toString());
        ia = new ItemAdapter(this, list);
        lv.setAdapter(ia);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView itemname = (TextView) view.findViewById(R.id.item_name);
        TextView description = (TextView) view.findViewById(R.id.description);
        TextView price = (TextView) view.findViewById(R.id.price);
        TextView userid = (TextView) view.findViewById(R.id.userid);
        TextView imageid = (TextView) view.findViewById(R.id.imageid);
        Bundle b = new Bundle();
        b.putString("itemname", itemname.getText().toString());
        b.putString("description", description.getText().toString());
        b.putString("price", price.getText().toString());
        b.putString("userid", userid.getText().toString());
        b.putString("imageid", imageid.getText().toString());
        Intent next = new Intent(getApplicationContext(), ExchangeActivityDetails.class);
        next.putExtras(b);
        startActivity(next);
    }
}
