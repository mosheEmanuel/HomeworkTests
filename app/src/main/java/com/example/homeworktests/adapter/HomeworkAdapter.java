package com.example.homeworktests.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeworktests.Homework;
import com.example.homeworktests.R;

import java.util.List;

public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.HomeworkTViewHolder> {

    Context mCtx;
    List<Homework> homeworkList;
    ItmeClickListener mItmeClickListener;

    public HomeworkAdapter(Context mCtx, List<Homework> productList,ItmeClickListener mItmeClickListener) {
        this.mCtx = mCtx;
        this.homeworkList = productList;
        this.mItmeClickListener =mItmeClickListener;
    }

    @NonNull
    @Override
    public HomeworkTViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.custom_layout_homework, null);
        return new HomeworkTViewHolder(view,mItmeClickListener);
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



    static class HomeworkTViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvPage, tvExercise, tvSubject, tvDate, tvPriority;
        ItmeClickListener itmeClickListener;

        public HomeworkTViewHolder(View itemView, ItmeClickListener itmeClickListener) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvPage = itemView.findViewById(R.id.tvPage);
            tvExercise = itemView.findViewById(R.id.tvExercise);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvPriority = itemView.findViewById(R.id.tvPriority);
            itemView.setOnClickListener(this);
            this.itmeClickListener = itmeClickListener;

        }


        @Override
        public void onClick(View v) {
            itmeClickListener.onItmeClick(getAdapterPosition());
        }
    }

    @Override
    public int getItemCount() {
        return homeworkList.size();
    }

    public interface ItmeClickListener {
        void onItmeClick(int position);

    }
}
