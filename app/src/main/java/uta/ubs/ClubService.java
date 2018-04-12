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
import java.util.List;

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

    public void insertMessage(Message m, String clubname){
        try{
            URL url = new URL(endpoint + "/users/clubmessage/insert");
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
            user.put("clubname", clubname);
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

    public ArrayList<Message> getClubMessages(String clubname){
        ArrayList<Message> temp = new ArrayList<>();
        System.out.println(clubname);
        try{
            URL url = new URL(endpoint + "/users/clubmessage");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type", "application/json");
            JSONObject user = new JSONObject();
            user.put("clubname", clubname);
            OutputStream out = conn.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            writer.write(user.toString());
            writer.close();
            out.close();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JSONObject result = new JSONObject(rd.readLine());
            JSONArray tempres = result.getJSONArray("data");
            for (int i = 0;i < tempres.length();i++) {
                Message m = new Message(tempres.getJSONObject(i).getString("message"), tempres.getJSONObject(i).getString("userid"), tempres.getJSONObject(i).getString("date"));
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

    public String insertClubMember(String clubname, String member){
        try{
            URL url = new URL(endpoint + "/users/insertclubmembers");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type", "application/json");
            JSONObject user = new JSONObject();
            user.put("clubname", clubname);
            user.put("member", member);
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

    public ArrayList<String> checkIfMember(String clubname){
        ArrayList<String> temp = new ArrayList<>();
        try{
            URL url = new URL(endpoint + "/users/checkmember");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type", "application/json");
            JSONObject user = new JSONObject();
            user.put("clubname", clubname);
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
                    temp.add(tempres.getJSONObject(i).getString("clubmember"));
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

    public ArrayList<Message> getMessagesAfterTime(String time, String clubname){
        ArrayList<Message> temp = new ArrayList<>();
        try{
            URL url = new URL(endpoint + "/users/clubmessage/instant");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type", "application/json");
            JSONObject user = new JSONObject();
            user.put("date", time);
            user.put("clubname", clubname);
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
                    Message m = new Message(tempres.getJSONObject(i).getString("message"), tempres.getJSONObject(i).getString("userid"), tempres.getJSONObject(i).getString("time"));
                    temp.add(m);
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
