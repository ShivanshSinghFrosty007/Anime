package com.example.finalanime.data;

public class DatasMp {

    String name, image, rating, id, ep_no;

    public DatasMp() {
    }

    public DatasMp(String name, String image, String rating, String id, String ep_no) {
        this.name = name;
        this.image = image;
        this.rating = rating;
        this.id = id;
        this.ep_no = ep_no;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEp_no() {
        return ep_no;
    }

    public void setEp_no(String ep_no) {
        this.ep_no = ep_no;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
