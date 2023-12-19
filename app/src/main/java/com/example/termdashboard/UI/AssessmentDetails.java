package com.example.termdashboard.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AssessmentDetails extends AppCompatActivity {

    int assessmentID;
    String assessmentName;
    int assessmentType;
    int courseID;
    String startAssessmentDate;
    String endAssessmentDate;
    String startCourseDate;
    String endCourseDate;

   // EditText editAssessmentID;
    EditText editAssessmentName;
   // EditText editCourseID;

    TextView editStartAssessmentDate;
    TextView editEndAssessmentDate;

    Spinner assessmentSpinner;
    ArrayList<String> assessmentArrayList = new ArrayList<>();

    Assessment currentAssessment;
    Repository repository;
    DatePickerDialog.OnDateSetListener startDateClick;
    DatePickerDialog.OnDateSetListener endDateClick;
    final Calendar myCalendarAssessmentStart = Calendar.getInstance();
    final Calendar myCalendarAssessmentEnd = Calendar.getInstance();

    String myFormat = "MM/dd/yy"; //In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        repository = new Repository(getApplication());

        //editAssessmentID=findViewById(R.id.editAssessmentID);
        editAssessmentName=findViewById(R.id.editAssessmentName);
        //editCourseID=findViewById(R.id.editCourseID);
        assessmentSpinner=findViewById(R.id.assessmentSpinner);
        editStartAssessmentDate=findViewById(R.id.editStartAssessmentDate);
        editEndAssessmentDate=findViewById(R.id.editEndAssessmentDate);

        ArrayAdapter<String> assessmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, assessmentArrayList);
        assessmentArrayList.add("OA");
        assessmentArrayList.add("PA");

        assessmentSpinner.setAdapter(assessmentAdapter);
        assessmentSpinner.setSelection(0);

        assessmentID = getIntent().getIntExtra("assessmentID", -1);
        assessmentName = getIntent().getStringExtra("assessmentName");
        assessmentType  = getIntent().getIntExtra("assessmentType", -1);
        courseID = getIntent().getIntExtra("courseID", -1);

        startAssessmentDate = getIntent().getStringExtra("startAssessmentDate");
        endAssessmentDate = getIntent().getStringExtra("endAssessmentDate");

        startCourseDate = getIntent().getStringExtra("startCourseDate");
        endCourseDate = getIntent().getStringExtra("endCourseDate");

        //editAssessmentID.setText(String.valueOf(assessmentID));
        editAssessmentName.setText(assessmentName);
        //editCourseID.setText(String.valueOf(courseID));
        editStartAssessmentDate.setText(startAssessmentDate);
        editEndAssessmentDate.setText(endAssessmentDate);

        //editNote=findViewById(R.id.editNote);

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


        if(assessmentType == 0){
            assessmentSpinner.setSelection(0);
        }
        if(assessmentType == 1){
            assessmentSpinner.setSelection(1);
        }

        editStartAssessmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info=editStartAssessmentDate.getText().toString();
                if(info.equals(""))info= LocalDate.now().toString();
                try{
                    myCalendarAssessmentStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetails.this, startDateClick, myCalendarAssessmentStart
                        .get(Calendar.YEAR), myCalendarAssessmentStart.get(Calendar.MONTH),
                        myCalendarAssessmentStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDateClick = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarAssessmentStart.set(Calendar.YEAR, year);
                myCalendarAssessmentStart.set(Calendar.MONTH, monthOfYear);
                myCalendarAssessmentStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };

        editEndAssessmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info=editEndAssessmentDate.getText().toString();
                if(info.equals(""))info=LocalDate.now().toString();
                try{
                    myCalendarAssessmentEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetails.this, endDateClick, myCalendarAssessmentEnd
                        .get(Calendar.YEAR), myCalendarAssessmentEnd.get(Calendar.MONTH),
                        myCalendarAssessmentEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDateClick = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarAssessmentEnd.set(Calendar.YEAR, year);
                myCalendarAssessmentEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarAssessmentEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };

        /*
        Spinner spinner=findViewById(R.id.spinner);
        ArrayList<Course> courseArrayList = new ArrayList<>();

        courseArrayList.addAll(repository.getmAllCourses());

        ArrayAdapter<Course> courseAdapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courseArrayList);
        spinner.setAdapter(courseAdapter);
        spinner.setSelection(0);

        */
    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editStartAssessmentDate.setText(sdf.format(myCalendarAssessmentStart.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editEndAssessmentDate.setText(sdf.format(myCalendarAssessmentEnd.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_assessment_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== R.id.assessmentSave)
        {
            /*

            Date myStartCourseDate = null;
            try {
                myStartCourseDate = sdf.parse(startCourseDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Date myEndCourseDate = null;
            try {
                myEndCourseDate = sdf.parse(endCourseDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (myCalendarAssessmentEnd.getTime().before(myCalendarAssessmentStart.getTime()))
            {
                Toast.makeText(AssessmentDetails.this, "End date cannot be before start date."  + " (" + editStartAssessmentDate.getText() + ")", Toast.LENGTH_LONG).show();
                return true;
            }

            if (myCalendarAssessmentStart.getTime().before(myStartCourseDate))
            {
                Toast.makeText(AssessmentDetails.this, "Start date cannot be before course start date."  + " (" + startCourseDate + ")", Toast.LENGTH_LONG).show();
                return true;
            }

            if (myCalendarAssessmentStart.getTime().after(myEndCourseDate))
            {
                Toast.makeText(AssessmentDetails.this, "Start date cannot be after course end date."  + " (" + endCourseDate + ")", Toast.LENGTH_LONG).show();
                return true;
            }

            if (myCalendarAssessmentEnd.getTime().after(myEndCourseDate))
            {
                Toast.makeText(AssessmentDetails.this, "End date cannot be after course end date." + " (" + endCourseDate + ")" , Toast.LENGTH_LONG).show();
                return true;
            }*/
            Assessment assessment;
            if (assessmentID==-1){
                if (repository.getmAllAssessments().size() == 0) assessmentID = 1;
                else assessmentID = repository.getmAllAssessments().get(repository.getmAllAssessments().size() - 1).getAssessmentID() + 1;

                assessment = new Assessment(assessmentID, editAssessmentName.getText().toString(), assessmentSpinner.getSelectedItemPosition(), courseID,
                        editStartAssessmentDate.getText().toString(), editEndAssessmentDate.getText().toString(), startCourseDate, endCourseDate);
                repository.insert(assessment);
                this.finish();
            }

            else{
                assessment = new Assessment(assessmentID, editAssessmentName.getText().toString(), assessmentSpinner.getSelectedItemPosition(), courseID,
                        editStartAssessmentDate.getText().toString(), editEndAssessmentDate.getText().toString(), startCourseDate, endCourseDate);
                repository.update(assessment);
                this.finish();
            }
            return true;
        }
        if (item.getItemId() == R.id.assessmentDelete){
            if(assessmentID == -1)
            {
                Toast.makeText(AssessmentDetails.this, "Select an assessment to delete it.", Toast.LENGTH_LONG).show();
                return true;
            }
            for(Assessment assessment:repository.getmAllAssessments()){
                if(assessment.getAssessmentID()==assessmentID)currentAssessment=assessment;
            }
                repository.delete(currentAssessment);
                Toast.makeText(AssessmentDetails.this, "\"" + currentAssessment.getAssessmentName() + "\""  + " was deleted", Toast.LENGTH_LONG).show();
                AssessmentDetails.this.finish();
        }

        if (item.getItemId() == R.id.notify) {
            /*
            Date myStartCourseDate = null;
            try {
                myStartCourseDate = sdf.parse(startCourseDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Date myEndCourseDate = null;
            try {
                myEndCourseDate = sdf.parse(endCourseDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (myCalendarAssessmentEnd.getTime().before(myCalendarAssessmentStart.getTime()))
            {
                Toast.makeText(AssessmentDetails.this, "End date cannot be before start date."  + " (" + editStartAssessmentDate.getText() + ")", Toast.LENGTH_LONG).show();
                return true;
            }

            if (myCalendarAssessmentStart.getTime().before(myStartCourseDate))
            {
                Toast.makeText(AssessmentDetails.this, "Start date cannot be before course start date."  + " (" + startCourseDate + ")", Toast.LENGTH_LONG).show();
                return true;
            }

            if (myCalendarAssessmentStart.getTime().after(myEndCourseDate))
            {
                Toast.makeText(AssessmentDetails.this, "Start date cannot be after course end date."  + " (" + endCourseDate + ")", Toast.LENGTH_LONG).show();
                return true;
            }

            if (myCalendarAssessmentEnd.getTime().after(myEndCourseDate))
            {
                Toast.makeText(AssessmentDetails.this, "End date cannot be after course end date." + " (" + endCourseDate + ")" , Toast.LENGTH_LONG).show();
                return true;
            }*/

            String dateFromScreen = editStartAssessmentDate.getText().toString();

            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Long trigger = myDate.getTime();
            Intent intent = new Intent(AssessmentDetails.this, MyReceiver.class);
            intent.putExtra("key", "\"" + assessmentName + "\"" + " assessment begins.");
            PendingIntent sender=PendingIntent.getBroadcast(AssessmentDetails.this, ++MainActivity.numAlert, intent,PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger ,sender);

            dateFromScreen = editEndAssessmentDate.getText().toString();

            myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Long trigger2 = myDate.getTime();
            Intent intent2 = new Intent(AssessmentDetails.this, MyReceiver.class);
            intent2.putExtra("key", "\"" + assessmentName + "\"" + " assessment ends.");
            sender=PendingIntent.getBroadcast(AssessmentDetails.this, ++MainActivity.numAlert, intent2,PendingIntent.FLAG_IMMUTABLE);
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger2 ,sender);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}