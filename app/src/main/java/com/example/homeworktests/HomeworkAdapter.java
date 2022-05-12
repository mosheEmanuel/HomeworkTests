package com.example.homeworktests;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.HomeworkTViewHolder> {

    Context mCtx;
    List<Homework> homeworkList;

    public HomeworkAdapter(Context mCtx, List<Homework> productList) {
        this.mCtx = mCtx;
        this.homeworkList = productList;
    }

    @NonNull
    @Override
    public HomeworkTViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.custom_layout_homework, null);
        return new HomeworkTViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeworkTViewHolder holder, int position) {
        Homework homework = homeworkList.get(position);
        holder.tvSubject.setText(homework.getSubject());
        holder.tvPage.setText(homework.getPage());
        holder.tvExercise.setText(homework.getExercise());
        holder.tvDate.setText(homework.getDate());
        holder.tvPriority.setText(homework.getPriority());

    }

    @Override
    public int getItemCount() {
        return homeworkList.size();
    }

    static class HomeworkTViewHolder extends RecyclerView.ViewHolder {

        TextView tvPage,tvExercise, tvSubject, tvDate ,tvPriority;

        public HomeworkTViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvPage = itemView.findViewById(R.id.tvPage);
            tvExercise = itemView.findViewById(R.id.tvExercise);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvPriority = itemView.findViewById(R.id.tvPriority);

        }

    }
}
