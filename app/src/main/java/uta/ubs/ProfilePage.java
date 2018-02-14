package uta.ubs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shantan on 2/14/2018.
 */

public class ProfilePage extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    ProfileService ps = null;
    EditText name = null;
    EditText age = null;
    EditText gender = null;
    EditText email = null;
    EditText userid = null;
    EditText number = null;
    Button edit = null;
    Button save = null;
    String name_value = "";
    String age_value = "";
    String gender_value = "";
    String email_value = "";
    String userid_value = "";
    String number_value = "";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.activity_profile);
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        ps = new ProfileService();
        Map<String,String> hm = ps.getProfile(sharedPreferences.getString("userid",null).toString());
        Log.d("dfs",hm.get("name"));
        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        gender = (EditText) findViewById(R.id.gender);
        email = (EditText) findViewById(R.id.email);
        userid = (EditText) findViewById(R.id.userid);
        number = (EditText) findViewById(R.id.number);
        final Button edit = (Button) findViewById(R.id.edit);
        final Button save = (Button) findViewById(R.id.save);
        name_value = hm.get("name").toString();
        age_value = hm.get("age").toString();
        gender_value = hm.get("gender").toString();
        email_value = hm.get("email").toString();
        userid_value = hm.get("userid").toString();
        number_value = hm.get("phone").toString();
        name.setText(name_value);
        age.setText(age_value);
        gender.setText(gender_value);
        email.setText(email_value);
        userid.setText(userid_value);
        number.setText(number_value);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setEnabled(true);
                age.setEnabled(true);
                gender.setEnabled(true);
                email.setEnabled(true);
                number.setEnabled(true);
                save.setVisibility(View.VISIBLE);
                edit.setVisibility(View.GONE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setEnabled(false);
                age.setEnabled(false);
                gender.setEnabled(false);
                email.setEnabled(false);
                number.setEnabled(false);
                if(name_value.equals(name.getText().toString()) && age_value.equals(age.getText().toString()) && gender_value.equals(gender.getText().toString()) && email_value.equals(email.getText().toString()) && userid_value.equals(userid.getText().toString()) && number_value.equals(number.getText().toString())) {
                    edit.setVisibility(View.VISIBLE);
                    save.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Nothing to save all values are same", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.d("email", email.getText().toString());
                    ps.saveProfile(name.getText().toString(), age.getText().toString(), gender.getText().toString(), email.getText().toString(), userid.getText().toString(), number.getText().toString());
                    name_value = name.getText().toString();
                    age_value = age.getText().toString();
                    gender_value = gender.getText().toString();
                    email_value = email.getText().toString();
                    userid_value = userid.getText().toString();
                    number_value = number.getText().toString();
                    edit.setVisibility(View.VISIBLE);
                    save.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
