package com.codingtive.consumer.model;

public class Sport {
    private String strSport;
    private String strSportThumb;

    public Sport(String title, String image) {
        strSport = title;
        strSportThumb = image;
    }

    public String getStrSport() {
        return strSport;
    }

    public void setStrSport(String strSport) {
        this.strSport = strSport;
    }

    public String getStrSportThumb() {
        return strSportThumb;
    }

    public void setStrSportThumb(String strSportThumb) {
        this.strSportThumb = strSportThumb;
    }
}
