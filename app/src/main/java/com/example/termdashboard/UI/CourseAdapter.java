package com.example.termdashboard.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termdashboard.R;
import com.example.termdashboard.entities.Course;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private List<Course> mCourses;
    private final Context context;

    private final LayoutInflater mInflater;
    public CourseAdapter(Context context){
        mInflater= LayoutInflater.from(context);
        this.context=context;
    }
    public class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    final Course current=mCourses.get(position);
                    Intent intent = new Intent(context,CourseDetails.class);
                    intent.putExtra("courseID", current.getCourseID());
                    intent.putExtra("termID", current.getTermID());
                    intent.putExtra("courseName", current.getCourseName());
                    intent.putExtra("termID", current.getTermID());
                    intent.putExtra("startCourseDate", current.getStartCourseDate());
                    intent.putExtra("endCourseDate", current.getEndCourseDate());
                    intent.putExtra("status", current.getStatus());
                    intent.putExtra("instructorName", current.getInstructorName());
                    intent.putExtra("instructorPhone", current.getInstructorPhone());
                    intent.putExtra("instructorEmail", current.getInstructorEmail());
                    intent.putExtra("note", current.getNote());
                    intent.putExtra("startTermDate", current.getStartTermDate());
                    intent.putExtra("endTermDate", current.getEndTermDate());
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.course_list_item,parent,false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if(mCourses!=null){
            Course current=mCourses.get(position);
            String name=current.getCourseName();
            holder.courseItemView.setText(name);
        }
        else{
            holder.courseItemView.setText("No course name");
        }
    }

    @Override
    public int getItemCount() {
        if(mCourses!=null) {
            return mCourses.size();
        }
        else return 0;
    }

    public void setCourses(List<Course> courses){
        mCourses=courses;
        notifyDataSetChanged();
    }
}
