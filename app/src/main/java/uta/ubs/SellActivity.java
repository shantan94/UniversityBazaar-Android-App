package uta.ubs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by shantan on 2/25/2018.
 */

public class SellActivity extends AppCompatActivity {

    ListView lv;
    ItemService is;
    ArrayList<Item> list;
    ItemAdapter ia;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_page);
        lv = (ListView) findViewById(R.id.textView2);
        list = new ArrayList<>();
        is = new ItemService();
        list = is.getItems("Sell");
        Log.d("here", list.toString());
        ia = new ItemAdapter(this, list);
        lv.setAdapter(ia);
    }
}
