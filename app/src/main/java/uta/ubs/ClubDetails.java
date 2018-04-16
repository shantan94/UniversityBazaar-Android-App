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
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by harsh on 3/29/2018.
 */

public class ClubDetails extends AppCompatActivity {

    Bundle b;
    TextView name;
    ArrayList<String> list;
    ClubService cs;
    Button b1;
    SharedPreferences sharedPreferences;
    RelativeLayout rl;
    String id;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.activity_club_details);
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
        ca = new ChatAdapter(this, list1, id);
        lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(ca);
        if(list1.size() != 0)
            curtime = list1.get(list1.size() - 1).getDate();
        b1 = (Button) findViewById(R.id.join);
        rl = (RelativeLayout) findViewById(R.id.relative);
        send = (Button) findViewById(R.id.send);
        chat = (EditText) findViewById(R.id.chat);
        if(list.contains(id)){
            b1.setVisibility(View.GONE);
            rl.setVisibility(View.VISIBLE);
        }
        ClubDetails.AsyncTaskRunner  runner = new ClubDetails.AsyncTaskRunner();
        runner.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

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
                String status = cs.insertClubMember(b.getString("name"), id);
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
