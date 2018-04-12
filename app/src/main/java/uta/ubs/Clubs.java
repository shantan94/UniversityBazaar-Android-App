package uta.ubs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by shantan on 2/14/2018.
 */

public class Clubs extends AppCompatActivity implements AdapterView.OnItemClickListener {

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
        lv.setOnItemClickListener(this);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(), CreateClub.class);
                startActivity(next);
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView description = (TextView) view.findViewById(R.id.description);
        TextView userid = (TextView) view.findViewById(R.id.userid);
        Bundle b = new Bundle();
        b.putString("name", name.getText().toString());
        b.putString("description", description.getText().toString());
        b.putString("userid", userid.getText().toString());
        Intent next = new Intent(getApplicationContext(), ClubDetails.class);
        next.putExtras(b);
        startActivity(next);
    }
}
