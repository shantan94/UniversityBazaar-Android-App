package uta.ubs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.negotiate_chat);
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
            @Override
            public void onClick(View view) {
                curtime = getCurrentTime();
                n = new Negotiate(chat.getText().toString(), b.getString("curuser"), b.getString("nextuser"), curtime, b.getString("imageid"));
                ns.insertMessage(n);
                list.add(n);
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
}
