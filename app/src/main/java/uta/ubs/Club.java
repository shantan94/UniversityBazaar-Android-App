package uta.ubs;

/**
 * Created by shantan on 3/29/2018.
 */

public class Club {
    private String userid;
    private String name;
    private String description;

    public Club(String userid, String name, String description){
        this.userid = userid;
        this.name = name;
        this.description = description;
    }

    public String getUserid(){
        return userid;
    }

    public void setUserid(String userid){
        this.userid = userid;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }
}
