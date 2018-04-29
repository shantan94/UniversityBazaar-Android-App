package uta.ubs;


import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ann on 2/13/2018.
 */

public class HomeActivity extends AppCompatActivity {

    private AdView mAdView;
    Button send;
    Button button2;
    Button button3;
    ArrayList<Message> list = new ArrayList<>();
    ArrayList<Message> templist = new ArrayList<>();
    ListView lv;
    EditText chat;
    SharedPreferences sharedPreferences;
    Message m;
    MessageService ms;
    ChatAdapter ca;
    TextView count;
    boolean done = true;
    String id = "";
    String curtime = "";
    int chatSize = 300;
    DrawerLayout mdl;
    NavigationView nv;
    String imageid = "";
    Context context;
    ImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mdl = findViewById(R.id.drawer_layout);
        nv = findViewById(R.id.nav_view);
        context = this;
        lv = (ListView) findViewById(R.id.textView2);
        profile = (ImageView) nv.getHeaderView(0).findViewById(R.id.profile_picture);
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        id = sharedPreferences.getString("userid",null).toString();
        imageid = sharedPreferences.getString("imageid",null).toString();
        System.out.println(profile);
        Picasso.with(context).load("https://s3-us-west-2.amazonaws.com/item-bucket/" + imageid).into(profile);
        ms = new MessageService();
        templist = ms.getMessages();
        list = new ArrayList<>(templist);
        if(list.size() != 0)
            curtime = list.get(list.size() - 1).getDate();
        ca = new ChatAdapter(this, list, id);
        count = (TextView) findViewById(R.id.count);
        count.setText(0 + "/" + chatSize);
        lv.setAdapter(ca);
        send = (Button) findViewById(R.id.send);
        button2 = (Button) findViewById(R.id.button2);
        chat = (EditText) findViewById(R.id.chat);
        send.setEnabled(false);
        HomeActivity.AsyncTaskRunner  runner = new HomeActivity.AsyncTaskRunner();
        runner.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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

        chat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                return;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().equals(""))
                    send.setEnabled(false);
                else
                    send.setEnabled(true);
                int strlen = chat.getText().toString().length();
                count.setText(strlen + "/" + chatSize);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                return;
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                curtime = getCurrentTime();
                m = new Message(chat.getText().toString(), id, curtime);
                sendNotification(id, chat.getText().toString());
                ms.insertMessage(m);
                list.add(m);
                chat.setText("");
                ca.notifyDataSetChanged();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(HomeActivity.this,
                        MarketPlace.class);
                startActivity(myIntent);
            }
        });


        button3 = (Button) findViewById(R.id.button3);

        // Capture button clicks
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(HomeActivity.this,
                        Clubs.class);
                startActivity(myIntent);
            }
        });
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        done = false;
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        done = true;
//        HomeActivity.AsyncTaskRunner runner = new HomeActivity.AsyncTaskRunner();
//        runner.execute();
//    }

    private class AsyncTaskRunner extends AsyncTask<Void, Void, Void> {
        ArrayList<Message> list1;

        @TargetApi(Build.VERSION_CODES.O)
        @Override
        protected Void doInBackground(Void... voids) {
            while (true) {
                System.out.println("here");
                list1 = new ArrayList<>();
                try {
                    Thread.sleep(2000);
                    if(curtime.contains("T")) {
                        String[] temparr = curtime.split("T");
                        String[] temparr1 = temparr[1].split("\\.");
                        String finaltime = temparr[0] + " " + temparr1[0];
                        list1 = ms.getMessagesAfterTime(finaltime);
                    }
                    else
                        list1 = ms.getMessagesAfterTime(curtime);
                    if(list1.size() != 0) {
                        curtime = list1.get(list1.size() - 1).getDate();
                        for (int i = 0;i < list1.size();i ++) {
                            Thread.sleep(100);
                            sendNotification(list1.get(i).getUserid(), list1.get(i).getMessage());
                        }
                        list.addAll(list1);
                        updateListView();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
//            return null;
        }
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

    public String getCurrentTime(){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    public void updateListView(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ca.notifyDataSetChanged();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendNotification(String id, String message) {
        System.out.println(message);
        String CHANNEL_ID = "my_channel_01";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, "my channel", importance);
        mNotificationManager.createNotificationChannel(mChannel);
        Notification notification = new Notification.Builder(HomeActivity.this, CHANNEL_ID)
                .setContentTitle(id)
                .setContentText(message)
                .setSmallIcon(R.drawable.other)
                .setChannelId(CHANNEL_ID)
                .build();
        mNotificationManager.notify(101 + (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE), notification);
    }
}