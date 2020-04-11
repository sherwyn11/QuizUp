package com.example.quizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TeacherChooserActivity extends AppCompatActivity {

    Button csv, take_pic, web_scrape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_chooser);

        csv = findViewById(R.id.csv);
        take_pic = findViewById(R.id.pic);
        web_scrape = findViewById(R.id.web_scrape);
        
        csv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCSV();
            }
        });

        web_scrape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebScrape();
            }
        });
    }

    private void openCSV() {
        Uri uri = Uri.parse("http://127.0.0.1/Quiz/new/newQS.php");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void openWebScrape() {
        Intent intent = new Intent(this, WebScrapeActivity.class);
        startActivity(intent);
    }
}
