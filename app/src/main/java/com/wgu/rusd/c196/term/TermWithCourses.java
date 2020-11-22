package com.wgu.rusd.c196.term;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.wgu.rusd.c196.assessment.Assessment;
import com.wgu.rusd.c196.course.Course;

import java.util.ArrayList;
import java.util.List;

public class TermWithCourses {

    @Embedded public Term term = new Term();

    @Relation(
            parentColumn = "termId",
            entityColumn = "termId"
    )
    public List<Course> courses = new ArrayList<>();

    @Override
    public String toString() {
        return "TermWithCourses{" +
                "term=" + term +
                ", courses=" + courses +
                '}';
    }
}
