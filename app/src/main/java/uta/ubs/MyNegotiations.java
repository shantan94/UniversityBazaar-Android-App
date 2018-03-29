package uta.ubs;

/**
 * Created by shantan on 3/29/2018.
 */

public class MyNegotiations {
    private String message;
    private String userid;

    public MyNegotiations(String message, String userid){
        this.message = message;
        this.userid = userid;
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
}
