package uta.ubs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by shantan on 3/28/2018.
 */

public class CreateClub extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String id;
    Button send;
    EditText cname;
    EditText cdes;
    ClubService cs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_club);
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        id = sharedPreferences.getString("userid",null).toString();
        send = (Button) findViewById(R.id.Csubmit);
        cname = (EditText) findViewById(R.id.Cname);
        cdes = (EditText) findViewById(R.id.Cdes);
        cs = new ClubService();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = cname.getText().toString();
                String des = cdes.getText().toString();
                String status = cs.insertClub(name, des, id);
                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                Intent next = new Intent(getApplicationContext(), Clubs.class);
                startActivity(next);
            }
        });
    }
}
