package uta.ubs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by shantan on 2/25/2018.
 */

public class SellActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lv;
    ItemService is;
    ArrayList<Item> list;
    ItemAdapter ia;
    DrawerLayout mdl;
    NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mdl = findViewById(R.id.drawer_layout);
        nv = findViewById(R.id.nav_view);
        lv = (ListView) findViewById(R.id.textView2);
        list = new ArrayList<>();
        is = new ItemService();
        list = is.getItems("Sell");
        Log.d("here", list.toString());
        ia = new ItemAdapter(this, list);
        lv.setAdapter(ia);
        lv.setOnItemClickListener(this);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:{
                        mdl.closeDrawers();
                        Intent next = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(next);
                        return true;
                    }
                    case R.id.nav_marketplace:{
                        mdl.closeDrawers();
                        Intent next = new Intent(getApplicationContext(), MarketPlace.class);
                        startActivity(next);
                        return true;
                    }
                    case R.id.nav_clubpage:{
                        mdl.closeDrawers();
                        Intent next = new Intent(getApplicationContext(), Clubs.class);
                        startActivity(next);
                        return true;
                    }
                    case R.id.nav_uploaditem:{
                        mdl.closeDrawers();
                        Intent next = new Intent(getApplicationContext(), ListItemPage.class);
                        startActivity(next);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mdl.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
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
        Intent next = new Intent(getApplicationContext(), BuyActivityDetails.class);
        next.putExtras(b);
        startActivity(next);
    }
}
