package com.example.termdashboard.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TermDetails extends AppCompatActivity {

    int termID;
    String termName;
    String startTermDate;
    String endTermDate;

    //TextView editTermID;
    EditText editTermName;

    TextView editStartTermDate;

    TextView editEndTermDate;


    private Repository repository;
    DatePickerDialog.OnDateSetListener startDateClick;
    DatePickerDialog.OnDateSetListener endDateClick;

    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    Term currentTerm;
    int numCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        //editTermID=findViewById(R.id.editTermID);
        editTermName=findViewById(R.id.editTermName);
        editStartTermDate=findViewById(R.id.editStartTermDate);
        editEndTermDate=findViewById(R.id.editEndTermDate);

        termID = getIntent().getIntExtra("termID", -1);
        termName = getIntent().getStringExtra("termName");
        startTermDate = getIntent().getStringExtra("startTermDate");
        endTermDate = getIntent().getStringExtra("endTermDate");

        //editTermID.setText(String.valueOf(termID));
        editTermName.setText(termName);
        editStartTermDate.setText(startTermDate);
        editEndTermDate.setText(endTermDate);

        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editStartTermDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date;

                String info=editStartTermDate.getText().toString();
                if(info.equals(""))info= LocalDate.now().toString();
                try{
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetails.this, startDateClick, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startDateClick = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };

        editEndTermDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Date date;
                //get value from other screen,but I'm going to hard code it right now
                String info=editEndTermDate.getText().toString();
                if(info.equals(""))info=LocalDate.now().toString();
                try{
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetails.this, endDateClick, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDateClick = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };

        RecyclerView recyclerView=findViewById(R.id.courseRecyclerView);
        repository=new Repository(getApplication());

        final CourseAdapter courseAdapter=new CourseAdapter(this);

        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*
        List<Course> filteredCourses = new ArrayList<>();
        for (Course c : repository.getmAllCourses()) {
            if (c.getTermID() == termID) filteredCourses.add(c);
        }

        courseAdapter.setCourses(filteredCourses);
        */

        List<Course> associatedCourses = new ArrayList<>();
        for (Course c : repository.getmAssociatedCourses(termID))
        {
            associatedCourses.add(c);
        }
        courseAdapter.setCourses(associatedCourses);

        FloatingActionButton fab=findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(termID > 0) {
                    Intent intent = new Intent(TermDetails.this, CourseDetails.class);
                    intent.putExtra("termID", termID);
                    intent.putExtra("startTermDate", startTermDate);
                    intent.putExtra("endTermDate", endTermDate);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(TermDetails.this, "Select a term first to add a course.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editStartTermDate.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editEndTermDate.setText(sdf.format(myCalendarEnd.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_term_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        /*
        if (myCalendarEnd.getTime().before(myCalendarStart.getTime()))
        {
            Toast.makeText(TermDetails.this, "End date cannot be before start date. " + "(" + editStartTermDate.getText() + ")", Toast.LENGTH_LONG).show();
            return true;
        } */

        if(item.getItemId()== R.id.termSave){
            Term term;
            if (termID==-1){
                if (repository.getmAllTerms().size() == 0) termID = 1;
                else termID = repository.getmAllTerms().get(repository.getmAllTerms().size() - 1).getTermID() + 1;
                term = new Term(termID, editTermName.getText().toString(), editStartTermDate.getText().toString(), editEndTermDate.getText().toString());
                repository.insert(term);
                this.finish();
            }
            else{
                term = new Term(termID, editTermName.getText().toString(), editStartTermDate.getText().toString(), editEndTermDate.getText().toString());
                repository.update(term);
                this.finish();
            }
        }
        if(item.getItemId()== R.id.termDelete){

            if(termID == -1)
            {
                Toast.makeText(TermDetails.this, "Select a term to delete it.", Toast.LENGTH_LONG).show();
                return true;
            }
            for(Term term:repository.getmAllTerms()){
                if(term.getTermID()==termID)currentTerm=term;
            }
            numCourses = 0;
            for(Course course: repository.getmAllCourses()){
                if(course.getTermID()==termID)++numCourses;
            }
            if(numCourses==0){
                repository.delete(currentTerm);
                Toast.makeText(TermDetails.this, "\"" + currentTerm.getTermName() + "\"" + " was deleted", Toast.LENGTH_LONG).show();
                TermDetails.this.finish();
            }
            else{
                Toast.makeText(TermDetails.this, "Cannot delete a term with courses.", Toast.LENGTH_LONG).show();
            }
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView=findViewById(R.id.courseRecyclerView);
        repository=new Repository(getApplication());

        final CourseAdapter courseAdapter=new CourseAdapter(this);

        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Course> filteredCourses = new ArrayList<>();
        for (Course c : repository.getmAllCourses()) {
            if (c.getTermID() == termID) filteredCourses.add(c);
        }

        courseAdapter.setCourses(filteredCourses);
    }
}