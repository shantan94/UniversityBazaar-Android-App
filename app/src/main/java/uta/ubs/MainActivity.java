package uta.ubs;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shantan on 2/12/2018.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private Button register = null;
    private EditText name = null;
    private EditText age = null;
    private EditText email = null;
    private EditText password = null;
    private EditText confimpassword = null;
    private EditText userid = null;
    private EditText number = null;
    private Spinner spinner = null;
    private String value = "";
    RegisterService rs = null;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.main_page);
        rs = new RegisterService();
        spinner = (Spinner) findViewById(R.id.gender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        findViewById();
    }

    public void findViewById(){
        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confimpassword = (EditText) findViewById(R.id.confirmpassword);
        userid = (EditText) findViewById(R.id.userid);
        number = (EditText) findViewById(R.id.number);
        register = (Button) findViewById(R.id.submit);
        register.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View view){
        if(view == register){
            if(name.getText().toString().equals("") || age.getText().toString().equals("") || email.getText().toString().equals("") || password.getText().toString().equals("") || confimpassword.getText().toString().equals("") || userid.getText().toString().equals("") || number.getText().toString().equals(""))
                Toast.makeText(getApplicationContext(), "All fields are required!", Toast.LENGTH_SHORT).show();
            else if(!email.getText().toString().endsWith("@mavs.uta.edu"))
                Toast.makeText(getApplicationContext(), "Email must end with @mavs.uta.edu", Toast.LENGTH_SHORT).show();
            else if(!Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,16}",password.getText().toString()))
                Toast.makeText(getApplicationContext(), "Password must contain one upper case, one lower case, one number and one special character", Toast.LENGTH_SHORT).show();
            else if(!password.getText().toString().equals(confimpassword.getText().toString()))
                Toast.makeText(getApplicationContext(), "Password and Confirm Password must match", Toast.LENGTH_SHORT).show();
            else if(!Pattern.matches("^[0-9]{10}$",userid.getText().toString()))
                Toast.makeText(getApplicationContext(), "Userid must be a number with 10 digits", Toast.LENGTH_SHORT).show();
            else if(!Pattern.matches("^[0-9]{10}$",number.getText().toString()))
                Toast.makeText(getApplicationContext(), "Number must be a number with 10 digits", Toast.LENGTH_SHORT).show();
            else
                setAllValues();
        }
    }

    public void setAllValues(){
        String name = this.name.getText().toString();
        int age = Integer.parseInt(this.age.getText().toString());
        String gender = value;
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();
        String userid = this.userid.getText().toString();
        String phone = this.number.getText().toString();
        String status = rs.addUser(name, age, gender, email ,password, userid, phone);
        if(status.equals("Insert Successful")) {
            Toast.makeText(getApplicationContext(), "Registration Complete", Toast.LENGTH_SHORT).show();
            Intent next = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(next);
        }
        else
            Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
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
