package com.example.vaccinationremindersystem.model;

import java.io.Serializable;
public class Profile implements Serializable{
    String parentName;
    String email;
    String phone;
    String location;
    String childName;
    String childDOB;

    public Profile() {

    }

    public Profile(String parentName, String email, String phone, String location, String childName, String childDOB) {
        this.parentName = parentName;
        this.email = email;
        this.phone = phone;
        this.location = location;
        this.childName = childName;
        this.childDOB = childDOB;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getChildDOB() {
        return childDOB;
    }

    public void setChildDOB(String childDOB) {
        this.childDOB = childDOB;
    }
}
