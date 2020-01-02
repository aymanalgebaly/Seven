
package com.compubase.seven.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentsResponse {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("IdPost")
    @Expose
    private String idPost;
    @SerializedName("IdProfile")
    @Expose
    private String idProfile;
    @SerializedName("Comment")
    @Expose
    private String comment;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("datee")
    @Expose
    private String datee;
    @SerializedName("URLProfile")
    @Expose
    private String uRLProfile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    public String getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(String idProfile) {
        this.idProfile = idProfile;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDatee() {
        return datee;
    }

    public void setDatee(String datee) {
        this.datee = datee;
    }

    public String getURLProfile() {
        return uRLProfile;
    }

    public void setURLProfile(String uRLProfile) {
        this.uRLProfile = uRLProfile;
    }

}
