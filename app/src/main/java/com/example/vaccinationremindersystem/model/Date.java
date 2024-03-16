package com.example.vaccinationremindersystem.model;

public class Date {
    private String NextDateOfVisit;

    public String getNextDateOfVisit() {
        return NextDateOfVisit;
    }

    public void setNextDateOfVisit(String nextDateOfVisit) {
        NextDateOfVisit = nextDateOfVisit;
    }

    public Date(String nextDateOfVisit) {
        NextDateOfVisit = nextDateOfVisit;

    }
}
