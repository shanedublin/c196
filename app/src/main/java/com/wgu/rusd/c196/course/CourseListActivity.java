package com.wgu.rusd.c196.course;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wgu.rusd.c196.BaseMenuActivity;
import com.wgu.rusd.c196.R;

import java.util.List;

public class CourseListActivity extends BaseMenuActivity implements EditCourse {

    public static final String TAG = CourseListActivity.class.getName();

    RecyclerView recyclerView;
    CourseListAdapter adapter;
    LayoutManager layoutManager;
    CourseListViewModel viewModel;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        viewModel = new ViewModelProvider(this).get(CourseListViewModel.class);
        recyclerView = findViewById(R.id.course_list_recyclerview);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        viewModel.getList().observe(this, s ->{
            Log.d(this.getClass().getName(), s.toString());
            if(adapter == null) {
                Log.d(TAG,"new");
                List<CourseWithAssessments> list = viewModel.getList().getValue();
                list.sort((cwa1, cwa2) -> cwa1.term.start.compareTo(cwa2.term.start));
                adapter = new CourseListAdapter(list,this);
                recyclerView.setAdapter(adapter);
            } else {
                Log.d(TAG,"update");
                s.sort((cwa1, cwa2) -> cwa1.term.start.compareTo(cwa2.term.start));
                adapter.setList(s);
            }

        });
    }

    public void newCourse(View view){
        Intent i = new Intent(this, CourseActivity.class);
        startActivity(i);
    }

    @Override
    public void editCourse(long id){
        Intent i = new Intent(this, CourseActivity.class);
        i.putExtra("id", id);
        startActivity(i);
    }



}