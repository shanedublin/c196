package com.wgu.rusd.c196.mentor;

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

import java.util.List;

public class MentorListAdapter extends RecyclerView.Adapter<MentorListAdapter.MentorListViewHolder> {

    List<Mentor> list;

    MentorListActivity mentorListActivity;
    public MentorListAdapter(List<Mentor> l, MentorListActivity thiz) {
        list = l;
        mentorListActivity = thiz;
        notifyDataSetChanged();
    }
    
    @NonNull
    @Override
    public MentorListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View mentorView = inflater.inflate(R.layout.mentor_list_linear_layout,parent,false);
        MentorListViewHolder vh = new MentorListViewHolder(mentorView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MentorListViewHolder holder, int position) {
        holder.textView.setText(list.get(position).name);
        holder.editButton.setOnClickListener(view -> {
            long id = list.get(position).mentorId;
            Log.d(this.getClass().getName(), "id: " + id);
            mentorListActivity.editMentor(id);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Mentor> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public static class MentorListViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public Button editButton;
        MentorListViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.mentor_list_text_view);
            editButton = v.findViewById(R.id.mentor_list_edit_button);
        }
    }

}
