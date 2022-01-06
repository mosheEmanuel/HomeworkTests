package com.example.homeworktests;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class HomeworkAdapter extends ArrayAdapter<Homework_Test> {

    Context context;
    List<Homework_Test> objects;

    public HomeworkAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Homework_Test> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_layout_homework,parent,false);
        ImageView ivPic = view.findViewById(R.id.ivPic);
        TextView tvQuestions =view.findViewById(R.id.tvQuestions);
        TextView tvPage = view.findViewById(R.id.tvPage);
        TextView tvSubject = view.findViewById(R.id.tvSubject);
        TextView tvDate_of_submission =view.findViewById(R.id.tvDate_of_submission);
        Homework_Test homeworkTest = objects.get(position);

        ivPic.setImageBitmap(homeworkTest.getBitmap());
        tvDate_of_submission.setText(homeworkTest.getDate_of_submission());
        tvPage.setText(homeworkTest.getPage());
        tvSubject.setText(homeworkTest.getSubject());
        tvQuestions.setText(homeworkTest.getQuestions());
        return view;
    }
}
