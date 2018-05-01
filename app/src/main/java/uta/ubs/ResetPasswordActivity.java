package uta.ubs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

/**
 * Created by Ann on 3/27/2018.
 */

public class ResetPasswordActivity extends AppCompatActivity {

    private AdView mAdView;
    RegisterService rs = null;
    Button Reset;
    static String MyPREFERENCES = "Session Values";
    SharedPreferences sharedPreferences;
    DrawerLayout mdl;
    NavigationView nv;
    TextView profile_name;
    String username;
    ImageView profile;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mdl = findViewById(R.id.drawer_layout);
        nv = findViewById(R.id.nav_view);
        profile_name = (TextView) nv.getHeaderView(0).findViewById(R.id.profile_username);
        context = this;
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Reset = (Button) findViewById(R.id.Reset);
        final EditText userid = (EditText) findViewById(R.id.usernameData);
        final EditText CurrentPasswordData = (EditText) findViewById(R.id.CurrentPasswordData);
        final EditText NewPasswordData = (EditText) findViewById(R.id.NewPasswordData);
        final EditText ConfirmPasswordData = (EditText) findViewById(R.id.ConfirmPasswordData);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        rs = new RegisterService();
        profile = (ImageView) nv.getHeaderView(0).findViewById(R.id.profile_picture);
        String imageid = sharedPreferences.getString("imageid",null).toString();
        username = sharedPreferences.getString("username",null).toString();
        profile_name.setText(username);
        Picasso.with(context).load("https://s3-us-west-2.amazonaws.com/item-bucket/" + imageid).into(profile);

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(next);
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
        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = userid.getText().toString();
                String CurrentPassword = CurrentPasswordData.getText().toString();
                String NewPassword = NewPasswordData.getText().toString();
                String ConfirmPassword = ConfirmPasswordData.getText().toString();
                String status = rs.login(username, CurrentPassword);
                if (status.equals("Passed")) {
                    if (NewPassword.equals(ConfirmPassword)) {
                        String status1 = rs.reset(username,NewPassword);
                        Toast.makeText(getApplicationContext(), status1, Toast.LENGTH_SHORT).show();
                        Intent next = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(next);

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }

                } else
                    Toast.makeText(getApplicationContext(), "Username or password does not exist", Toast.LENGTH_SHORT).show();
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