package com.wgu.rusd.c196.course;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wgu.rusd.c196.R;
import com.wgu.rusd.c196.assessment.AssessmentActivity;

import java.util.List;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseListViewHolder> {

    public static final String TAG = CourseListAdapter.class.getName();

    List<CourseWithAssessments> list;

    EditCourse editCourse;
    public CourseListAdapter(List<CourseWithAssessments> l, EditCourse editCourse) {
        list = l;
        this.editCourse = editCourse;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View CourseView = inflater.inflate(R.layout.course_list_linear_layout,parent,false);
        CourseListViewHolder vh = new CourseListViewHolder(CourseView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseListViewHolder holder, int position) {

        CourseWithAssessments cwa = list.get(position);
        Course c = cwa.course;
        holder.textView.setText(c.title);
        if(cwa.term != null) {
            //Log.d(TAG, cwa.toString());
            holder.termText.setText(cwa.term.title);
        }
        holder.editButton.setOnClickListener(view -> {
            long id = c.courseId;
            //Log.d(this.getClass().getName(), "id: " + id);
            editCourse.editCourse(id);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<CourseWithAssessments> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public static class CourseListViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public Button editButton;
        public TextView termText;
        CourseListViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.course_list_text_view);
            editButton = v.findViewById(R.id.course_list_edit_button);
            termText = v.findViewById(R.id.course_list_term_text);
        }
    }

}
