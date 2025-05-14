package com.rest.vaadin_private_medical_clinic.domain;

import java.time.LocalDate;
import java.util.List;

public class Patient {

    private long id;
    private String firstname;
    private String lastname;
    private int phoneNumber;
    private String pesel;
    private LocalDate birthDate;
    private long userId;
    private List<Long> appointmentIdList;
    private List<Long> reviewIdList;

    public void setId(long id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setAppointmentIdList(List<Long> appointmentIdList) {
        this.appointmentIdList = appointmentIdList;
    }

    public void setReviewIdList(List<Long> reviewIdList) {
        this.reviewIdList = reviewIdList;
    }

    public long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getPesel() {
        return pesel;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public long getUserId() {
        return userId;
    }

    public List<Long> getAppointmentIdList() {
        return appointmentIdList;
    }

    public List<Long> getReviewIdList() {
        return reviewIdList;
    }
}
