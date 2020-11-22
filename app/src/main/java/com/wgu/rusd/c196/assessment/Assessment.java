package com.wgu.rusd.c196.assessment;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Assessment implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public long assessmentId;

    public long courseId;

    public String name;
    public String status;
    public String type;

    public LocalDate goalDate;


    @Override
    public String toString() {
        return "Assessment{" +
                "assessmentId=" + assessmentId +
                ", courseId=" + courseId +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                "} \n";
    }
}
