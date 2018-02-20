package uta.ubs;

/**
 * Created by shantan on 2/15/2018.
 */

public class Message {
    private String message;
    private String userid;
    private String date;

    public Message(String message, String userid, String date){
        this.message = message;
        this.userid = userid;
        this.date = date;
    }

    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public String getUserid(){
        return userid;
    }
    public void setUserid(String userid){
        this.userid = userid;
    }
    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date = date;
    }
}
