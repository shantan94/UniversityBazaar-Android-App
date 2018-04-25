package uta.ubs;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by harsh on 2/28/2018.
 */

public class NegotiateChat extends AppCompatActivity {

    ListView lv;
    EditText chat;
    Button send;
    ArrayList<Negotiate> list = new ArrayList<>();
    ArrayList<Negotiate> templist = new ArrayList<>();
    NegotiateAdapter na;
    NegotiateService ns;
    String id;
    SharedPreferences sharedPreferences;
    String curtime = "";
    Negotiate n;
    Bundle b;
    DrawerLayout mdl;
    NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.negotiate_chat);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mdl = findViewById(R.id.drawer_layout);
        nv = findViewById(R.id.nav_view);
        Intent current = this.getIntent();
        b = current.getExtras();
        lv = (ListView) findViewById(R.id.listView);
        ns = new NegotiateService();
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        id = sharedPreferences.getString("userid",null).toString();
        templist = ns.getNegotiations(id, b.getString("nextuser"), b.getString("imageid"));
        list = new ArrayList<>(templist);
        na = new NegotiateAdapter(this, list, id);
        lv.setAdapter(na);
        send = (Button) findViewById(R.id.send);
        send.setEnabled(false);
        chat = (EditText) findViewById(R.id.chat);

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
                n = new Negotiate(chat.getText().toString(), b.getString("curuser"), b.getString("nextuser"), curtime, b.getString("imageid"));
                ns.insertMessage(n);
                list.add(n);
                sendNotification(id, chat.getText().toString());
                System.out.println(list);
                chat.setText("");
                na.notifyDataSetChanged();
            }
        });
    }
    public String getCurrentTime(){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendNotification(String id, String message) {
        String CHANNEL_ID = "my_channel_01";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, "my channel", importance);
        mNotificationManager.createNotificationChannel(mChannel);
        Notification notification = new Notification.Builder(NegotiateChat.this, CHANNEL_ID)
                .setContentTitle(id)
                .setContentText(message)
                .setSmallIcon(R.drawable.other)
                .setChannelId(CHANNEL_ID)
                .build();
        mNotificationManager.notify(101 + (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE), notification);
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
