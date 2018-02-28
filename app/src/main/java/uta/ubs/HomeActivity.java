package uta.ubs;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ann on 2/13/2018.
 */

public class HomeActivity extends AppCompatActivity {
    Button send;
    Button button2;
    Button button3;
    Button button4;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        lv = (ListView) findViewById(R.id.textView2);
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        id = sharedPreferences.getString("userid",null).toString();
        ms = new MessageService();
        templist = ms.getMessages();
        list = new ArrayList<>(templist);
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
        runner.execute();

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
            @Override
            public void onClick(View view) {
                curtime = getCurrentTime();
                m = new Message(chat.getText().toString(), id, curtime);
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


        button4 = (Button) findViewById(R.id.button4);

        // Capture button clicks
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(HomeActivity.this,
                        ProfilePage.class);
                startActivity(myIntent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        done = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        done = true;
        HomeActivity.AsyncTaskRunner runner = new HomeActivity.AsyncTaskRunner();
        runner.execute();
    }

    private class AsyncTaskRunner extends AsyncTask<Void, Void, Void> {
        ArrayList<Message> list1;

        @Override
        protected Void doInBackground(Void... voids) {
            while (done) {
                list1 = new ArrayList<>();
                try {
                    Log.d("here", "in here");
                    System.out.println("in here");
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
                        list.addAll(list1);
                        updateListView();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
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
}