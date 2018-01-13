package com.memeswow.Adapters;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by Prateek on 1/11/2018.
 */

public class PostSkeleton {
    private String id;
    private long timestamp;
    private String posterID;
    private String posterName;
    private int likes;
    private String imgURL;
    private HashMap<String,Comment> comments;

    public String getPosterName() {
        return posterName;
    }

    public void setPosterName(String posterName) {
        this.posterName = posterName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPosterID() {
        return posterID;
    }

    public void setPosterID(String posterID) {
        this.posterID = posterID;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public HashMap<String, Comment> getComments() {
        return comments;
    }

    public void setComments(HashMap<String, Comment> comments) {
        this.comments = comments;
    }

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
{posterName="";
this.timestamp=0;
this.posterID="";
    id="";
    likes=0;
    comments= new HashMap<>();

}}
class Comment{
    private String commentID;
    private String comment,commenterName,commenterUID;

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommenterName() {
        return commenterName;
    }

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }

    public String getCommenterUID() {
        return commenterUID;
    }

    public void setCommenterUID(String commenterUID) {
        this.commenterUID = commenterUID;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    private long timestamp;
    public Comment(){
        comment="";
        commenterName="";
        timestamp=0;
        commenterUID="";
            }

}