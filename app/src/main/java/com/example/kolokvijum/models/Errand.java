package com.example.kolokvijum.models;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public class Errand {


    private int id;
    private int level;
    private String title;
    private String descr;
    private LocalDateTime dayStart;
    private LocalDateTime dayEnd;

    public Errand(int id, int level, String title, String descr, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.level = level;
        this.title = title;
        this.descr = descr;
        this.dayStart = start;
        this.dayEnd = end;
    }

    public int getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public String getTitle() {
        return title;
    }

    public String getDescr() {
        return descr;
    }



    public LocalDateTime getDayStart() {
        return dayStart;
    }

    public LocalDateTime getDayEnd() {
        return dayEnd;
    }

    @Override
    public boolean equals(@Nullable Object obj) {

        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        Errand errand = (Errand) obj;

        return  this.id == errand.id &&
                this.level == errand.level &&
                this.title.equals(errand.title) &&
                this.descr.equals(errand.descr) &&
                this.dayEnd.equals(errand.dayEnd) &&
                this.dayStart.equals(errand.dayStart);
    }
}
