package com.example.meditreat_hospital.Model;

import com.example.meditreat_hospital.PlaceAmbulance;

public class PlaceAmbulanceTo {

    private String Address;
    private String Status;

    public PlaceAmbulanceTo(){

    }
    public PlaceAmbulanceTo(String address) {
        Address = address;
        this.Status = "New Patient";
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
