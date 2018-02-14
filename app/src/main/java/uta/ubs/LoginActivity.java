package uta.ubs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    RegisterService rs = null;
    static String MyPREFERENCES = "Session Values";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.activity_login);
        Button b1= (Button) findViewById(R.id.button1);
        TextView t1=(TextView) findViewById(R.id.textView3);
        final EditText userid=(EditText) findViewById(R.id.editText3);
        final EditText password=(EditText) findViewById(R.id.editText4);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        rs = new RegisterService();

        t1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses Login button
                Intent i = new Intent(getApplicationContext(),forgotActivity.class);
                startActivity(i);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = userid.getText().toString();
                String pass = password.getText().toString();
                String status = rs.login(username, pass);
                if(status.equals("Passed")){
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userid", userid.getText().toString());
                    editor.commit();
                    Intent next = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(next);
                }
                else
                    Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
            }
        });

    }


}

