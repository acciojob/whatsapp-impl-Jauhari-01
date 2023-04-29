package com.driver;

import java.util.Date;

import javax.xml.transform.Templates;

public class Message {
    private int id;
    private String content;
    private Date timestamp;

    //constructor
    public Message(){

    }
    public Message(int id,String content){
        this.id = id;
        this.content = content;
    }

    //getters and setters
    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getContent(){
        return this.content;
    }
    public void setContent(String content){
        this.content = content;
    }
    public Date getTimestamp(){
        return this.timestamp;
    }
    public void setTimestamp(Date timestamp){
        this.timestamp = timestamp;
    }
}
