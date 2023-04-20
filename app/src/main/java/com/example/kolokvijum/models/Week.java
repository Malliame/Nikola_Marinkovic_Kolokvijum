package com.example.kolokvijum.models;

public class Week {

    private int id;
    private Day Monday;
    private Day Tuesday;
    private Day Wednesday;
    private Day Thurstday;
    private Day Friday;
    private Day Saturday;
    private Day Sunday;

    public Week(int id, Day monday, Day tuesday, Day wednesday, Day thurstday, Day friday, Day saturday, Day sunday) {
        this.id = id;
        this.Monday = monday;
        this.Tuesday = tuesday;
        this.Wednesday = wednesday;
        this.Thurstday = thurstday;
        this.Friday = friday;
        this.Saturday = saturday;
        this.Sunday = sunday;
    }

    public int getId() {
        return id;
    }

    public Day getMonday() {
        return Monday;
    }

    public Day getTuesday() {
        return Tuesday;
    }

    public Day getWednesday() {
        return Wednesday;
    }

    public Day getThurstday() {
        return Thurstday;
    }

    public Day getFriday() {
        return Friday;
    }

    public Day getSaturday() {
        return Saturday;
    }

    public Day getSunday() {
        return Sunday;
    }
}
