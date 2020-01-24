package com.example.lab4;

public class Users {

    private String userID;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String displayName;
    /*private String streetAddress;
    private String city;
    private String state;
    private String zip;

    Users(int userID, String username, String password, String firstName, String lastName, String streetAddress, String city, String state, String zip){
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName=  lastName;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zip = zip;

    }

    Users( String firstName, String lastName, String phoneNumber, String streetAddress, String city, String state, String zip){

        this.firstName = firstName;
        this.lastName=  lastName;
        this.streetAddress = streetAddress;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.state = state;
        this.zip = zip;

    }*/

    Users(String userId, String username, String password, String firstName, String lastName, String email, String phoneNumber){

        this.userID = userId;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName=  lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.displayName = firstName + " " + lastName;

    }

    public void setUserID(String userID){this.userID = userID;}
    public void setUsername(String username){this.username = username;}
    public void setPassword(String password){this.password = password;}
    public void setFirstName(String firstName){this.firstName = firstName;}
    public void setLastName(String lastName){this.lastName = lastName;}
    public void setDisplayName(String firstName, String lastName){this.firstName = firstName; this.lastName = lastName;}
    /*public void setStreetAddress(String streetAddress){this.streetAddress = streetAddress;}
    public void setCity(String city){this.city = city;}
    public void setState(String state){this.state = state;}
    public void setZip(String zip){this.zip = zip;}*/

    public String getUserID(){return this.userID;}
    public String getUsername(){return this.username;}
    public String getPassword(){return this.password;}
    public String getFirstName(){return this.firstName;}
    public String getLastName(){return this.lastName;}
    public String getDisplayName(){return this.firstName + " " + this.lastName;}
    /*public String getStreetAddress(){return this.streetAddress;}
    public String getCity(){return this.city;}
    public String getState(){return this.state;}
    public String getZip(){return this.zip;}*/



}
