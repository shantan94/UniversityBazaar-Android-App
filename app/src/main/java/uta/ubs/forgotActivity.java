package uta.ubs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by shantan on 2/13/2018.
 */

import uta.ubs.ConfigurationFile;


public class forgotActivity extends AppCompatActivity {
    private Button buttonEmailForgot;
    private EditText editTextEmailForgot;
    ProgressDialog progressDialog;
    Context context;
    HttpURLConnection connection;
    private final String base_url = ConfigurationFile.base_url;
    //Preparing Parameters to pass in Async Thread


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        context = this;
        buttonEmailForgot = (Button) findViewById(R.id.buttonEmailForgot);
        editTextEmailForgot = (EditText) findViewById(R.id.editTextEmailForgot);

        buttonEmailForgot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               /* Send Email Request */
                if(!editTextEmailForgot.getText().toString().isEmpty() ){
                    //Setting Progress Dialog
                    progressDialog = ProgressDialog.show(context, "UniversityBazaar", "Checking Credentials", true, false);
                    String email = editTextEmailForgot.getText().toString();
                    String url ="/users/forgot";
                    //Async Runner
                    forgotActivity.AsyncTaskRunner runner = new forgotActivity.AsyncTaskRunner();
                    runner.execute(url, email);
                }else{
                    Toast.makeText(context, "Please fill all the details!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    /* Thread for Server Interation - Pass paramenter and URL */
    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected String doInBackground(String... params) {
            try {
                StringBuffer responseString = null;
                String inputLine;
                URL dataUrl = new URL(base_url + params[0]);
                connection = (HttpURLConnection) dataUrl.openConnection();
                connection.setConnectTimeout(ConfigurationFile.connectionTimeout); //'Connection Timeout' is only called at the beginning to test if the server is up or not.
                connection.setReadTimeout(ConfigurationFile.connectionTimeout); //'Read Timeout' is to test a bad network all along the transfer.
                // optional default is GET
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                JSONObject user = new JSONObject();
                user.put("email", params[1]);
                OutputStream out = connection.getOutputStream();
                Writer writer = new OutputStreamWriter(out, "UTF-8");
                writer.write(user.toString());
                writer.close();
                out.close();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                JSONObject result = new JSONObject(in.readLine());
                resp = result.getString("message");
                return resp;
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            } finally {
                try {
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace(); //If you want further info on failure...
                }
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            progressDialog.dismiss();
            //Toast.makeText(context, "Password Sent to Registered Email-Id.", Toast.LENGTH_SHORT).show();
            if(result.equals("Success")) {
                Toast.makeText(context, "Password sent to registered email", Toast.LENGTH_LONG).show();
                Intent next = new Intent(getApplicationContext(), LoginActivity.class);
            }
            else
                Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        }
    }

}
