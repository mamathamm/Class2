package project.dublin.com.dublin.Pojo;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;


@IgnoreExtraProperties
public class FavouriteTrip implements Serializable {
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public FavouriteTrip(String userid, String fromlocation, String tolocation, String comments,ArrayList<String> imagelist,String date) {
        this.fromlocation = fromlocation;
        this.tolocation = tolocation;
        this.comments = comments;
        this.userid=userid;
        this.imagelist=imagelist;
        this.date=date;
    }
    public FavouriteTrip(String userid, String fromlocation, String tolocation, String comments,String images) {
        this.fromlocation = fromlocation;
        this.tolocation = tolocation;
        this.comments = comments;
        this.userid=userid;
        this.images=images;

    }
    public FavouriteTrip(){

    }

    public String getFromlocation() {
        return fromlocation;
    }

    public void setFromlocation(String fromlocation) {
        this.fromlocation = fromlocation;
    }

    public String getTolocation() {
        return tolocation;
    }

    public void setTolocation(String tolocation) {
        this.tolocation = tolocation;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    private String fromlocation;
    private String tolocation;
    private String comments;
    private String userid;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;


    public ArrayList<String> getImage() {
        return imagelist;
    }

    public void setImage(ArrayList<String> imagelist) {
        this.imagelist = imagelist;
    }

    private ArrayList<String> imagelist;

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    private String images;

}
