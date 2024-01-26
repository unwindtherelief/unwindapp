package com.depression.relief.depressionissues.authentication;

public class UserModel {
    private String firstname;
    private String lastname;
    private String email;
    private String completeNumber;
    private String gender;
    private String dateOfBirth;

    // Default constructor (required for Firestore)
    public UserModel() {
    }

    public UserModel(String firstname, String lastname, String email, String completeNumber, String gender, String dateOfBirth) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.completeNumber = completeNumber;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return completeNumber;
    }

    public void setMobile(String completeNumber) {
        this.completeNumber = completeNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}