package com.wgu.rusd.c196.objects;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.wgu.rusd.c196.course.Course;
import com.wgu.rusd.c196.mentor.Mentor;

import java.util.List;

public class MentorWithCourses {

    @Embedded public Mentor mentor;

    @Relation(
            parentColumn = "mentorId",
            entityColumn = "courseId"
    )
    public List<Course> courses;
}
