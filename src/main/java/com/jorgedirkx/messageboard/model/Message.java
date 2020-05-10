package com.jorgedirkx.messageboard.model;

import java.util.Date;

public class Message {


    private int Id;

    private Date date;

    private String name;

    private String message;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "date=" + date +
                ", name='" + name + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
