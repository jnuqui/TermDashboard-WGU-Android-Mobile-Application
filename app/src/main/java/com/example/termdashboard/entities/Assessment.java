package com.example.termdashboard.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessments")
public class Assessment {

    @PrimaryKey(autoGenerate = true)
    private int assessmentID;
    private String assessmentName;
    private int assessmentType;
    private int courseID;
    private String startAssessmentDate;
    private String endAssessmentDate;
    private String startCourseDate;
    private String endCourseDate;

    public Assessment(int assessmentID, String assessmentName, int assessmentType, int courseID, String startAssessmentDate, String endAssessmentDate, String startCourseDate, String endCourseDate) {
        this.assessmentID = assessmentID;
        this.assessmentName = assessmentName;
        this.assessmentType = assessmentType;
        this.courseID = courseID;
        this.startAssessmentDate = startAssessmentDate;
        this.endAssessmentDate = endAssessmentDate;
        this.startCourseDate = startCourseDate;
        this.endCourseDate = endCourseDate;
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public int getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(int assessmentType) {
        this.assessmentType = assessmentType;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getStartAssessmentDate() {
        return startAssessmentDate;
    }

    public void setStartAssessmentDate(String startAssessmentDate) {
        this.startAssessmentDate = startAssessmentDate;
    }

    public String getEndAssessmentDate() {
        return endAssessmentDate;
    }

    public void setEndAssessmentDate(String endAssessmentDate) {
        this.endAssessmentDate = endAssessmentDate;
    }

    public String getStartCourseDate() {
        return startCourseDate;
    }

    public void setStartCourseDate(String startCourseDate) {
        this.startCourseDate = startCourseDate;
    }

    public String getEndCourseDate() {
        return endCourseDate;
    }

    public void setEndCourseDate(String endCourseDate) {
        this.endCourseDate = endCourseDate;
    }
}
