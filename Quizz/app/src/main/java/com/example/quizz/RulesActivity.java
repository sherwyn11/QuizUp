package com.example.quizz;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RulesActivity extends AppCompatActivity {

    private TextView codevalue;
    private Button buttonnext;

    private String Roll_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        Intent intent = getIntent();
        Roll_no = intent.getStringExtra("roll_no");
        Log.d("ROll no",Roll_no);

        buttonnext=findViewById(R.id.enterquiz);
        buttonnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });

    }
    public void openActivity2(){
        Intent intent = new Intent(this, QuizMainActivity.class);
        intent.putExtra("Roll_no",Roll_no);
        startActivity(intent);
    }
}
