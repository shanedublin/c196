package com.wgu.rusd.c196.objects;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.wgu.rusd.c196.assessment.Assessment;
import com.wgu.rusd.c196.assessment.AssessmentDAO;
import com.wgu.rusd.c196.course.Course;
import com.wgu.rusd.c196.course.CourseDAO;
import com.wgu.rusd.c196.database.DateConverter;
import com.wgu.rusd.c196.mentor.Mentor;
import com.wgu.rusd.c196.mentor.MentorDAO;
import com.wgu.rusd.c196.term.Term;
import com.wgu.rusd.c196.term.TermDAO;

@Database(version = 1, entities = {Mentor.class, Course.class, Assessment.class, Term.class}, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class C196Database extends RoomDatabase {


    private static volatile C196Database db;

    public static synchronized C196Database getDBInstance(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context, C196Database.class, "C196Database")
                .fallbackToDestructiveMigration()
                .build();
            // would need to remove before prod
            DatabaseUtil.prepopulateDatabase(context);
        }

        return db;
    }

    public abstract MentorDAO mentorDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();
    public abstract TermDAO termDAO();


}
