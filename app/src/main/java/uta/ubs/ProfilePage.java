package uta.ubs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by shantan on 2/14/2018.
 */

public class ProfilePage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    SharedPreferences sharedPreferences;
    ProfileService ps = null;
    EditText name = null;
    EditText age = null;
    Spinner gender = null;
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
    String value = "";
    DrawerLayout mdl;
    NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mdl = findViewById(R.id.drawer_layout);
        nv = findViewById(R.id.nav_view);
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        ps = new ProfileService();
        Map<String,String> hm = ps.getProfile(sharedPreferences.getString("userid",null).toString());
        Log.d("dfs",hm.get("name"));
        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        gender = (Spinner) findViewById(R.id.gender);
        email = (EditText) findViewById(R.id.email);
        userid = (EditText) findViewById(R.id.userid);
        number = (EditText) findViewById(R.id.number);
        final Button edit = (Button) findViewById(R.id.edit);
        final Button save = (Button) findViewById(R.id.save);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(adapter);
        gender.setOnItemSelectedListener(this);
        gender.setEnabled(false);
        name_value = hm.get("name").toString();
        age_value = hm.get("age").toString();
        gender_value = hm.get("gender").toString();
        email_value = hm.get("email").toString();
        userid_value = hm.get("userid").toString();
        number_value = hm.get("phone").toString();
        name.setText(name_value);
        age.setText(age_value);
        int spinnerPosition = adapter.getPosition(gender_value);
        gender.setSelection(spinnerPosition);
        email.setText(email_value);
        userid.setText(userid_value);
        number.setText(number_value);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:{
                        mdl.closeDrawers();
                        Intent next = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(next);
                        return true;
                    }
                    case R.id.nav_marketplace:{
                        mdl.closeDrawers();
                        Intent next = new Intent(getApplicationContext(), MarketPlace.class);
                        startActivity(next);
                        return true;
                    }
                    case R.id.nav_clubpage:{
                        mdl.closeDrawers();
                        Intent next = new Intent(getApplicationContext(), Clubs.class);
                        startActivity(next);
                        return true;
                    }
                    case R.id.nav_uploaditem:{
                        mdl.closeDrawers();
                        Intent next = new Intent(getApplicationContext(), ListItemPage.class);
                        startActivity(next);
                        return true;
                    }
                }
                return false;
            }
        });

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
                if(name_value.equals(name.getText().toString()) && age_value.equals(age.getText().toString()) && gender_value.equals(value) && email_value.equals(email.getText().toString()) && userid_value.equals(userid.getText().toString()) && number_value.equals(number.getText().toString())) {
                    edit.setVisibility(View.VISIBLE);
                    save.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Nothing to save all values are same", Toast.LENGTH_SHORT).show();
                    name.setEnabled(false);
                    age.setEnabled(false);
                    gender.setEnabled(false);
                    email.setEnabled(false);
                    number.setEnabled(false);
                }
                else{
                    if(name.getText().toString().equals("") || age.getText().toString().equals("") || email.getText().toString().equals("") || number.getText().toString().equals(""))
                        Toast.makeText(getApplicationContext(), "All fields are required!", Toast.LENGTH_SHORT).show();
                    else if(!email.getText().toString().endsWith("@mavs.uta.edu"))
                        Toast.makeText(getApplicationContext(), "Email must end with @mavs.uta.edu", Toast.LENGTH_SHORT).show();
                    else if(!Pattern.matches("^[0-9]{10}$",number.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Number must be a number with 10 digits", Toast.LENGTH_SHORT).show();
                    else {
                        Log.d("email", email.getText().toString());
                        ps.saveProfile(name.getText().toString(), age.getText().toString(), value, email.getText().toString(), userid.getText().toString(), number.getText().toString());
                        name_value = name.getText().toString();
                        age_value = age.getText().toString();
                        gender_value = value;
                        email_value = email.getText().toString();
                        userid_value = userid.getText().toString();
                        number_value = number.getText().toString();
                        edit.setVisibility(View.VISIBLE);
                        save.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                        name.setEnabled(false);
                        age.setEnabled(false);
                        gender.setEnabled(false);
                        email.setEnabled(false);
                        number.setEnabled(false);
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mdl.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        value = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        return;
    }
}
