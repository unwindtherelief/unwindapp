package com.depression.relief.depressionissues.authentication;

public class UserModel {
    private String firstname;
    private String email;
    private String completeNumber;
    private String gender;
    private String dateOfBirth;
    private String selectedImageUrl;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String userId;

    // Default constructor (required for Firestore)
    public UserModel(String displayName, String email, String userId){
        this.firstname = displayName;
        this.email = email;
        this.userId = userId;
    }

    public UserModel(String firstname, String email, String completeNumber, String gender, String dateOfBirth, String selectedImageUrl,String userId) {
        this.firstname = firstname;
        this.email = email;
        this.completeNumber = completeNumber;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.selectedImageUrl = selectedImageUrl;
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
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

    public String getCompleteNumber() {
        return completeNumber;
    }

    public void setCompleteNumber(String completeNumber) {
        this.completeNumber = completeNumber;
    }

    public String getSelectedImageUrl() {
        return selectedImageUrl;
    }

    public void setSelectedImageUrl(String selectedImageUrl) {
        this.selectedImageUrl = selectedImageUrl;
    }
}