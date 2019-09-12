package com.example.quizz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.quizz.CodeActivity;
import com.example.quizz.R;

public class TeacherActivity extends AppCompatActivity {

    private Button startQuiz;
    private Button studentDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        
        startQuiz=findViewById(R.id.quiz_start);
        studentDatabase = findViewById(R.id.student_database);
        
        studentDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatabase();
            }
        });
        
        startQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHotspot();
            }
        });
    }

    private void openDatabase() {
        Intent intent = new Intent(this, DatabaseActivity.class);
        startActivity(intent);
    }

    public void startHotspot(){
        Intent intent = new Intent(this, CodeActivity.class);
        startActivity(intent);
    }
}