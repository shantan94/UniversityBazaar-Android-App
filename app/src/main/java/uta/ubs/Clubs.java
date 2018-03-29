package uta.ubs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by shantan on 2/14/2018.
 */

public class Clubs extends AppCompatActivity {

    Button create;
    ListView lv;
    ArrayList<Club> list = new ArrayList<>();
    ClubService cs;
    ClubAdapter ca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clubs);
        create = (Button) findViewById(R.id.ccbutton);
        lv = (ListView) findViewById(R.id.listView);
        cs = new ClubService();
        list = cs.getClubs();
        ca = new ClubAdapter(this, list);
        lv.setAdapter(ca);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(), CreateClub.class);
                startActivity(next);
            }
        });
    }
}
