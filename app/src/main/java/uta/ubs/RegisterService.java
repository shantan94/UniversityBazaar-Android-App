package uta.ubs;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by shantan on 2/12/2018.
 */

public class RegisterService {
    String endpoint = "https://univbazaarservices.herokuapp.com";

    public String addUser(String name, int age, String gender, String email, String password, String userid, String phone, Bitmap image){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        byte[] data1 = outputStream.toByteArray();
        String data = Base64.encodeToString(data1, Base64.DEFAULT);
        try{
            URL url = new URL(endpoint + "/users/register");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type", "application/json");
            JSONObject user = new JSONObject();
            user.put("name", name);
            user.put("age", age);
            user.put("gender", gender);
            user.put("email", email);
            user.put("password", password);
            user.put("userid", userid);
            user.put("phone", phone);
            user.put("image", data);
            OutputStream out = conn.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            writer.write(user.toString());
            writer.close();
            out.close();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JSONObject result = new JSONObject(rd.readLine());
            rd.close();
            conn.disconnect();
            return result.getString("message");
        }
        catch (Exception e){
            Log.d("fdjk",e.getMessage());
        }
        return "Login failed";
    }

    public String login(String username, String password){
        try{
            URL url = new URL(endpoint + "/users/login");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type", "application/json");
            JSONObject user = new JSONObject();
            user.put("userid", username);
            user.put("password", password);
            OutputStream out = conn.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            writer.write(user.toString());
            writer.close();
            out.close();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JSONObject result = new JSONObject(rd.readLine());
            rd.close();
            conn.disconnect();
            JSONObject result1 = (JSONObject) result.getJSONArray("data").get(0);
            return result.getString("message") + " " + result1.getString("name") + " " + result1.getString("imageid");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "Failed";
    }

    public String reset(String username,String NewPassword){
        try{
            URL url = new URL(endpoint + "/users/reset");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type", "application/json");
            JSONObject user = new JSONObject();
            user.put("userid", username);
            user.put("password", NewPassword);
            OutputStream out = conn.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            writer.write(user.toString());
            writer.close();
            out.close();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JSONObject result = new JSONObject(rd.readLine());
            rd.close();
            conn.disconnect();
            return result.getString("message");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "Reset Failed";
    }

}

