package com.example.newcatering.datapackages;

import java.util.List;

public class Attendance {
    String date;
    List<String> attendance;

    public Attendance(String date, List<String> attendance) {
        this.date = date;
        this.attendance = attendance;
    }

    public Attendance() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<String> attendance) {
        this.attendance = attendance;
    }
}
