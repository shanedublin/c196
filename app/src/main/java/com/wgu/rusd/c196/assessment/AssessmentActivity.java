package com.wgu.rusd.c196.assessment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.wgu.rusd.c196.BaseMenuActivity;
import com.wgu.rusd.c196.R;
import com.wgu.rusd.c196.objects.C196Database;
import com.wgu.rusd.c196.course.CourseWithAssessments;
import com.wgu.rusd.c196.util.DateListener;

import static com.wgu.rusd.c196.util.MyDateUtil.dtf;

public class AssessmentActivity extends BaseMenuActivity {

    public static final String TAG = AssessmentActivity.class.getName();

    Spinner statusSpinner;
    EditText title;
    RadioButton objective;
    RadioButton performance;

    EditText goalDate;

    CourseWithAssessments courseWithAssessments;
    Assessment assessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);
        title = findViewById(R.id.assessment_title);
        statusSpinner = findViewById(R.id.assessment_status);
        goalDate = findViewById(R.id.goal_date);


        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this,R.array.assessment_status_array, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);

        objective = findViewById(R.id.objective_rb);
        performance = findViewById(R.id.performance_rb);

        courseWithAssessments = (CourseWithAssessments) getIntent().getSerializableExtra("cwa");
        assessment = (Assessment) getIntent().getSerializableExtra("assessment");
        if(assessment == null) {
            assessment = new Assessment();
            assessment.courseId = courseWithAssessments.course.courseId;
            courseWithAssessments.assessments.add(assessment);
        }

        new DateListener(goalDate, this, (localDate) -> assessment.goalDate = localDate);

        title.setText(assessment.name);
        int position = statusAdapter.getPosition(assessment.status);
        statusSpinner.setSelection(position);
        Log.d(TAG + "Assessment: " , assessment.toString());

        if("Objective".equalsIgnoreCase(assessment.type)){
            objective.setChecked(true);
            performance.setChecked(false);
        } else {
            performance.setChecked(true);
            objective.setChecked(false);
        }

        if (assessment.goalDate != null) {
            goalDate.setText(assessment.goalDate.format(dtf));
        }

    }

    public void save(View view) {
        assessment.name = title.getText().toString();
        // just default to one
        assessment.type  = objective.isChecked() ? "Objective" : "Performance";
        assessment.status = statusSpinner.getSelectedItem().toString();
        assessment.courseId = courseWithAssessments.course.courseId;
        Log.d(TAG + " Saving",  assessment.type );


        AsyncTask.execute(()->{
            C196Database.getDBInstance(getApplicationContext()).assessmentDAO().insert(assessment);
            finish();
        });
    }

    public void delete(View view) {
        AsyncTask.execute(()->{
            C196Database.getDBInstance(getApplicationContext()).assessmentDAO().delete(assessment);
            finish();
        });
    }







}