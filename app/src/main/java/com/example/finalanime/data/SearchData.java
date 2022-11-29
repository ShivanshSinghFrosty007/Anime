package com.example.finalanime.data;

public class SearchData {

    public String name, ep_no, image, desc;

    public SearchData() {
    }

    public SearchData(String name, String ep_no, String image, String desc) {
        this.name = name;
        this.ep_no = ep_no;
        this.image = image;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEp_no() {
        return ep_no;
    }

    public void setEp_no(String ep_no) {
        this.ep_no = ep_no;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
