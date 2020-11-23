package com.wgu.rusd.c196.course;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;


import java.util.List;

@Dao
public interface CourseDAO {

    String TAG = CourseDAO.class.getName();

    default void insertCourseWithAssessments(CourseWithAssessments cwa){
        if(cwa.mentor != null) {
            cwa.course.mentorId = cwa.mentor.mentorId;
        } else {
            cwa.course.mentorId = null;
        }
        Log.d(TAG, cwa.toString());
        insert(cwa.course);
    }

    @Query("select * from course")
    LiveData<List<Course>> loadAll();

    @Transaction
    @Query("select * from course where courseId =(:id)")
    CourseWithAssessments findCourse(long id);

    @Query("select * from course where termId = (:id)")
    List<Course> findCoursesByTermId(long id);

    @Transaction
    @Query("select * from course")
    LiveData<List<CourseWithAssessments>> courseWithAssessments();

    @Transaction
    @NonNull
    @Query("select * from course where termId =(:id)")
    LiveData<List<CourseWithAssessments>> courseWithAssessmentsById(long id);


    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Course course);


    @Transaction
    @Delete
    void delete(Course course);
}
