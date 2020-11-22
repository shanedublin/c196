package com.wgu.rusd.c196.course;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Course implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public Long courseId;
    public Long termId;
    public Long mentorId;

    public String title;

    public LocalDate endDate;
    public LocalDate startDate;
    public String status;

    public String notes;


    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", termId=" + termId +
                ", mentorId=" + mentorId +
                ", title='" + title + '\'' +
                ", endDate=" + endDate +
                ", startDate=" + startDate +
                ", status='" + status + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
