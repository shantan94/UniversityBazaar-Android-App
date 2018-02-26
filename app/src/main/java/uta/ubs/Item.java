package uta.ubs;

/**
 * Created by shantan on 2/25/2018.
 */

public class Item {
    private String userid;
    private String itemname;
    private String description;
    private String image;
    private String type;
    private String price;

    public Item(String userid, String itemname, String description, String image, String type, String price){
        this.userid = userid;
        this.itemname = itemname;
        this.description = description;
        this.image = image;
        this.type = type;
        this.price = price;
    }

    public String getUserid(){
        return userid;
    }
    public void setUserid(String userid){
        this.userid = userid;
    }
    public String getItemname(){
        return itemname;
    }
    public void setItemname(String itemname){
        this.itemname = itemname;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getImage(){
        return image;
    }
    public void setImage(String image){
        this.image = image;
    }
    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getPrice(){
        return price;
    }
    public void setPrice(String price){
        this.price = price;
    }
}
