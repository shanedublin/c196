package com.wgu.rusd.c196.objects;

import androidx.room.Entity;


@Entity(primaryKeys = {"courseId","mentorId"})
public class CourseMentor {
    public long courseId;
    public long mentorId;

}
