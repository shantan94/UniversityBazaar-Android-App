package uta.ubs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.*;
import android.app.ActionBar;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

       /* ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);//hide title
        actionBar.setDisplayShowHomeEnabled(false);//hide icon
*/
        Button button = (Button) findViewById(R.id.Login);
        Button button1 = (Button) findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses Login button
                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
                //setContentView(R.layout.activity_login);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(next);
            }
        });

    }
}
