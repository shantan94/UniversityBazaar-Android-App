package uta.ubs;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shantan on 2/14/2018.
 */

public class ProfileService {
    String endpoint = "https://univbazaarservices.herokuapp.com";

    public Map<String,String> getProfile(String userid){
        Map<String,String> hm = new HashMap<>();
        try{
            URL url = new URL(endpoint + "/users/profile");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            Log.d("here",userid);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type", "application/json");
            JSONObject user = new JSONObject();
            user.put("userid", userid);
            OutputStream out = conn.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            writer.write(user.toString());
            writer.close();
            out.close();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JSONObject result = new JSONObject(rd.readLine());
            JSONObject data = new JSONObject(result.getString("data"));
            hm.put("userid", userid);
            hm.put("name", data.getString("name"));
            hm.put("age", data.getString("age"));
            hm.put("gender", data.getString("gender"));
            hm.put("email", data.getString("email"));
            hm.put("phone", data.getString("phone"));
            rd.close();
            conn.disconnect();
            return hm;
        }
        catch (Exception e){
            e.getStackTrace();
            return hm;
        }
    }

    public void saveProfile(String name, String age, String gender, String email, String userid, String number){
        try{
            URL url = new URL(endpoint + "/users/register");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type", "application/json");
            JSONObject user = new JSONObject();
            user.put("userid", userid);
            user.put("name", name);
            user.put("age", age);
            user.put("gender", gender);
            user.put("email", email);
            user.put("userid", userid);
            user.put("phone", number);
            OutputStream out = conn.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            writer.write(user.toString());
            writer.close();
            out.close();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JSONObject result = new JSONObject(rd.readLine());
            Log.d("status", result.toString());
            rd.close();
            conn.disconnect();
        }
        catch (Exception e){
            e.getStackTrace();
        }
    }

}
