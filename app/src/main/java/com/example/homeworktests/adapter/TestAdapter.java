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
    List<Test> testList; // הרשימה
    TestItmeClickListener mTestItmeClickListener; // אוביקט בשביל לדעת על איזה עצם לחצת

    // פעולה בונה
    public TestAdapter(Context mCtx, List<Test> testList, TestItmeClickListener mTestItmeClickListener) {
        this.mCtx = mCtx;
        this.testList = testList;
        this.mTestItmeClickListener = mTestItmeClickListener;
    }

    //יוצרViewHolder

    @NonNull
    @Override
    public TestTViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.custom_layout_test, null);
        return new TestTViewHolder(view, mTestItmeClickListener);
    }
    // בונה את ViewHolder
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

    // class עזר  שמקבל רפרנס ל custom_layout_homework
    static class TestTViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvSubject, tvSubSubject, tvDate;
        TestItmeClickListener testItmeClickListener;

        public TestTViewHolder(View itemView, TestItmeClickListener testItmeClickListener) {
            super(itemView);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvSubSubject = itemView.findViewById(R.id.tvSubSubject);
            tvDate = itemView.findViewById(R.id.tvDate);
            itemView.setOnClickListener(this);
            this.testItmeClickListener = testItmeClickListener;
        }


        @Override
        public void onClick(View v) {
            testItmeClickListener.onTestItmeClick(getAdapterPosition());
        }
    }


    public interface TestItmeClickListener {
        void onTestItmeClick(int position);

    }

}
