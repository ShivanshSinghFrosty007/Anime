package com.example.finalanime.data;

public class Datas {
    String name, season, ep_no, image, id;

    Datas() {
    }

    public Datas(String name, String season, String ep_no, String image, String id) {
        this.name = name;
        this.season = season;
        this.ep_no = ep_no;
        this.image = image;
        this.id = id;
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

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
