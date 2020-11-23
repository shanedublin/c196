package com.wgu.rusd.c196.mentor;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.wgu.rusd.c196.BaseMenuActivity;
import com.wgu.rusd.c196.R;

import static com.wgu.rusd.c196.objects.C196Database.getDBInstance;

public class MentorActivity extends BaseMenuActivity {

    Mentor mentor;
    EditText text;
    EditText mentorPhone;
    EditText mentorEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor);
        text = findViewById(R.id.mentor_name);
        mentorPhone = findViewById(R.id.mentor_phone);
        mentorEmail = findViewById(R.id.mentor_email);

        long id = getIntent().getLongExtra("id", -1l);
        if(id >= 0) {
            Log.d(this.getLocalClassName(),"id: " + id);
            //Mentor mentor = MainActivity.db.mentorDAO().findMentor(id);
            AsyncTask.execute(() -> {
                mentor = getDBInstance(getApplicationContext()).mentorDAO().findMentor(id);
                text.setText(mentor.name);
                mentorPhone.setText(mentor.phoneNumber);
                mentorEmail.setText(mentor.emailAddress);
            });
        } else {
            mentor = new Mentor();
        }
    }

    public void saveMentor(View view){
        Log.d(this.getClass().getName(), "Save Mentor");
        Log.d(this.getClass().getName(),text.getText().toString() + " " + mentorPhone.getText().toString() + " " + mentorEmail.getText().toString());
        mentor.emailAddress = mentorEmail.getText().toString();
        mentor.phoneNumber =mentorPhone.getText().toString();
        mentor.name = text.getText().toString();
        AsyncTask.execute(() -> {
            Long id = getDBInstance(getApplicationContext()).mentorDAO().insert(mentor);
            mentor.mentorId = id ;
            finish();
        });
    }

    public void deleteMentor(View view) {
        AsyncTask.execute(() -> {
            getDBInstance(getApplicationContext()).mentorDAO().delete(mentor);
            finish();
        });
    }

}