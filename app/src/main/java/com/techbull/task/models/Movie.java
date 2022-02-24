package com.techbull.task.models;

public class Movie {
    String Name,Year,Image;
    public Movie(String Name,String Year,String Image){
        this.Name=Name;
        this.Year=Year;
        this.Image=Image;
    };

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
