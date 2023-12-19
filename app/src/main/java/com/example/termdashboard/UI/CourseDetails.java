package com.example.termdashboard.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.termdashboard.R;
import com.example.termdashboard.database.Repository;
import com.example.termdashboard.entities.Assessment;
import com.example.termdashboard.entities.Course;
import com.example.termdashboard.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseDetails extends AppCompatActivity {

    int courseID;
    int termID;
    String courseName;
    String startCourseDate;
    String endCourseDate;

    int status;
    String instructorName;
    String instructorPhone;
    String instructorEmail;
    String note;
    String startTermDate;
    String endTermDate;
    EditText editCourseName;
    TextView editStartCourseDate;
    TextView editEndCourseDate;
    EditText editInstructorName;
    EditText editInstructorPhone;
    EditText editInstructorEmail;
    EditText editNote;

    Repository repository;

    DatePickerDialog.OnDateSetListener startDateClick;
    DatePickerDialog.OnDateSetListener endDateClick;
    final Calendar myCalendarCourseStart = Calendar.getInstance();
    final Calendar myCalendarCourseEnd = Calendar.getInstance();

    String myFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    Course currentCourse;
    int numAssessments;

    Spinner spinner;
    //ArrayList<Term> termArrayList = new ArrayList<>();
    ArrayList<String> statusArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        FloatingActionButton fab=findViewById(R.id.floatingActionButton4);

        //editCourseID=findViewById(R.id.editCourseID);
        editCourseName=findViewById(R.id.editCourseName);
        //editTermID=findViewById(R.id.editTermID);
        editStartCourseDate=findViewById(R.id.editStartCourseDate);
        editEndCourseDate=findViewById(R.id.editEndCourseDate);
        spinner=findViewById(R.id.spinner);
        editInstructorName=findViewById(R.id.editInstructorName);
        editInstructorPhone=findViewById(R.id.editInstructorPhone);
        editInstructorEmail=findViewById(R.id.editInstructorEmail);
        editNote=findViewById(R.id.editNote);

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusArrayList);
        statusArrayList.add("In progress");
        statusArrayList.add("Completed");
        statusArrayList.add("Dropped");
        statusArrayList.add("Plan to take");

        spinner.setAdapter(statusAdapter);
        spinner.setSelection(0);

        courseID = getIntent().getIntExtra("courseID", -1);
        termID = getIntent().getIntExtra("termID", -1);
        courseName = getIntent().getStringExtra("courseName");
        startCourseDate = getIntent().getStringExtra("startCourseDate");
        endCourseDate = getIntent().getStringExtra("endCourseDate");
        status = getIntent().getIntExtra("status", -1);
        instructorName = getIntent().getStringExtra("instructorName");
        instructorPhone = getIntent().getStringExtra("instructorPhone");
        instructorEmail = getIntent().getStringExtra("instructorEmail");
        note = getIntent().getStringExtra("note");

        startTermDate = getIntent().getStringExtra("startTermDate");
        endTermDate = getIntent().getStringExtra("endTermDate");

        //editCourseID.setText(String.valueOf(courseID));
        editCourseName.setText(courseName);
        //editTermID.setText(String.valueOf(termID));
        editStartCourseDate.setText(startCourseDate);
        editEndCourseDate.setText(endCourseDate);

        if(status == 0){
            spinner.setSelection(0);
        }
        if(status == 1){
            spinner.setSelection(1);
        }
        if(status == 2){
            spinner.setSelection(2);
        }
        if(status == 3){
            spinner.setSelection(3);
        }

        editInstructorName.setText(instructorName);
        editInstructorPhone.setText(instructorPhone);
        editInstructorEmail.setText(instructorEmail);
        editNote.setText(note);

        editStartCourseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String info=editStartCourseDate.getText().toString();
                if(info.equals(""))info= LocalDate.now().toString();
                try{
                    myCalendarCourseStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetails.this, startDateClick, myCalendarCourseStart
                        .get(Calendar.YEAR), myCalendarCourseStart.get(Calendar.MONTH),
                        myCalendarCourseStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDateClick = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarCourseStart.set(Calendar.YEAR, year);
                myCalendarCourseStart.set(Calendar.MONTH, monthOfYear);
                myCalendarCourseStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };

        editEndCourseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info=editEndCourseDate.getText().toString();
                if(info.equals(""))info=LocalDate.now().toString();
                try{
                    myCalendarCourseEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetails.this, endDateClick, myCalendarCourseEnd
                        .get(Calendar.YEAR), myCalendarCourseEnd.get(Calendar.MONTH),
                        myCalendarCourseEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDateClick = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarCourseEnd.set(Calendar.YEAR, year);
                myCalendarCourseEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarCourseEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (courseID > 0) {
                    Intent intent = new Intent(CourseDetails.this, AssessmentDetails.class);
                    intent.putExtra("courseID", courseID);
                    intent.putExtra("startCourseDate", startCourseDate);
                    intent.putExtra("endCourseDate", endCourseDate);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(CourseDetails.this, "Select a course first to add an assessment.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        repository = new Repository(getApplication());

        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*
        List<Assessment> filteredAssessments = new ArrayList<>();
        for (Assessment a : repository.getmAllAssessments()) {
            if (a.getCourseID() == courseID) filteredAssessments.add(a);
        }
        assessmentAdapter.setAssessments(filteredAssessments);
        */

        List<Assessment> associatedAssessments = new ArrayList<>();
        for (Assessment a : repository.getmAssociatedAssessments(courseID))
        {
            associatedAssessments.add(a);
        }
        assessmentAdapter.setAssessments(associatedAssessments);

        //ArrayList<Term> termArrayList = new ArrayList<>();
        //ArrayAdapter<Term> termAdapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, termArrayList);
        //termArrayList.addAll(repository.getmAllTerms());
        //termAdapter.getItem(spinner.getSelectedItemPosition()).getTermID();

        //Toast.makeText(CourseDetails.this, startTermDate + " is the start " + endTermDate + " is the end", Toast.LENGTH_LONG).show();
    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editStartCourseDate.setText(sdf.format(myCalendarCourseStart.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editEndCourseDate.setText(sdf.format(myCalendarCourseEnd.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_course_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        //ArrayAdapter<Term> termAdapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, termArrayList);
        ArrayAdapter<String> statusAdapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusArrayList);

        if(item.getItemId()== R.id.courseSave){
            Date myStartTermDate = null;
            try {
                myStartTermDate = sdf.parse(startTermDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Date myEndTermDate = null;
            try {
                myEndTermDate = sdf.parse(endTermDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            /*
            if (myCalendarCourseEnd.getTime().before(myCalendarCourseStart.getTime()) && (myCalendarCourseEnd.getTime().getDay() < myCalendarCourseStart.getTime().getDay()))
            {
                Toast.makeText(CourseDetails.this, "End date cannot be before start date."  + " (" + editStartCourseDate.getText() + ")", Toast.LENGTH_LONG).show();
                return true;
            }

            if (myCalendarCourseStart.getTime().before(myStartTermDate) && (myCalendarCourseEnd.getTime().getDay() < myStartTermDate.getDay()))
            {
                Toast.makeText(CourseDetails.this, "Start date cannot be before term start date." + " (" + startTermDate + ")", Toast.LENGTH_LONG).show();
                return true;
            }

            if (myCalendarCourseStart.getTime().after(myEndTermDate))
            {
                Toast.makeText(CourseDetails.this, "Start date cannot be after term end date."  + " (" + endTermDate + ")", Toast.LENGTH_LONG).show();
                return true;
            }

            if (myCalendarCourseEnd.getTime().after(myEndTermDate))
            {
                Toast.makeText(CourseDetails.this, "End date cannot be after term end date."   + " (" + endTermDate + ")", Toast.LENGTH_LONG).show();
                return true;
            }*/
            Course course;
            if (courseID==-1){
                if (repository.getmAllCourses().size() == 0) courseID = 1;
                else courseID = repository.getmAllCourses().get(repository.getmAllCourses().size() - 1).getCourseID() + 1;
                course = new Course(courseID, editCourseName.getText().toString(),
                        termID,
                        editStartCourseDate.getText().toString(), editEndCourseDate.getText().toString(),
                        (int) statusAdapter.getItemId(spinner.getSelectedItemPosition()),
                        editInstructorName.getText().toString(), editInstructorPhone.getText().toString(),
                        editInstructorEmail.getText().toString(),
                        editNote.getText().toString(), startTermDate, endTermDate);
            repository.insert(course);
            this.finish();
            }
            else{
                course = new Course(courseID, editCourseName.getText().toString(),
                        termID,
                        editStartCourseDate.getText().toString(), editEndCourseDate.getText().toString(),
                        (int) statusAdapter.getItemId(spinner.getSelectedItemPosition()),
                        editInstructorName.getText().toString(), editInstructorPhone.getText().toString(),
                        editInstructorEmail.getText().toString(),
                        editNote.getText().toString(), startTermDate, endTermDate);
                repository.update(course);
                this.finish();
            }
        }
        if(item.getItemId()== R.id.courseDelete){
            if(courseID == -1)
            {
                Toast.makeText(CourseDetails.this, "Select a course to delete it.", Toast.LENGTH_LONG).show();
                return true;
            }
            for(Course course:repository.getmAllCourses()){
                if(course.getCourseID()==courseID)currentCourse=course;
            }
            numAssessments = 0;
            for(Assessment assessment: repository.getmAllAssessments()){
                if(assessment.getCourseID()==courseID)++numAssessments;
            }
            if(numAssessments==0){
                repository.delete(currentCourse);
                Toast.makeText(CourseDetails.this, "\"" + currentCourse.getCourseName() + "\""  + " was deleted", Toast.LENGTH_LONG).show();
                CourseDetails.this.finish();
            }
            else{
                Toast.makeText(CourseDetails.this, "Can't delete a course with assessments.", Toast.LENGTH_LONG).show();
            }
        }
        if (item.getItemId() == R.id.courseShare){
            Intent sendIntent= new Intent();

            //sendIntent.setData(Uri.parse("SMS:"));
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, editNote.getText().toString()+ " EXTRA_TEXT");
            sendIntent.putExtra(Intent.EXTRA_TITLE, editNote.getText().toString()+ " EXTRA_TITLE");
            sendIntent.setType("test/plain");

            Intent shareIntent=Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }
        if (item.getItemId() == R.id.courseNotify) {

            /*
            Date myStartTermDate = null;
            try {
                myStartTermDate = sdf.parse(startTermDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Date myEndTermDate = null;
            try {
                myEndTermDate = sdf.parse(endTermDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            if (myCalendarCourseEnd.getTime().before(myCalendarCourseStart.getTime()))
            {
                Toast.makeText(CourseDetails.this, "End date cannot be before start date."  + " (" + editStartCourseDate.getText() + ")", Toast.LENGTH_LONG).show();
                return true;
            }

            if (myCalendarCourseStart.getTime().before(myStartTermDate))
            {
                Toast.makeText(CourseDetails.this, "Start date cannot be before term start date." + " (" + startTermDate + ")", Toast.LENGTH_LONG).show();
                return true;
            }

            if (myCalendarCourseStart.getTime().after(myEndTermDate))
            {
                Toast.makeText(CourseDetails.this, "Start date cannot be after term end date."  + " (" + endTermDate + ")", Toast.LENGTH_LONG).show();
                return true;
            }

            if (myCalendarCourseEnd.getTime().after(myEndTermDate))
            {
                Toast.makeText(CourseDetails.this, "End date cannot be after term end date."   + " (" + endTermDate + ")", Toast.LENGTH_LONG).show();
                return true;
            }

            */

            String dateFromScreen = editStartCourseDate.getText().toString();
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long trigger = myDate.getTime();
            Intent intent = new Intent(CourseDetails.this, MyReceiver.class);
            intent.putExtra("key", "\"" + courseName + "\"" + " course begins.");
            PendingIntent sender=PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert, intent,PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger ,sender);

            dateFromScreen = editEndCourseDate.getText().toString();

            myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Long trigger2 = myDate.getTime();
            Intent intent2 = new Intent(CourseDetails.this, MyReceiver.class);
            intent2.putExtra("key", "\"" + courseName + "\"" + " course ends.");
            sender=PendingIntent.getBroadcast(CourseDetails.this, ++MainActivity.numAlert, intent2,PendingIntent.FLAG_IMMUTABLE);
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger2 ,sender);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        courseID = getIntent().getIntExtra("courseID", -1);
        courseName = getIntent().getStringExtra("courseName");

        //editCourseID.setText(String.valueOf(courseID));
        editCourseName.setText(courseName);

        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        repository = new Repository(getApplication());
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> filteredAssessments = new ArrayList<>();
        for (Assessment a : repository.getmAllAssessments()) {
            if (a.getCourseID() == courseID) filteredAssessments.add(a);
        }
        assessmentAdapter.setAssessments(filteredAssessments);
    }
}