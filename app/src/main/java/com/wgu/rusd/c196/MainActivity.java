package com.wgu.rusd.c196;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.preference.PreferenceManager;

import com.wgu.rusd.c196.assessment.Assessment;
import com.wgu.rusd.c196.course.Course;
import com.wgu.rusd.c196.course.CourseListActivity;
import com.wgu.rusd.c196.database.DateConverter;
import com.wgu.rusd.c196.mentor.MentorListActivity;
import com.wgu.rusd.c196.notifications.MyNotificationPublisher;
import com.wgu.rusd.c196.term.TermListActivity;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

import static com.wgu.rusd.c196.objects.C196Database.getDBInstance;
import static com.wgu.rusd.c196.util.MyDateUtil.dtf2;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getName();

    private static final String CHANNEL_ID = "course_notification";
    private boolean assessmentNotifications;
    private boolean courseNotification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        assessmentNotifications = sharedPreferences.getBoolean(SettingsActivity.ASSESSMENT_NOTIFICATIONS, false);
        courseNotification = sharedPreferences.getBoolean(SettingsActivity.COURSE_NOTIFICATIONS, false);


        createNotificationChannel();
        initNotifications();

    }

    public void initNotifications() {


        // schedule a notif one day before

        // get all courses
        if (courseNotification) {
            getDBInstance(getApplicationContext()).courseDAO().loadAll().observe(this, list ->
                    list.stream().forEach(course -> {
                        scheduleCourseNotification(course, false);
                        scheduleCourseNotification(course, true);
                    }
            ));

        }

        // get all assessments
        if (assessmentNotifications) {
            getDBInstance(getApplicationContext()).assessmentDAO().loadAll().observe(this, list ->
                    list.stream().forEach(this::scheduleAssessmentNotification)
            );

        }

    }


    private void scheduleCourseNotification(Course course, boolean startDate) {

        LocalDate date;
        String msg;
        if (startDate) {
            date = course.startDate;
            msg = "Start date: " + date.format(dtf2);
        } else {
            date = course.endDate;
            msg = "End date: " + date.format(dtf2);
        }

        long triggerTime = DateConverter.dateToTimestamp(date);
        scheduleNotification(course.title, msg, getID(), triggerTime);
    }

    private void scheduleAssessmentNotification(Assessment assessment) {
        LocalDate date = assessment.goalDate;
        if (date != null) {
            String msg = "Start date: " + date.format(dtf2);
            long triggerTime = DateConverter.dateToTimestamp(date);
            scheduleNotification(assessment.name, msg, getID(), triggerTime);
        }
    }

    public static final AtomicInteger counter = new AtomicInteger(1000);

    public int getID() {
        return counter.getAndIncrement();
    }

    private void scheduleNotification(String title, String msg, Integer id, long triggerTime) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Notification notification = builder.build();

        Intent intent = new Intent(this, MyNotificationPublisher.class);
        intent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, id);
        intent.putExtra(MyNotificationPublisher.NOTIFICATION, notification);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, triggerTime, pendingIntent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();
        Log.d(TAG, "item id : " + itemId);
        if (itemId == R.id.settings_menu_button) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.assessment_channel_name);
            String description = getString(R.string.assessment_channel_description);

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void openMentor(View view) {
        Intent i = new Intent(this, MentorListActivity.class);
        startActivity(i);
    }

    public void openCourseList(View view) {
        Intent i = new Intent(this, CourseListActivity.class);
        startActivity(i);
    }

    public void openTermList(View view) {
        Intent i = new Intent(this, TermListActivity.class);
        startActivity(i);
    }
}