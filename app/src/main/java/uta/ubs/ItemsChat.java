package uta.ubs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by shantan on 3/29/2018.
 */

public class ItemsChat extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ArrayList<MyNegotiations> list;
    ListView lv;
    NegotiateService ns;
    MyNegotiationsAdapter mna;
    Bundle b;
    String id;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_chat);
        Intent current = this.getIntent();
        b = current.getExtras();
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        id = sharedPreferences.getString("userid",null).toString();
        lv = (ListView) findViewById(R.id.listView);
        list = new ArrayList<>();
        ns = new NegotiateService();
        list = ns.getMyNegotiations(b.getString("imageid"), id);
        mna = new MyNegotiationsAdapter(this, list);
        lv.setAdapter(mna);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id1) {
        Bundle b1 = new Bundle();
        TextView userid = (TextView) view.findViewById(R.id.userid);
        b1.putString("curuser", id);
        b1.putString("nextuser", userid.getText().toString());
        b1.putString("imageid", b.getString("imageid"));
        Intent next = new Intent(getApplicationContext(), NegotiateChat.class);
        next.putExtras(b1);
        startActivity(next);
    }
}
