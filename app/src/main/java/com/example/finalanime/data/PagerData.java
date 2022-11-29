package com.example.finalanime.data;

public class PagerData {

    String name, image, p_id, id, ep_no;

    public PagerData() {
    }

    public PagerData(String name, String image, String p_id, String id, String ep_no) {
        this.name = name;
        this.image = image;
        this.p_id = p_id;
        this.id = id;
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

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
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
}
