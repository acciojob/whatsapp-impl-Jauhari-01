package com.driver;

public class Group {
    private String name;
    private int numberOfParticipants;

    //constructor
    public Group(){

    }
    public Group(String name,int num){
        this.name = name;
        this.numberOfParticipants = num;
    }

    //Getters and Setters
    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getNumberOfParticipants(){
        return this.numberOfParticipants;
    }
    public void setNumberOfParticipants(int num){
        this.numberOfParticipants = num;
    }
}
