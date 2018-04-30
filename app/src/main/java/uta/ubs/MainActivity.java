package uta.ubs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.regex.Pattern;

/**
 * Created by shantan on 2/12/2018.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private AdView mAdView;
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
    Button upload_image;
    ImageView image = null;
    ProgressDialog progressDialog;
    Bitmap selected_image;
    Context context;
    String name_val;
    int age_val;
    String gender_val;
    String email_val;
    String password_val;
    String userid_val;
    String phone_val;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.main_page);
        rs = new RegisterService();
        context = this;
        spinner = (Spinner) findViewById(R.id.gender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        findViewById();

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
            }
        });
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
        upload_image = (Button) findViewById(R.id.profile_upload);
        upload_image.setOnClickListener(this);
        image = findViewById(R.id.profile_pic);
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
        else if(view == upload_image){
            Intent getImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(getImage, 0);
        }
    }

    public void setAllValues(){
        name_val = this.name.getText().toString();
        age_val = Integer.parseInt(this.age.getText().toString());
        gender_val = value;
        email_val = this.email.getText().toString();
        password_val = this.password.getText().toString();
        userid_val = this.userid.getText().toString();
        phone_val = this.number.getText().toString();
        progressDialog = ProgressDialog.show(context, "UniversityBazaar", "Posting Data", true, false);
        MainActivity.AsyncTaskRunner ma = new MainActivity.AsyncTaskRunner();
        ma.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Uri targetUri = data.getData();
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                selected_image = bitmap;
                image.setImageBitmap(bitmap);
                image.setVisibility(View.VISIBLE);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.parseColor("#FDD835"));
        value = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.parseColor("#FDD835"));
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected String doInBackground(String... strings) {
            String status = rs.addUser(name_val, age_val, gender_val, email_val ,password_val, userid_val, phone_val, selected_image);
            resp = status;
            return resp;
        }

        @Override
        protected void onPostExecute(String resp){
            progressDialog.dismiss();
            if(resp.equals("Insert Successful")) {
                Toast.makeText(getApplicationContext(), "Registration Complete", Toast.LENGTH_SHORT).show();
                Intent next = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(next);
            }
            else
                Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
        }
    }
}
