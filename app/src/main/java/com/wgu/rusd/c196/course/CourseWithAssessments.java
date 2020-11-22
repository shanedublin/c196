package com.wgu.rusd.c196.course;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.wgu.rusd.c196.assessment.Assessment;
import com.wgu.rusd.c196.course.Course;
import com.wgu.rusd.c196.mentor.Mentor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CourseWithAssessments implements Serializable {
    @Embedded public Course course = new Course();

    @Relation(
            parentColumn = "courseId",
            entityColumn = "courseId"
    )
    public List<Assessment> assessments = new ArrayList<>();

    @Relation( parentColumn = "mentorId",
    entityColumn = "mentorId")
    public Mentor mentor;


    @Override
    public String toString() {
        return "CourseWithAssessments{" +
                "course=" + course +
                ", assessments=" + assessments +
                ", mentor=" + mentor +
                '}';
    }
}
