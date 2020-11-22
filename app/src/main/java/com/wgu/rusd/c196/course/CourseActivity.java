package com.wgu.rusd.c196.course;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.wgu.rusd.c196.R;
import com.wgu.rusd.c196.assessment.AssessmentActivity;
import com.wgu.rusd.c196.assessment.AssessmentListAdapter;
import com.wgu.rusd.c196.mentor.Mentor;
import com.wgu.rusd.c196.mentor.MentorListViewModel;
import com.wgu.rusd.c196.assessment.Assessment;
import com.wgu.rusd.c196.term.Term;
import com.wgu.rusd.c196.util.DateListener;


import java.util.ArrayList;
import java.util.Arrays;

import static com.wgu.rusd.c196.objects.C196Database.getDBInstance;
import static com.wgu.rusd.c196.util.MyDateUtil.dtf;

public class CourseActivity extends AppCompatActivity {

    public static final String TAG = CourseActivity.class.getName();

    EditText courseTitle;
    EditText startDate;
    EditText endDate;
    CourseWithAssessments courseWithAssessments;

    TextView phoneNumber;
    TextView email;
    TextView notes;

    Button addAssessmentButton;

    Spinner statusSpinner;
    Spinner termSpinner;
    AutoCompleteTextView autoCompleteTextView;

    LayoutManager layoutManager;
    RecyclerView recyclerView;

    AssessmentListAdapter assessmentListAdapter;

    MentorListViewModel mentorListViewModel;
    ArrayAdapter<Mentor> mentorArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        courseTitle = findViewById(R.id.course_title);
        startDate = findViewById(R.id.course_start_date);
        endDate = findViewById(R.id.course_end_date);

        phoneNumber = findViewById(R.id.mentor_number);
        email = findViewById(R.id.mentor_email);
        notes = findViewById(R.id.notes);

        statusSpinner = findViewById(R.id.course_status);
        termSpinner = findViewById(R.id.term_spinner);

        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this, R.array.assessment_status_array, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);


        loadTerms();

        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        autoCompleteTextView = findViewById(R.id.mentor_auto_complete);


        mentorListViewModel = new ViewModelProvider(this).get(MentorListViewModel.class);

        mentorListViewModel.getList().observe(this, list -> {
            Log.d(TAG, list.toString());
            if (mentorArrayAdapter == null) {
                mentorArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, list);
                autoCompleteTextView.setAdapter(mentorArrayAdapter);
            } else {
                mentorArrayAdapter.notifyDataSetChanged();
            }
        });

        autoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
            Mentor m = (Mentor) adapterView.getItemAtPosition(i);
            courseWithAssessments.mentor = m;
            courseWithAssessments.course.mentorId = m.mentorId;
            phoneNumber.setText(m.phoneNumber);
            email.setText(m.emailAddress);
            }
        );


        mentorListViewModel = new ViewModelProvider(this).get(MentorListViewModel.class);


        long id = getIntent().getLongExtra("id", -1l);
        if (id >= 0) {
            Log.d(this.getLocalClassName(), "id: " + id);
            //Mentor mentor = MainActivity.db.mentorDAO().findMentor(id);
            loadCourseInfo(id);
        } else {
            courseWithAssessments = new CourseWithAssessments();
            DateListener startDateListener = new DateListener(startDate, CourseActivity.this, (localDate) -> courseWithAssessments.course.startDate = localDate);
            DateListener endDateListener = new DateListener(endDate, CourseActivity.this, (localDate) -> courseWithAssessments.course.endDate = localDate);
            addAssessmentButton = findViewById(R.id.add_assessment);
            addAssessmentButton.setEnabled(false);
            addAssessmentButton.setText("(Save course to add Assessments)");


        }
    }

    public void loadTerms(){

        getDBInstance(getApplicationContext()).termDAO().loadAll().observe(this,list ->{
            ArrayAdapter<CharSequence> termAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
            termAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            termSpinner.setAdapter(termAdapter);
        });

        termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Term term = (Term) adapterView.getItemAtPosition(i);
                courseWithAssessments.course.termId = term.termId;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                courseWithAssessments.course.termId = null;
            }
        });


    }

    public void loadCourseInfo(Long id) {
        AsyncTask.execute(() -> {
            courseWithAssessments = getDBInstance(getApplicationContext()).courseDAO().findCourse(id);

            runOnUiThread(() -> {
                courseTitle.setText(courseWithAssessments.course.title);
                DateListener startDateListener = new DateListener(startDate, CourseActivity.this, (localDate) -> courseWithAssessments.course.startDate = localDate);
                DateListener endDateListener = new DateListener(endDate, CourseActivity.this, (localDate) -> courseWithAssessments.course.endDate = localDate);
                if (courseWithAssessments.mentor != null) {
                    autoCompleteTextView.setText(courseWithAssessments.mentor.name);
                    phoneNumber.setText(courseWithAssessments.mentor.phoneNumber);
                    email.setText(courseWithAssessments.mentor.emailAddress);
                }
                if (courseWithAssessments.course.startDate != null) {
                    startDate.setText(courseWithAssessments.course.startDate.format(dtf));
                }

                Log.d(TAG, courseWithAssessments.course.endDate + "");
                if (courseWithAssessments.course.endDate != null) {
                    endDate.setText(courseWithAssessments.course.endDate.format(dtf));
                }

                if (courseWithAssessments.course.notes != null) {
                    notes.setText(courseWithAssessments.course.notes);
                }

                if (courseWithAssessments.assessments == null) {
                    courseWithAssessments.assessments = new ArrayList<>();
                }

                Log.d(TAG, Arrays.toString(courseWithAssessments.assessments.toArray()));
                assessmentListAdapter = new AssessmentListAdapter(courseWithAssessments.assessments, this);
                recyclerView.setAdapter(assessmentListAdapter);
            });

        });
    }

    public void saveCourse(View view) {
        courseWithAssessments.course.title = courseTitle.getText().toString();
        courseWithAssessments.course.status = statusSpinner.getSelectedItem().toString();
        courseWithAssessments.course.notes = notes.getText().toString();



        AsyncTask.execute(() -> {
            getDBInstance(getApplicationContext()).courseDAO().insertCourseWithAssessments(courseWithAssessments);
            finish();
        });
    }

    public void deleteCourse(View view) {
        AsyncTask.execute(() -> {
            getDBInstance(getApplicationContext()).courseDAO().delete(courseWithAssessments.course);
            finish();
        });
    }


    public void editAssessment(Assessment assessment) {
        Intent i = new Intent(this, AssessmentActivity.class);
        i.putExtra("cwa", courseWithAssessments);
        i.putExtra("assessment", assessment);
        startActivity(i);

    }

    public void openAssessment(View view) {
        Intent i = new Intent(this, AssessmentActivity.class);
        i.putExtra("cwa", courseWithAssessments);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "BACK");
        long id = getIntent().getLongExtra("id", -1l);
        if (id >= 0) {
            Log.d(this.getLocalClassName(), "id: " + id);
            //Mentor mentor = MainActivity.db.mentorDAO().findMentor(id);
            loadCourseInfo(id);
        }
    }

    public void share(View view) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, courseTitle.getText().toString());
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, notes.getText().toString());
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
}