package uta.ubs;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
/**
 * Created by Ann on 2/13/2018.
 */

public class HomeActivity extends AppCompatActivity {
    Button button2;
    Button button3;
    Button button4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        button2 = (Button) findViewById(R.id.button2);

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


}