package com.wgu.rusd.c196.course;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProvider.Factory;


public class CourseListViewModelFactory implements Factory {

    Application application;
    long id;

    public CourseListViewModelFactory(Application application, long id){
        this.application = application;
        this.id = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CourseListViewModel(application,id);
    }
}
