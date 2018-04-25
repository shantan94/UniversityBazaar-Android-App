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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by shantan on 2/14/2018.
 */

public class MarketPlace extends AppCompatActivity {

    ImageButton list_item;
    ImageButton buy_item;
    ImageButton lend_item;
    ImageButton exchange_item;
    Button myitems;
    DrawerLayout mdl;
    NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketplace);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mdl = findViewById(R.id.drawer_layout);
        nv = findViewById(R.id.nav_view);
        list_item = (ImageButton) findViewById(R.id.list_item);
        buy_item = (ImageButton) findViewById(R.id.sell_item);
        lend_item = (ImageButton) findViewById(R.id.lend_item);
        exchange_item = (ImageButton) findViewById(R.id.exchange_item);
        myitems = (Button) findViewById(R.id.myitems);

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

        list_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(getApplicationContext(), ListItemPage.class);
                startActivity(next);
            }
        });

        buy_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(getApplicationContext(), SellActivity.class);
                startActivity(next);
            }
        });

        lend_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(getApplicationContext(), LendActivity.class);
                startActivity(next);
            }
        });

        exchange_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(getApplicationContext(), ExchangeActivity.class);
                startActivity(next);
            }
        });

        myitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(getApplicationContext(), MyItemsActivity.class);
                startActivity(next);
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
}
