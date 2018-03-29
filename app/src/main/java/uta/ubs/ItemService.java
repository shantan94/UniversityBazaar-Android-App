package uta.ubs;

import android.graphics.Bitmap;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by shantan on 2/23/2018.
 */

public class ItemService {
    String endpoint = "https://univbazaarservices.herokuapp.com";

    public String postitem(String id, String name, String desc, Bitmap image, String type, String price){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        byte[] data1 = outputStream.toByteArray();
        String data = Base64.encodeToString(data1, Base64.DEFAULT);
        try{
            URL url = new URL(endpoint + "/users/items");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type", "application/json");
            JSONObject user = new JSONObject();
            user.put("userid", id);
            user.put("itemname", name);
            user.put("description", desc);
            user.put("image", data);
            user.put("type", type);
            user.put("price", price);
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

    public ArrayList<Item> getItems(String type){
        ArrayList<Item> templist = new ArrayList<>();
        try{
            URL url = new URL(endpoint + "/users/getitems");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type", "application/json");
            JSONObject user = new JSONObject();
            user.put("type", type);
            OutputStream out = conn.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            writer.write(user.toString());
            writer.close();
            out.close();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JSONObject result = new JSONObject(rd.readLine());
            JSONArray tempres = result.getJSONArray("data");
            for(int i = 0;i < tempres.length();i++){
                Item it = new Item(tempres.getJSONObject(i).getString("userid"), tempres.getJSONObject(i).getString("itemname"), tempres.getJSONObject(i).getString("description"), tempres.getJSONObject(i).getString("image"), tempres.getJSONObject(i).getString("type"), tempres.getJSONObject(i).getString("price"));
                templist.add(it);
            }
            rd.close();
            return templist;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return templist;
    }

    public ArrayList<Item> getMyItems(String type, String userid){
        ArrayList<Item> templist = new ArrayList<>();
        try{
            URL url = new URL(endpoint + "/users/getmyitems");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type", "application/json");
            JSONObject user = new JSONObject();
            user.put("type", type);
            user.put("userid", userid);
            OutputStream out = conn.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            writer.write(user.toString());
            writer.close();
            out.close();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JSONObject result = new JSONObject(rd.readLine());
            JSONArray tempres = result.getJSONArray("data");
            for(int i = 0;i < tempres.length();i++){
                Item it = new Item(tempres.getJSONObject(i).getString("userid"), tempres.getJSONObject(i).getString("itemname"), tempres.getJSONObject(i).getString("description"), tempres.getJSONObject(i).getString("image"), tempres.getJSONObject(i).getString("type"), tempres.getJSONObject(i).getString("price"));
                templist.add(it);
            }
            rd.close();
            return templist;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return templist;
    }
}
