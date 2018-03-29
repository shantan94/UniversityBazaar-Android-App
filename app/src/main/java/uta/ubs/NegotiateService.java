package uta.ubs;

import android.widget.ArrayAdapter;

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

public class NegotiateService {
    String endpoint = "https://univbazaarservices.herokuapp.com";

    public void insertMessage(Negotiate n){
        try{
            URL url = new URL(endpoint + "/users/setnegotiate");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type", "application/json");
            JSONObject user = new JSONObject();
            user.put("from", n.getFromid());
            user.put("to", n.getToid());
            user.put("message", n.getMessage());
            user.put("date", n.getDate());
            user.put("imageid", n.getImageid());
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

    public ArrayList<Negotiate> getNegotiations(String from, String to, String imageid){
        ArrayList<Negotiate> temp = new ArrayList<>();
        try{
            URL url = new URL(endpoint + "/users/getnegotiate");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type", "application/json");
            JSONObject user = new JSONObject();
            user.put("from", from);
            user.put("to", to);
            user.put("imageid", imageid);
            OutputStream out = conn.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            writer.write(user.toString());
            writer.close();
            out.close();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JSONObject result = new JSONObject(rd.readLine());
            if(result.has("data")) {
                JSONArray tempres = result.getJSONArray("data");
                for (int i = 0; i < tempres.length(); i++) {
                    Negotiate n = new Negotiate(tempres.getJSONObject(i).getString("message"), tempres.getJSONObject(i).getString("from"), tempres.getJSONObject(i).getString("to"), tempres.getJSONObject(i).getString("time"), tempres.getJSONObject(i).getString("imageid"));
                    temp.add(n);
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

    public ArrayList<MyNegotiations> getMyNegotiations(String imageid, String userid){
        ArrayList<MyNegotiations> temp = new ArrayList<>();
        try{
            URL url = new URL(endpoint + "/users/getmyusernegotiate");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type", "application/json");
            JSONObject user = new JSONObject();
            user.put("userid", userid);
            user.put("imageid", imageid);
            OutputStream out = conn.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            writer.write(user.toString());
            writer.close();
            out.close();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JSONObject result = new JSONObject(rd.readLine());
            if(result.has("data")) {
                JSONArray tempres = result.getJSONArray("data");
                for (int i = 0; i < tempres.length(); i++) {
                    MyNegotiations mn = new MyNegotiations("", tempres.getJSONObject(i).getString("from"));
                    temp.add(mn);
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
