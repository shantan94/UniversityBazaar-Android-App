package uta.ubs;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shantan on 2/20/2018.
 */

public class MessageService {
    String endpoint = "https://univbazaarservices.herokuapp.com";

    public void insertMessage(Message m){
        try{
            URL url = new URL(endpoint + "/users/message/insert");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type", "application/json");
            JSONObject user = new JSONObject();
            user.put("userid", m.getUserid());
            user.put("message", m.getMessage());
            user.put("date", m.getDate());
            OutputStream out = conn.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            writer.write(user.toString());
            writer.close();
            out.close();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JSONObject result = new JSONObject(rd.readLine());
            rd.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<Message> getMessages(){
        ArrayList<Message> temp = new ArrayList<>();
        try{
            URL url = new URL(endpoint + "/users/message");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JSONObject result = new JSONObject(rd.readLine());
            JSONArray tempres = result.getJSONArray("data");
            for (int i = 0;i < tempres.length();i++) {
                Message m = new Message(tempres.getJSONObject(i).getString("message"), tempres.getJSONObject(i).getString("userid"), tempres.getJSONObject(i).getString("time"));
                temp.add(m);
            }
            rd.close();
            return temp;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return temp;
    }
}
