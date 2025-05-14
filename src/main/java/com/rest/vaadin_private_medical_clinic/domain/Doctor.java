package com.rest.vaadin_private_medical_clinic.domain;

import java.util.List;

public class Doctor {

    private long id;
    private String firstname;
    private String lastname;
    private String specialization;
    private double rating;
    private long userId;
    private List<Long> appointmentIdList;
    private List<Long> reviewIdList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<Long> getAppointmentIdList() {
        return appointmentIdList;
    }

    public void setAppointmentIdList(List<Long> appointmentIdList) {
        this.appointmentIdList = appointmentIdList;
    }

    public List<Long> getReviewIdList() {
        return reviewIdList;
    }

    public void setReviewIdList(List<Long> reviewIdList) {
        this.reviewIdList = reviewIdList;
    }
}
