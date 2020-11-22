package com.wgu.rusd.c196.util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.EditText;

import com.wgu.rusd.c196.course.CourseWithAssessments;

import java.time.LocalDate;
import java.time.ZoneId;

import static com.wgu.rusd.c196.util.MyDateUtil.dtf;

public class DateListener {

    public interface MyInterface {
        void setText(LocalDate localDate);
    }

    //CourseWithAssessments courseWithAssessments;
    EditText editTextDate;
    MyInterface myInterface;

    LocalDate localDate = LocalDate.now(ZoneId.systemDefault());

    public DateListener(EditText startDate, Context context, MyInterface myInterface) {
        this.editTextDate = startDate;
       // this.courseWithAssessments = courseWithAssessments;
        this.myInterface = myInterface;

        this.editTextDate.setOnClickListener(v ->
                new DatePickerDialog(context, onDateSetListener,
                        localDate.getYear(),
                        localDate.getMonthValue() -1,
                        localDate.getDayOfMonth())
                        .show());
    }

    DatePickerDialog.OnDateSetListener onDateSetListener = (datePicker, year, monthOfYear, dayOfMonth) -> {
        LocalDate ld = LocalDate.of(year,monthOfYear +1 ,dayOfMonth);
        editTextDate.setText(ld.format(dtf));
        myInterface.setText(ld);
    };

}
