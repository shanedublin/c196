package com.wgu.rusd.c196.mentor;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.wgu.rusd.c196.objects.C196Database;

import java.util.List;

public class MentorListViewModel extends AndroidViewModel {


    private LiveData<List<Mentor>> list;

    public MentorListViewModel(Application application) {
        super(application);
        list = C196Database.getDBInstance(getApplication()).mentorDAO().loadAll();
    }

    public LiveData<List<Mentor>> getList() {
        return list;
    }
}
