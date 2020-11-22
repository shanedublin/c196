package com.wgu.rusd.c196.course;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.wgu.rusd.c196.objects.C196Database;

import java.util.List;

public class CourseListViewModel extends AndroidViewModel {


    private LiveData<List<CourseWithAssessments>> list;

    public CourseListViewModel(Application application) {
        super(application);
        list = C196Database.getDBInstance(getApplication()).courseDAO().courseWithAssessments();
    }

    public CourseListViewModel(Application application, long id) {
        super(application);
        list = C196Database.getDBInstance(getApplication()).courseDAO().courseWithAssessmentsById(id);
    }

    public LiveData<List<CourseWithAssessments>> getList() {
        return list;
    }

}
