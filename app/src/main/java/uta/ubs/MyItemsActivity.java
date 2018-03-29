package uta.ubs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by harsh on 3/28/2018.
 */

public class MyItemsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lv, sv, ev;
    ItemService is;
    ArrayList<Item> list, list_sell, list_exchange;
    ItemAdapter il, isi, ie;
    SharedPreferences sharedPreferences;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_items);
        lv = (ListView) findViewById(R.id.lend_myitems);
        sv = (ListView) findViewById(R.id.sell_myitems);
        ev = (ListView) findViewById(R.id.exchange_myitems);
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        id = sharedPreferences.getString("userid",null).toString();
        list = new ArrayList<>();
        list_sell = new ArrayList<>();
        list_exchange = new ArrayList<>();
        is = new ItemService();
        list = is.getMyItems("Lend", id);
        Log.d("here", list.toString());
        il = new ItemAdapter(this, list);
        list_sell = is.getMyItems("Sell", id);
        Log.d("here", list_sell.toString());
        isi = new ItemAdapter(this, list_sell);
        list_exchange = is.getMyItems("Exchange", id);
        Log.d("here", list_exchange.toString());
        ie = new ItemAdapter(this, list_exchange);
        lv.setAdapter(il);
        lv.setOnItemClickListener(this);
        sv.setAdapter(isi);
        sv.setOnItemClickListener(this);
        ev.setAdapter(ie);
        ev.setOnItemClickListener(this);
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
    }
}
