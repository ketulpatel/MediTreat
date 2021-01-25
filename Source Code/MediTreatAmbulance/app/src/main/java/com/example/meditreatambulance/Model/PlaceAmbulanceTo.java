package com.example.meditreatambulance.Model;

public class PlaceAmbulanceTo {

    private String Address;
    private String Status;

    public PlaceAmbulanceTo(){

    }
    public PlaceAmbulanceTo(String address) {
        Address = address;
        this.Status = "Accept";
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
