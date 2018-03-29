package uta.ubs;

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

/**
 * Created by shantan on 3/28/2018.
 */

public class ClubService {
    String endpoint = "https://univbazaarservices.herokuapp.com";

    public String insertClub(String name, String des, String userid){
        try{
            URL url = new URL(endpoint + "/users/clubs");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type", "application/json");
            JSONObject user = new JSONObject();
            user.put("name", name);
            user.put("description", des);
            user.put("userid", userid);
            OutputStream out = conn.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            writer.write(user.toString());
            writer.close();
            out.close();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JSONObject result = new JSONObject(rd.readLine());
            rd.close();
            return result.getString("message");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "Failed";
    }

    public ArrayList<Club> getClubs(){
        ArrayList<Club> temp = new ArrayList<>();
        try{
            URL url = new URL(endpoint + "/users/getclubs");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type", "application/json");
            OutputStream out = conn.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            writer.close();
            out.close();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JSONObject result = new JSONObject(rd.readLine());
            if(result.has("data")) {
                JSONArray tempres = result.getJSONArray("data");
                for (int i = 0; i < tempres.length(); i++) {
                    Club c = new Club(tempres.getJSONObject(i).getString("founder"), tempres.getJSONObject(i).getString("clubname"), tempres.getJSONObject(i).getString("clubdescription"));
                    temp.add(c);
                }
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
