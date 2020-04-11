package com.example.quizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class StudentChooseActivity extends AppCompatActivity {

    private Button button1, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_choose);

        button1 = findViewById(R.id.give_quiz);
        button2 = findViewById(R.id.see_scores);

        Log.d("Test", "Test2 passed");

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNext(0);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNext(1);
            }
        });
    }

    void openNext(int check){
        if(check == 0){
            Intent intent = new Intent(this, SelectQuizActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, SelectSubjectActivity.class);
            startActivity(intent);
        }
    }
}
