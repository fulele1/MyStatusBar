package com.xaqb.newexpress.entity;

/**
 * Created by lenovo on 2017/9/8.
 */

public class Message {
    private String meg;
    private String comForm;
    private String date;
    private String title;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComForm() {
        return comForm;
    }

    public void setComForm(String comForm) {
        this.comForm = comForm;
    }

    public String getMeg() {
        return meg;
    }

    public void setMeg(String meg) {
        this.meg = meg;
    }

    private String text;
}
