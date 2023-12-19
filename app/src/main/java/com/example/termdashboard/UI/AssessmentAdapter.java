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
import com.example.termdashboard.entities.Assessment;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;

    class AssessmentViewHolder extends RecyclerView.ViewHolder {

        private final TextView assessmentItemView;
        //private final TextView assessmentItemView2;

        public AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.textView2);
            //assessmentItemView2 = itemView.findViewById(R.id.textView3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessment current = mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetails.class);
                    intent.putExtra("assessmentID", current.getAssessmentID());
                    intent.putExtra("assessmentName", current.getAssessmentName());
                    intent.putExtra("assessmentType", current.getAssessmentType());
                    intent.putExtra("courseID", current.getCourseID());
                    intent.putExtra("startAssessmentDate", current.getStartAssessmentDate());
                    intent.putExtra("endAssessmentDate", current.getEndAssessmentDate());
                    intent.putExtra("startCourseDate", current.getStartCourseDate());
                    intent.putExtra("endCourseDate", current.getEndCourseDate());
                    context.startActivity(intent);
                }
            });
        }
    }
        public AssessmentAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            this.context = context;
        }

        @Override
        public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
            return new AssessmentViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        if(mAssessments!=null){
            Assessment current=mAssessments.get(position);
            String name = current.getAssessmentName();
            int courseID= current.getCourseID();
            holder.assessmentItemView.setText(name);
            //holder.assessmentItemView2.setText(Integer.toString(courseID));
        }
        else{
            holder.assessmentItemView.setText("No assessment name");
            //holder.assessmentItemView2.setText("No course ID");
        }
        }

        public void setAssessments(List<Assessment> assessments){
        mAssessments=assessments;
        notifyDataSetChanged();
        }
        public int getItemCount(){
            if(mAssessments!=null){
            return mAssessments.size();
            }
            else return 0;
        }
    }

