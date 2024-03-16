package com.example.vaccinationremindersystem;

public class User {
    String username;
    String email;
    String password;
    String role;
    String uid;
    Long time;
    String message;
    String title;

    public User() {
    }

    public User(String username, String email, String password, String role , String uid,Long time, String title,String message) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.time = time;
        this.uid = uid;
        this.title = title;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
