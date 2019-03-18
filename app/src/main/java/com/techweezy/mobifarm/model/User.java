package com.techweezy.mobifarm.model;

public class User {
    public String id, title, fame, sname,emailAddress,
            streetAddress,phoneNumber,password,user_role;


    public User(String id, String title, String fame,
                String sname, String emailAddress,
                String streetAddress, String phoneNumber,
                String password, String user_role) {
        this.id = id;
        this.title = title;
        this.fame = fame;
        this.sname = sname;
        this.emailAddress = emailAddress;
        this.streetAddress = streetAddress;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.user_role = user_role;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getFame() {
        return fame;
    }

    public String getSname() {
        return sname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }
}
