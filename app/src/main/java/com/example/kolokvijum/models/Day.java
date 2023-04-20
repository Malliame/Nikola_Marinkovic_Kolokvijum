package com.example.kolokvijum.models;

import androidx.annotation.Nullable;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class Day{

    private int maxLevel;
    private List<Errand> errandList;
    private LocalDate date;

    public Day(int maxLevel, List<Errand> errandList, LocalDate date) {
        this.maxLevel = maxLevel;
        this.errandList = errandList;
        this.date = date;
    }

    public LocalDate getDate() {

       // if(date.equals(null)) return LocalDate.now();
        return date;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public List<Errand> getErrandList() {
        return errandList;
    }

    public void AddErrand(Errand errand){
        errandList.add(errand);
    }

    public void RemoveErrand(Errand errand){
        errandList.remove(errand);
    }

    @Override
    public boolean equals(@Nullable Object obj) {

        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        Day newDay = (Day) obj;

        return this.maxLevel == newDay.maxLevel &&
                this.errandList.equals(newDay.errandList) &&
                this.date.equals(newDay.date);
    }
}
