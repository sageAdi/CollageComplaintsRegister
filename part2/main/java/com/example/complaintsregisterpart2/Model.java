package com.example.complaintsregisterpart2;

public class Model {
    String roomDetail;
    String complaint;
    String otp;
    String status;
    String email;
    String hostel;
    String date;
    String electrician;

    public Model() {
    }

    public Model(String roomDetail, String complaint, String otp, String status,
                 String email, String hostel, String date, String electrician) {
        this.roomDetail = roomDetail;
        this.complaint = complaint;
        this.otp=otp;
        this.status=status;
        this.email=email;
        this.hostel=hostel;
        this.date=date;
        this.electrician=electrician;
    }

    public String getElectrician() {
        return electrician;
    }

    public void setElectrician(String electrician) {
        this.electrician = electrician;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getRoomDetail() {
        return roomDetail;
    }

    public void setRoomDetail(String roomDetail) {
        this.roomDetail = roomDetail;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

}
