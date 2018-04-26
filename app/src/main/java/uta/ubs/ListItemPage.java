package uta.ubs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
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

/**
 * Created by shantan on 2/23/2018.
 */

public class ListItemPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private AdView mAdView;

    Button select_image;
    SharedPreferences sharedPreferences;
    ImageView getImage;
    Spinner type;
    String value;
    EditText name;
    EditText price;
    EditText description;
    TextView count;
    Button post;
    String status;
    ItemService is;
    Context context;
    ProgressDialog progressDialog;
    Bitmap selected_image;
    int descSize = 300;
    String id;
    DrawerLayout mdl;
    NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mdl = findViewById(R.id.drawer_layout);
        nv = findViewById(R.id.nav_view);
        is = new ItemService();
        context = this;
        sharedPreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        id = sharedPreferences.getString("userid",null).toString();
        select_image = (Button) findViewById(R.id.select);
        getImage = (ImageView) findViewById(R.id.post_image);
        type = (Spinner) findViewById(R.id.type);
        price = (EditText) findViewById(R.id.price);
        description = (EditText) findViewById(R.id.description);
        count = (TextView) findViewById(R.id.count);
        name = (EditText) findViewById(R.id.item_name);
        post = (Button) findViewById(R.id.post);
        count.setText(0 + "/" + descSize);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type_array, android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);
        type.setOnItemSelectedListener(this);

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
                    case R.id.nav_profile:{
                        mdl.closeDrawers();
                        Intent next = new Intent(getApplicationContext(), ProfilePage.class);
                        startActivity(next);
                        return true;
                    }
                    case R.id.nav_passwordreset:{
                        mdl.closeDrawers();
                        Intent next = new Intent(getApplicationContext(), ResetPasswordActivity.class);
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
                    case R.id.nav_logout:{
                        mdl.closeDrawers();
                        SharedPreferences sp = getBaseContext().getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                        sp.edit().clear().commit();
                        Intent next = new Intent(getApplicationContext(), WelcomeActivity.class);
                        next.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(next);
                    }
                }
                return false;
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().equals("") || description.getText().toString().equals("") || selected_image == null || price.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "All fields are required!", Toast.LENGTH_SHORT).show();
                else{
                    progressDialog = ProgressDialog.show(context, "UniversityBazaar", "Posting Data", true, false);
                    ListItemPage.AsyncTaskRunner lip = new ListItemPage.AsyncTaskRunner();
                    lip.execute();
                }
            }
        });

        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                return;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int strlen = description.getText().toString().length();
                count.setText(strlen + "/" + descSize);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                return;
            }
        });

        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(getImage, 0);
            }
        });
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
                getImage.setImageBitmap(bitmap);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
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

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected String doInBackground(String... strings) {
            status = is.postitem(id, name.getText().toString(), description.getText().toString(), selected_image, value, price.getText().toString());
            resp = status;
            return resp;
        }

        @Override
        protected void onPostExecute(String resp){
            progressDialog.dismiss();
            if(resp.equals("Success")) {
                Toast.makeText(getApplicationContext(), "Item Updated", Toast.LENGTH_SHORT).show();
                if(value.equals("Sell")) {
                    Intent next = new Intent(getApplicationContext(), SellActivity.class);
                    startActivity(next);
                }
                else if(value.equals("Exchange")){
                    Intent next = new Intent(getApplicationContext(), ExchangeActivity.class);
                    startActivity(next);
                }
                else{
                    Intent next = new Intent(getApplicationContext(), LendActivity.class);
                    startActivity(next);
                }
            }
            else
                Toast.makeText(getApplicationContext(), "Failed to update item", Toast.LENGTH_SHORT).show();
        }
    }
}
