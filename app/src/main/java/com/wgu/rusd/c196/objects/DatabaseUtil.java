package com.wgu.rusd.c196.objects;

import android.content.Context;
import android.util.Log;

import com.wgu.rusd.c196.assessment.Assessment;
import com.wgu.rusd.c196.assessment.AssessmentDAO;
import com.wgu.rusd.c196.course.Course;
import com.wgu.rusd.c196.course.CourseDAO;
import com.wgu.rusd.c196.course.CourseWithAssessments;
import com.wgu.rusd.c196.mentor.Mentor;
import com.wgu.rusd.c196.mentor.MentorDAO;
import com.wgu.rusd.c196.term.Term;
import com.wgu.rusd.c196.term.TermDAO;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static android.os.AsyncTask.execute;

public class DatabaseUtil {
    public static final String TAG = DatabaseUtil.class.getName();

    static MentorDAO mentorDAO;
    static CourseDAO courseDAO;
    static AssessmentDAO assessmentDAO;
    static TermDAO termDAO;

    public static void prepopulateDatabase(Context context){
        C196Database db = C196Database.getDBInstance(context);
        mentorDAO = db.mentorDAO();
        courseDAO = db.courseDAO();
        assessmentDAO = db.assessmentDAO();
        termDAO = db.termDAO();

        LocalDate now = LocalDate.now();
        createTerm(1001l, "Term 1", now, now.plus(3, ChronoUnit.MONTHS) );
        createTerm(1002l, "Term 2", now.plus(3,ChronoUnit.MONTHS), now.plus(6, ChronoUnit.MONTHS) );
        createTerm(1003l, "Term 3 empty", now.plus(6, ChronoUnit.MONTHS), now.plus(9, ChronoUnit.MONTHS) );

        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        createDummyData(101l, "Shane Dublin", 1l, "C196", 1l, 1001l, "This android project is soo hard");
        createDummyData(102l, "Mr. Math Teacher", 2l, "Math",10l, 1002l,
                "Why is math so easy?!?!?");
        createDummyData(103l, "Dr. History Buff", 3l, "History",20l, 1002l,
                "I love age of empires!!");

        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
/*
        execute(() -> {
            try {
              //  Thread.sleep(2000);
                List<Assessment> listLiveData = assessmentDAO.loadAll();
                Thread.sleep(1000);
                Log.d(TAG, Arrays.toString(listLiveData.toArray()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        */

    }

    public static void createTerm(long termId, String termName, LocalDate start, LocalDate end){
        Term t = new Term();
        t.title = termName;
        t.termId = termId;
        t.start = start;
        t.end = end;
        execute(()->{
            termDAO.insert(t);
        });

    }

    public static void createDummyData(Long mentorId, String mentorName, Long courseId, String courseName, Long assessmentId, Long termId, String courseNotes ){
        Mentor m = new Mentor();
        m.mentorId = mentorId;
        m.emailAddress = mentorName.replace(" ",".").toLowerCase() + "@gmail.com";
        m.name = mentorName;
        m.phoneNumber = mentorId + "-557-5100";

        CourseWithAssessments courseWithAssessments = new CourseWithAssessments();
        Course c = courseWithAssessments.course;
        c.courseId = courseId;
        c.title = courseName;
        c.startDate = LocalDate.now();
        c.endDate = LocalDate.now().plus(5, ChronoUnit.MONTHS);
        c.mentorId = mentorId;

        c.notes = courseNotes;
        c.termId = termId;




        Assessment assessment = new Assessment();
        assessment.name = "Objective Assessment: " + courseName + " : " + courseId;
        assessment.type = "Objective";
        assessment.status = "Passed";
        assessment.courseId = courseId;
        assessment.assessmentId = assessmentId;

        Assessment a = new Assessment();
        a.name = "Performance Assessment: " + courseName + " : " + courseId;
        a.type = "Performance";
        a.status = "Un-attempted";
        a.courseId = courseId;
        a.assessmentId = assessmentId + 1;

        Log.d("what0", assessment.toString());
        Log.d("what0", a.toString());

        execute(() -> {

            mentorDAO.insert(m);
            courseDAO.insert(c);
            assessmentDAO.insert(a);
            assessmentDAO.insert(assessment);
        });




    }



}
