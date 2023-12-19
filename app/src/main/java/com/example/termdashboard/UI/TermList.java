package com.example.termdashboard.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.termdashboard.R;
import com.example.termdashboard.database.Repository;
import com.example.termdashboard.entities.Assessment;
import com.example.termdashboard.entities.Course;
import com.example.termdashboard.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TermList extends AppCompatActivity {
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        FloatingActionButton fab=findViewById(R.id.floatingActionButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TermList.this, TermDetails.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView=findViewById(R.id.termRecyclerView);
        repository=new Repository(getApplication());
        List<Term> allTerms=repository.getmAllTerms();
        final TermAdapter termAdapter=new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);

        System.out.println(getIntent().getStringExtra("test"));
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_term_list, menu);
        return true;
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView=findViewById(R.id.termRecyclerView);
        repository=new Repository(getApplication());
        List<Term> allTerms=repository.getmAllTerms();
        final TermAdapter termAdapter=new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);
    }
}