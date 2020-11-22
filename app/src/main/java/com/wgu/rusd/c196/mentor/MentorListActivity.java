package com.wgu.rusd.c196.mentor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wgu.rusd.c196.R;

public class MentorListActivity extends AppCompatActivity {

    public static final String TAG = MentorListActivity.class.getName();

    private RecyclerView recyclerView;
    private MentorListAdapter mentorListAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private MentorListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_list);

        viewModel = new ViewModelProvider(this).get(MentorListViewModel.class);
        recyclerView = findViewById(R.id.mentor_list_recyclerview);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        viewModel.getList().observe(this, s->{
            Log.d(this.getClass().getName(),s.toString());
            if(mentorListAdapter == null) {
                Log.d(TAG,"new");
                mentorListAdapter = new MentorListAdapter(viewModel.getList().getValue(),this);
                recyclerView.setAdapter(mentorListAdapter);
            } else {
                Log.d(TAG,"update");
                mentorListAdapter.setList(s);
            }
        });

    }

    public void newMentor(View view){
        Intent i = new Intent(this, MentorActivity.class);
        startActivity(i);
    }

    public void editMentor(long id){
        Intent i = new Intent(this, MentorActivity.class);
        i.putExtra("id", id);
        startActivity(i);
    }

}