package com.memeswow.Adapters;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by Prateek on 1/11/2018.
 */

public class PostSkeleton {
    private String id;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    private long timestamp;
    public String getPosterID() {
        return posterID;
    }

    public void setPosterID(String posterID) {
        this.posterID = posterID;
    }

    private String posterID;
    private int likes;

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    private String imgURL;
    public HashMap<String, Comment> getComments() {
        return comments;
    }

    public void setComments(HashMap<String, Comment> comments) {
        this.comments = comments;
    }

    private HashMap<String,Comment> comments;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

public PostSkeleton()
{
    id="";
    likes=0;
    comments= new HashMap<>();

}}
class Comment{
    private String comment,commenterName,commenterUID;
    private long timestamp;
    public Comment(){
        comment="";
        commenterName="";
        timestamp=0;
        commenterUID="";
            }

}