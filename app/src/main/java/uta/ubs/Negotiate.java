package uta.ubs;

/**
 * Created by shantan on 3/28/2018.
 */

public class Negotiate {
    private String message;
    private String fromid;
    private String toid;
    private String date;
    private String imageid;

    public Negotiate(String message, String fromid, String toid, String date, String imageid){
        this.message = message;
        this.fromid = fromid;
        this.toid = toid;
        this.date = date;
        this.imageid = imageid;
    }

    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }

    public String getFromid(){
        return fromid;
    }

    public void setFromid(String fromid){
        this.fromid = fromid;
    }

    public String getToid(){
        return toid;
    }

    public void setToid(String toid){
        this.toid = toid;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getImageid(){
        return imageid;
    }

    public void setImageid(String imageid){
        this.imageid = imageid;
    }
}
