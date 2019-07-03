package com.example.complaintregister;

public class Model {
    String roomDetail;
    String complaint;
    String email;
    String status;
    String otp;
    String date;
    String hostel;


    public Model() {
    }

    public Model(String roomDetail, String complaint,String status,String email,
                 String electrician,String otp,String date,String hostel) {
        this.roomDetail = roomDetail;
        this.complaint = complaint;
        this.status=status;
        this.email=email;
        this.electrician=electrician;
        this.otp=otp;
        this.date=date;
        this.hostel=hostel;
    }
    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }


    public String getElectrician() {
        return electrician;
    }

    public void setElectrician(String electrician) {
        this.electrician = electrician;
    }

    String electrician;

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
