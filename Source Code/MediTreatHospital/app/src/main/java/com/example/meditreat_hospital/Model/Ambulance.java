package com.example.meditreat_hospital.Model;

public class Ambulance {

    private String Name;
    private String Image;
    private String MenuID;


    public Ambulance(){

    }

    public Ambulance(String name, String image, String menuID) {
        Name = name;
        Image = image;
        MenuID = menuID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getMenuID() {
        return MenuID;
    }

    public void setMenuID(String menuID) {
        MenuID = menuID;
    }
}
