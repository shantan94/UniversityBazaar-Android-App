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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by shantan on 2/14/2018.
 */

public class Clubs extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Button create;
    ListView lv;
    ArrayList<Club> list = new ArrayList<>();
    ClubService cs;
    ClubAdapter ca;
    DrawerLayout mdl;
    NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clubs);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mdl = findViewById(R.id.drawer_layout);
        nv = findViewById(R.id.nav_view);
        create = (Button) findViewById(R.id.ccbutton);
        lv = (ListView) findViewById(R.id.listView);
        cs = new ClubService();
        list = cs.getClubs();
        ca = new ClubAdapter(this, list);
        lv.setAdapter(ca);
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

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(), CreateClub.class);
                startActivity(next);
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView description = (TextView) view.findViewById(R.id.description);
        TextView userid = (TextView) view.findViewById(R.id.userid);
        Bundle b = new Bundle();
        b.putString("name", name.getText().toString());
        b.putString("description", description.getText().toString());
        b.putString("userid", userid.getText().toString());
        Intent next = new Intent(getApplicationContext(), ClubDetails.class);
        next.putExtras(b);
        startActivity(next);
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
