package com.example.newcatering.datapackages;

public class Menu {
    String day,afternoon,night;

    public Menu(String day, String afternoon, String night) {
        this.day = day;
        this.afternoon = afternoon;
        this.night = night;
    }

    public Menu() {
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getAfternoon() {
        return afternoon;
    }

    public void setAfternoon(String afternoon) {
        this.afternoon = afternoon;
    }

    public String getNight() {
        return night;
    }

    public void setNight(String night) {
        this.night = night;
    }
}
