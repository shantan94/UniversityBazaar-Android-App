package uta.ubs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by shantan on 3/28/2018.
 */

public class CreateClub extends AppCompatActivity {

    private AdView mAdView;

    SharedPreferences sharedPreferences;
    String id;
    Button send;
    EditText cname;
    EditText cdes;
    ClubService cs;
    DrawerLayout mdl;
    NavigationView nv;
    String username;
    TextView countword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_club);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mdl = findViewById(R.id.drawer_layout);
        nv = findViewById(R.id.nav_view);
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        id = sharedPreferences.getString("userid",null).toString();
        username = sharedPreferences.getString("username",null).toString();
        send = (Button) findViewById(R.id.Csubmit);
        cname = (EditText) findViewById(R.id.Cname);
        cdes = (EditText) findViewById(R.id.Cdes);
        countword = (TextView) findViewById(R.id.count);
        cs = new ClubService();

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        cdes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                return;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.length();
                if(length >= 100) {
                    countword.setText(length + "/100");
                    Toast.makeText(getApplicationContext(), "Club description should be less than 100 characters", Toast.LENGTH_SHORT).show();
                }
                else
                    countword.setText(length + "/100");
            }

            @Override
            public void afterTextChanged(Editable s) {
                return;
            }
        });

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
            }
        });

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
                    case R.id.nav_profile:{
                        mdl.closeDrawers();
                        Intent next = new Intent(getApplicationContext(), ProfilePage.class);
                        startActivity(next);
                        return true;
                    }
                    case R.id.nav_passwordreset:{
                        mdl.closeDrawers();
                        Intent next = new Intent(getApplicationContext(), ResetPasswordActivity.class);
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
                    case R.id.nav_logout:{
                        mdl.closeDrawers();
                        SharedPreferences sp = getBaseContext().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                        sp.edit().clear().commit();
                        Intent next = new Intent(getApplicationContext(), WelcomeActivity.class);
                        next.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(next);
                        return true;
                    }
                    case R.id.nav_supportemail:{
                        mdl.closeDrawers();
                        Intent next = new Intent(getApplicationContext(), SupportEmail.class);
                        startActivity(next);
                        return true;
                    }
                }
                return false;
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = cname.getText().toString();
                String des = cdes.getText().toString();
                if(name.length() <= 4 || name.length() >= 20)
                    Toast.makeText(getApplicationContext(), "Club name should be between 4 and 20 characters", Toast.LENGTH_SHORT).show();
                else {
                    String status = cs.insertClub(name, des, id);
                    String status1 = cs.insertClubMember(name, id, username);
                    System.out.println(status1);
                    Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                    Intent next = new Intent(getApplicationContext(), Clubs.class);
                    startActivity(next);
                }
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
