package com.example.meditreat_hospital.Model;

public class Category {

    private String Name ;

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

    public Category(String name, String image) {
        Name = name;
        Image = image;
    }

    private String Image ;

    public Category(){

    }


}
