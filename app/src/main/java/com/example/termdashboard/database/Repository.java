package com.example.termdashboard.database;

import android.app.Application;

import com.example.termdashboard.dao.AssessmentDAO;
import com.example.termdashboard.dao.CourseDAO;
import com.example.termdashboard.dao.TermDAO;
import com.example.termdashboard.entities.Assessment;
import com.example.termdashboard.entities.Course;
import com.example.termdashboard.entities.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private TermDAO mTermDAO;
    private AssessmentDAO mAssessmentDAO;
    private CourseDAO mCourseDAO;

    private List<Term> mAllTerms;
    private List<Course> mAllCourses;
    private List<Assessment> mAllAssessments;

    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        ScheduleDatabaseBuilder db=ScheduleDatabaseBuilder.getDatabase(application);
        mTermDAO=db.termDAO();
        mAssessmentDAO=db.assessmentDAO();
        mCourseDAO=db.courseDAO();
    }

    public List<Term>getmAllTerms(){
        databaseExecutor.execute(()->{
            mAllTerms=mTermDAO.getAllTerms();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllTerms;
    }

    public void insert (Term term){
        databaseExecutor.execute(()-> {
            mTermDAO.insert(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update (Term term){
        databaseExecutor.execute(()-> {
            mTermDAO.update(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete (Term term){
        databaseExecutor.execute(()-> {
            mTermDAO.delete(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Course>getmAllCourses(){
        databaseExecutor.execute(()->{
            mAllCourses=mCourseDAO.getAllCourses();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllCourses;
    }

    public List<Course>getmAssociatedCourses(int termID) {
        databaseExecutor.execute(() -> {
            mAllCourses = mCourseDAO.getAssociatedCourses(termID);
        });

        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
        return mAllCourses;
    }
    
    public void insert (Course course){
        databaseExecutor.execute(()-> {
            mCourseDAO.insert(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update (Course course){
        databaseExecutor.execute(()-> {
            mCourseDAO.update(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete (Course course){
        databaseExecutor.execute(()-> {
            mCourseDAO.delete(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Assessment>getmAllAssessments() {
        databaseExecutor.execute(() -> {
            mAllAssessments = mAssessmentDAO.getAllAssessments();
        });

        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }

        return mAllAssessments;
    }


    public List<Assessment>getmAssociatedAssessments(int courseID) {
        databaseExecutor.execute(() -> {
            mAllAssessments = mAssessmentDAO.getAssociatedAssessments(courseID);
        });

        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
        return mAllAssessments;
    }

    public void insert (Assessment assessment){
        databaseExecutor.execute(()-> {
            mAssessmentDAO.insert(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update (Assessment assessment){
        databaseExecutor.execute(()-> {
            mAssessmentDAO.update(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete (Assessment assessment){
        databaseExecutor.execute(()-> {
            mAssessmentDAO.delete(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}