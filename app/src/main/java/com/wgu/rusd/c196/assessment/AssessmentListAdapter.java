package com.wgu.rusd.c196.assessment;

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
import com.wgu.rusd.c196.course.CourseActivity;

import java.util.List;

public class AssessmentListAdapter extends RecyclerView.Adapter<AssessmentListAdapter.AssessmentListViewHolder> {

    List<Assessment> list;

    CourseActivity courseActivity;
    public AssessmentListAdapter(List<Assessment> l, CourseActivity courseActivity) {
        list = l;
        this.courseActivity = courseActivity;
        notifyDataSetChanged();
    }
    

    @NonNull
    @Override
    public AssessmentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View CourseView = inflater.inflate(R.layout.assessment_list_linear_layout, parent,false);
        AssessmentListAdapter.AssessmentListViewHolder vh = new AssessmentListAdapter.AssessmentListViewHolder(CourseView);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentListAdapter.AssessmentListViewHolder holder, int position) {
        Assessment a = list.get(position);
        holder.textView.setText(a.name);
        holder.editButton.setOnClickListener(view -> {
            Log.d(this.getClass().getName(), "Assessment: " + a);
            courseActivity.editAssessment(a);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Assessment> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public static class AssessmentListViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public Button editButton;
        AssessmentListViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.assessment_list_text_view);
            editButton = v.findViewById(R.id.assessment_list_edit_button);
        }
    }

}
