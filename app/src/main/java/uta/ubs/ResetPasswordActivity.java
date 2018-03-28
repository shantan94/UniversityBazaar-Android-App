package uta.ubs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Ann on 3/27/2018.
 */

public class ResetPasswordActivity extends AppCompatActivity {
    RegisterService rs = null;
    static String MyPREFERENCES = "Session Values";
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Button Reset = (Button) findViewById(R.id.Reset);
        final EditText userid = (EditText) findViewById(R.id.userid);
        final EditText CurrentPasswordData = (EditText) findViewById(R.id.CurrentPasswordData);
        final EditText NewPasswordData = (EditText) findViewById(R.id.NewPasswordData);
        final EditText ConfirmPasswordData = (EditText) findViewById(R.id.ConfirmPasswordData);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        rs = new RegisterService();
        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = userid.getText().toString();
                String CurrentPassword = CurrentPasswordData.getText().toString();
                String NewPassword = NewPasswordData.getText().toString();
                String ConfirmPassword = ConfirmPasswordData.getText().toString();
                String status = rs.login(username, CurrentPassword);
                if (status.equals("Passed")) {
                    if (NewPassword.equals(ConfirmPassword)) {
                        String status1 = rs.reset(username,NewPassword);
                        Toast.makeText(getApplicationContext(), "Successfully changed password", Toast.LENGTH_SHORT).show();
                        Intent next = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(next);

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }

                } else
                    Toast.makeText(getApplicationContext(), "Username or password does not exist", Toast.LENGTH_SHORT).show();
            }
        });

    }}