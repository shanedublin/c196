package com.wgu.rusd.c196.term;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.wgu.rusd.c196.course.CourseWithAssessments;
import com.wgu.rusd.c196.objects.C196Database;

import java.util.List;

public class TermListViewModel extends AndroidViewModel {



    private LiveData<List<TermWithCourses>> list;

    public TermListViewModel(Application application) {
        super(application);
        list = C196Database.getDBInstance(getApplication()).termDAO().termWithAssessments();
    }

    public LiveData<List<TermWithCourses>> getList() {
        return list;
    }
}
