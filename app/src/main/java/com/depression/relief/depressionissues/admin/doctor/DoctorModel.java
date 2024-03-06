package com.depression.relief.depressionissues.admin.doctor;

public class DoctorModel {

    private String doctorId;
    private String doctorEmail;
    private String doctorPassword;
    private String documentId;  // New field to store document ID


    public DoctorModel() {
    }

    public DoctorModel(String doctorId, String doctorEmail, String doctorPassword) {
        this.doctorId = doctorId;
        this.doctorEmail = doctorEmail;
        this.doctorPassword = doctorPassword;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public String getDoctorPassword() {
        return doctorPassword;
    }

    public void setDoctorPassword(String doctorPassword) {
        this.doctorPassword = doctorPassword;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}
