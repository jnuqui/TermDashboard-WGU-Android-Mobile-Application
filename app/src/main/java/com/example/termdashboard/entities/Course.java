package com.example.termdashboard.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseID;
    private String courseName;
    private int termID;
    private String startCourseDate;
    private String endCourseDate;
    private int status;
    private String instructorName;
    private String instructorPhone;
    private String instructorEmail;
    private String note;
    private String startTermDate;
    private String endTermDate;

    public Course(int courseID, String courseName, int termID, String startCourseDate, String endCourseDate,
                  int status, String instructorName,
                  String instructorPhone, String instructorEmail, String note, String startTermDate, String endTermDate) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.termID = termID;
        this.startCourseDate = startCourseDate;
        this.endCourseDate = endCourseDate;
        this.status = status;
        this.instructorName = instructorName;
        this.instructorPhone = instructorPhone;
        this.instructorEmail = instructorEmail;
        this.note = note;
        this.startTermDate = startTermDate;
        this.endTermDate = endTermDate;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorPhone() {
        return instructorPhone;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStartTermDate() {
        return startTermDate;
    }

    public void setStartTermDate(String startTermDate) {
        this.startTermDate = startTermDate;
    }

    public String getEndTermDate() {
        return endTermDate;
    }

    public void setEndTermDate(String endTermDate) {
        this.endTermDate = endTermDate;
    }

    public String toString(){
        return courseName;
    }
}
