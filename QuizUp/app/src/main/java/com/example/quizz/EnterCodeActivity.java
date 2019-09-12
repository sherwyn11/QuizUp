package com.example.quizz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizz.Activity2;
import com.example.quizz.R;

import org.w3c.dom.Text;

public class EnterCodeActivity extends AppCompatActivity {

    private TextView codevalue;
    private Button buttonnext;

    private String Roll_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_code);

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
        Intent intent = new Intent(this, Activity2.class);
        intent.putExtra("Roll_no",Roll_no);
        startActivity(intent);
    }
}
