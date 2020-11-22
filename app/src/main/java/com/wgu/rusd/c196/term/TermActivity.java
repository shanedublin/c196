package com.wgu.rusd.c196.term;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wgu.rusd.c196.R;
import com.wgu.rusd.c196.course.Course;
import com.wgu.rusd.c196.course.CourseActivity;
import com.wgu.rusd.c196.course.CourseListAdapter;
import com.wgu.rusd.c196.course.CourseListViewModel;
import com.wgu.rusd.c196.course.CourseListViewModelFactory;
import com.wgu.rusd.c196.course.CourseWithAssessments;
import com.wgu.rusd.c196.course.EditCourse;
import com.wgu.rusd.c196.util.DateListener;

import java.util.List;

import static com.wgu.rusd.c196.objects.C196Database.getDBInstance;
import static com.wgu.rusd.c196.util.MyDateUtil.dtf;

public class TermActivity extends AppCompatActivity implements EditCourse {

    public static final String TAG = TermActivity.class.getName();

    EditText title;
    EditText startDate;
    EditText endDate;

    RecyclerView recyclerView;
    CourseListAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    CourseListViewModel viewModel;

    Term term;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);

        title = findViewById(R.id.term_title);
        startDate = findViewById(R.id.term_start_date);
        endDate = findViewById(R.id.term_end_date);

        recyclerView = findViewById(R.id.term_recyclerview);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        term = (Term) getIntent().getSerializableExtra("term");

        if (term == null) {
            term = new Term();
        }

        viewModel = new ViewModelProvider(this, new CourseListViewModelFactory(getApplication(),term.termId)).get(CourseListViewModel.class);

        title.setText(term.title);

        if (term.start != null) {
            startDate.setText(term.start.format(dtf));
        }

        if (term.end != null) {
            endDate.setText(term.end.format(dtf));
        }

        new DateListener(startDate, TermActivity.this, (localDate) -> term.start = localDate);
        new DateListener(endDate, TermActivity.this, (localDate) -> term.end = localDate);

        if (term.termId != null) {
            viewModel.getList().observe(this, s -> {
                Log.d(this.getClass().getName(), s.toString());
                if (adapter == null) {
                    Log.d(TAG, "new");
                    List<CourseWithAssessments> value = viewModel.getList().getValue();
                    adapter = new CourseListAdapter(value, this);
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.d(TAG, "update");
                    adapter.setList(s);
                }

            });
        }

    }

    public void save(View view) {
        term.title = title.getText().toString();
        AsyncTask.execute(() -> {
            getDBInstance(getApplicationContext()).termDAO().insert(term);
            finish();
        });
    }

    public void delete(View view) {
        AsyncTask.execute(() -> {
            getDBInstance(getApplicationContext()).termDAO().delete(term);
            finish();
        });
    }

    @Override
    public void editCourse(long id) {
        Intent i = new Intent(this, CourseActivity.class);
        i.putExtra("id", id);
        startActivity(i);
    }
}