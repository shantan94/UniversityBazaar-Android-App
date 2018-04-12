package uta.ubs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_details);
        Intent current = this.getIntent();
        b = current.getExtras();
        name = (TextView) findViewById(R.id.clubname);
        name.setText(b.getString("name"));
        list = new ArrayList<>();
        cs = new ClubService();
        list = cs.checkIfMember(b.getString("name"));
        b1 = (Button) findViewById(R.id.join);
        rl = (RelativeLayout) findViewById(R.id.relative);
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        id = sharedPreferences.getString("userid",null).toString();
        if(list.contains(id)){
            b1.setVisibility(View.GONE);
            rl.setVisibility(View.VISIBLE);
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = cs.insertClubMember(b.getString("name"), id);
                System.out.println(status);
                Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT);
                if(!status.equals("Failed")){
                    Intent next = getIntent();
                    finish();
                    startActivity(next);
                }
            }
        });
    }
}
