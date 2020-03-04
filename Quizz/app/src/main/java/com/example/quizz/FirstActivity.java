package com.example.quizz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class FirstActivity extends AppCompatActivity {
    private Button button1;
    private Button button2;
    private String check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        button1=findViewById(R.id.student);
        button2=findViewById(R.id.teacher);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = "1";
                openActivityMain();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = "0";
                openActivityMain();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FirstActivity.this);
        alertDialogBuilder.setMessage("Quit application?");
        alertDialogBuilder.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
        alertDialogBuilder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        finishAffinity();
                        System.exit(0);
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void openActivityMain(){
        Intent intent= new Intent(this, MainActivity.class);
        intent.putExtra("check",check);
        Log.d("checkll",check);
        startActivity(intent);
    }
}
