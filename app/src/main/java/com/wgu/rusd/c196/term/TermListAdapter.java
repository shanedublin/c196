package com.wgu.rusd.c196.term;

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

public class TermListAdapter extends RecyclerView.Adapter<TermListAdapter.TermListViewHolder> {

    List<TermWithCourses> list;

    com.wgu.rusd.c196.term.TermListActivity termListActivity;
    public TermListAdapter(List<TermWithCourses> l, TermListActivity thiz) {
        list = l;
        termListActivity = thiz;
        notifyDataSetChanged();
    }
    

    @NonNull
    @Override
    public TermListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View TermView = inflater.inflate(R.layout.term_list_linear_layout,parent,false);
        TermListViewHolder vh = new TermListViewHolder(TermView);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull TermListViewHolder holder, int position) {
        Term term = list.get(position).term;
        holder.textView.setText(term.title);
        holder.editButton.setOnClickListener(view -> {
           // long id = term.termId;
            //Log.d(this.getClass().getName(), "id: " + id);
            termListActivity.editTerm(term);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<TermWithCourses> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public static class TermListViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public Button editButton;
        TermListViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.term_list_text_view);
            editButton = v.findViewById(R.id.term_list_edit_button);
        }
    }

}
