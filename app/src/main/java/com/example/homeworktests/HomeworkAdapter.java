package com.example.homeworktests;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.HomeworkTViewHolder> {

    Context mCtx;
    List<Homework> productList;

    public HomeworkAdapter(Context mCtx, List<Homework> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
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
        Homework product = productList.get(position);
        holder.ivPic.setImageBitmap(product.getBitmap());
        holder.tvSubject.setText(product.getSubject());
        holder.tvPage.setText(product.getPage());
        holder.tvDate.setText(product.getDate());
        holder.tvPriority.setText(product.getDate());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class HomeworkTViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPic;
        TextView tvPage, tvSubject, tvDate ,tvPriority;

        public HomeworkTViewHolder(View itemView) {
            super(itemView);
            ivPic = itemView.findViewById(R.id.ivPic);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvPage = itemView.findViewById(R.id.tvPage);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvPriority = itemView.findViewById(R.id.tvPriority);

        }

    }
}
