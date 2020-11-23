package com.wgu.rusd.c196.term;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import com.wgu.rusd.c196.BaseMenuActivity;
import com.wgu.rusd.c196.R;
import com.wgu.rusd.c196.course.CourseListAdapter;
import com.wgu.rusd.c196.settings.SettingsActivity;

public class TermListActivity extends BaseMenuActivity {

    public static final String TAG = TermListActivity.class.getName();

    RecyclerView recyclerView;
    TermListAdapter adapter;
    LayoutManager layoutManager;
    TermListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        viewModel = new ViewModelProvider(this).get(TermListViewModel.class);
        recyclerView = findViewById(R.id.term_list_recyclerview);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        viewModel.getList().observe(this, s->{
            Log.d(this.getClass().getName(),s.toString());
            if(adapter == null) {
                Log.d(TAG,"new");
                adapter = new TermListAdapter(viewModel.getList().getValue(),this);
                recyclerView.setAdapter(adapter);
            } else {
                Log.d(TAG,"update");
                adapter.setList(s);
            }

        });
    }



    public void newTerm(View view){
        Intent i = new Intent(this, TermActivity.class);
        startActivity(i);
    }

    public void editTerm(Term term){
        Intent i = new Intent(this, TermActivity.class);
        i.putExtra("term", term);
        startActivity(i);
    }

}