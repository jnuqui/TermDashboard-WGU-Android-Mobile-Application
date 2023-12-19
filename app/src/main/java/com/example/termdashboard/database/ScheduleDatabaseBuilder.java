package com.example.termdashboard.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.termdashboard.dao.AssessmentDAO;
import com.example.termdashboard.dao.CourseDAO;
import com.example.termdashboard.dao.TermDAO;
import com.example.termdashboard.entities.Assessment;
import com.example.termdashboard.entities.Course;
import com.example.termdashboard.entities.Term;

@Database(entities = {Term.class, Course.class, Assessment.class}, version= 1, exportSchema=false)
public abstract class ScheduleDatabaseBuilder extends RoomDatabase {

    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();
    private static volatile ScheduleDatabaseBuilder INSTANCE;

    static ScheduleDatabaseBuilder getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (ScheduleDatabaseBuilder.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(), ScheduleDatabaseBuilder.class, "MyScheduleDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
