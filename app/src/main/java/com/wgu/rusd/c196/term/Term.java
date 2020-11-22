package com.wgu.rusd.c196.term;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Term implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public Long termId;
    public String title;
    public LocalDate start;
    public LocalDate end;

    @Override
    public String toString() {
        return title;
    }
}
