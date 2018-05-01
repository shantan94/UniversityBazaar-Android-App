package uta.ubs;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by harsh on 3/29/2018.
 */

public class ClubDetails extends AppCompatActivity {

    private AdView mAdView;

    Bundle b;
    TextView name;
    ArrayList<String> list;
    ClubService cs;
    Button b1;
    RelativeLayout rl;
    ArrayList<Message> list1 = new ArrayList<>();
    ListView lv;
    EditText chat;
    ChatAdapter ca;
    Button send;
    Message m;
    String curtime = "";
    boolean done = true;
    int chatSize = 300;
    TextView count;
    DrawerLayout mdl;
    NavigationView nv;
    Button ml;
    String username;
    ImageView profile;
    TextView profile_name;
    Context context;
    SharedPreferences sharedPreferences;
    String id;
    String imageid;
    Button leave_club;
    Button delete_club;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.activity_club_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mdl = findViewById(R.id.drawer_layout);
        nv = findViewById(R.id.nav_view);
        ml = (Button) findViewById(R.id.memberlist);
        context = this;
        profile = (ImageView) nv.getHeaderView(0).findViewById(R.id.profile_picture);
        profile_name = (TextView) nv.getHeaderView(0).findViewById(R.id.profile_username);
        Intent current = this.getIntent();
        b = current.getExtras();
        name = (TextView) findViewById(R.id.clubname);
        name.setText(b.getString("name"));
        count = (TextView) findViewById(R.id.count);
        list = new ArrayList<>();
        cs = new ClubService();
        list = cs.checkIfMember(b.getString("name"));
        list1 = cs.getClubMessages(b.getString("name"));
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        id = sharedPreferences.getString("userid",null).toString();
        imageid = sharedPreferences.getString("imageid",null).toString();
        username = sharedPreferences.getString("username",null).toString();
        profile_name.setText(username);
        Picasso.with(context).load("https://s3-us-west-2.amazonaws.com/item-bucket/" + imageid).into(profile);
        ca = new ChatAdapter(this, list1, id);
        lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(ca);
        if(list1.size() != 0)
            curtime = list1.get(list1.size() - 1).getDate();
        b1 = (Button) findViewById(R.id.join);
        rl = (RelativeLayout) findViewById(R.id.relative);
        send = (Button) findViewById(R.id.send);
        chat = (EditText) findViewById(R.id.chat);
        leave_club = (Button) findViewById(R.id.leave);
        delete_club = (Button) findViewById(R.id.delete);

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        delete_club.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(Html.fromHtml("<font color='#FDD835'>Delete Club</font>"));
                final View customLayout = getLayoutInflater().inflate(R.layout.activity_dialog_view1, null);
                builder.setView(customLayout);
                TextView dialog_message = customLayout.findViewById(R.id.message);
                dialog_message.setText("Do you want to delete the club?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String status = cs.deleteClubs(b.getString("name"));
                        String status1 = cs.deleteClubMembers(b.getString("name"));
                        String status2 = cs.deleteClubMessages(b.getString("name"));
                        if(status.equals("Success") && status1.equals("Success") && status2.equals("Success")){
                            Toast.makeText(getApplicationContext(), "Club delete successful", Toast.LENGTH_SHORT).show();
                            Intent next = new Intent(getApplicationContext(), Clubs.class);
                            startActivity(next);
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Club delete failed", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawableResource(R.color.backgroundPrimary);
                dialog.show();
            }
        });

        leave_club.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(Html.fromHtml("<font color='#FDD835'>Leave Club</font>"));
                final View customLayout = getLayoutInflater().inflate(R.layout.activity_dialog_view1, null);
                builder.setView(customLayout);
                TextView dialog_message = customLayout.findViewById(R.id.message);
                dialog_message.setText("Do you want to leave the club?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String status = cs.leaveClub(id, b.getString("name"));
                        if (status.equals("Success")){
                            Toast.makeText(getApplicationContext(), "Club left successfully", Toast.LENGTH_SHORT).show();
                            Intent next = new Intent(getApplicationContext(), Clubs.class);
                            startActivity(next);
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Club left failed", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawableResource(R.color.backgroundPrimary);
                dialog.show();
            }
        });

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


        if(list.contains(id)){
            b1.setVisibility(View.GONE);
            rl.setVisibility(View.VISIBLE);
            leave_club.setVisibility(View.VISIBLE);
        }

        if(list.get(0).equals(id)){
            leave_club.setVisibility(View.GONE);
            delete_club.setVisibility(View.VISIBLE);
        }

        ClubDetails.AsyncTaskRunner  runner = new ClubDetails.AsyncTaskRunner();
        runner.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

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

        ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b1 = new Bundle();
                b1.putString("clubname", b.getString("name"));
                b1.putString("imageid", b.getString("imageid"));
                Intent next = new Intent(getApplicationContext(), ClubMembersPage.class);
                next.putExtras(b1);
                startActivity(next);
            }
        });

        chat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                return;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int strlen = charSequence.length();
                if(strlen >= chatSize) {
                    count.setText(strlen + "/" + chatSize);
                    Toast.makeText(getApplicationContext(), "Cannot enter more than 300 characters", Toast.LENGTH_SHORT).show();
                }
                else
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
                if(!chat.getText().toString().equals("")) {
                    curtime = getCurrentTime();
                    m = new Message(chat.getText().toString(), id, curtime);
                    cs.insertMessage(m, b.getString("name"));
                    list1.add(m);
                    sendNotification(id, chat.getText().toString());
                    chat.setText("");
                    ca.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please enter a message", Toast.LENGTH_SHORT).show();
                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = cs.insertClubMember(b.getString("name"), id, username);
                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                if(!status.equals("Failed")){
                    Intent next = getIntent();
                    finish();
                    startActivity(next);
                }
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
//        ClubDetails.AsyncTaskRunner runner = new ClubDetails.AsyncTaskRunner();
//        runner.execute();
//    }

    private class AsyncTaskRunner extends AsyncTask<Void, Void, Void> {
        ArrayList<Message> list2;

        @TargetApi(Build.VERSION_CODES.O)
        @Override
        protected Void doInBackground(Void... voids) {
            while (true) {
                list2 = new ArrayList<>();
                try {
                    Thread.sleep(2000);
                    System.out.println("here");
                    if(curtime.contains("T")) {
                        String[] temparr = curtime.split("T");
                        String[] temparr1 = temparr[1].split("\\.");
                        String finaltime = temparr[0] + " " + temparr1[0];
                        list2 = cs.getMessagesAfterTime(finaltime, b.getString("name"));
                    }
                    else
                        list2 = cs.getMessagesAfterTime(curtime, b.getString("name"));
                    if(list2.size() != 0) {
                        curtime = list2.get(list2.size() - 1).getDate();
                        for(int i = 0;i < list2.size();i ++) {
                            Thread.sleep(100);
                            sendNotification(list2.get(i).getUserid(), list2.get(i).getMessage());
                        }
                        System.out.println(list2);
                        list1.addAll(list2);
                        updateListView();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
//            return null;
        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mdl.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendNotification(String id, String message) {
        System.out.println(message);
        String CHANNEL_ID = "my_channel_01";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, "my channel", importance);
        mNotificationManager.createNotificationChannel(mChannel);
        Notification notification = new Notification.Builder(ClubDetails.this, CHANNEL_ID)
                .setContentTitle(id)
                .setContentText(message)
                .setSmallIcon(R.drawable.other)
                .setChannelId(CHANNEL_ID)
                .build();
        mNotificationManager.notify(101 + (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE), notification);
    }
}
