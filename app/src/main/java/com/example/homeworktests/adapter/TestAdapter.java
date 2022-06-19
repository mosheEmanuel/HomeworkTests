package com.example.homeworktests.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeworktests.R;
import com.example.homeworktests.Test;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestTViewHolder> {

    Context mCtx;
    List<Test> testList;

    public TestAdapter(Context mCtx, List<Test> testList) {
        this.mCtx = mCtx;
        this.testList = testList;
    }


    @NonNull
    @Override
    public TestTViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.custom_layout_test, null);
        return new TestTViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestTViewHolder holder, int position) {
        Test test = testList.get(position);
        holder.tvSubject.setText(test.getSubject());
        holder.tvSubSubject.setText(test.getSubSubject());
        holder.tvDate.setText(test.getDate());
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    static class TestTViewHolder extends RecyclerView.ViewHolder {
        TextView tvSubject, tvSubSubject, tvDate;

        public TestTViewHolder(View itemView) {
            super(itemView);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvSubSubject = itemView.findViewById(R.id.tvSubSubject);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }


}
