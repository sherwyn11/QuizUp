package com.example.quizz;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EndActivity extends AppCompatActivity {
    private TextView finalscore;
    private Button end;
    private ProgressDialog progressDialog;

    private String Roll_no;
    private String score;
    private String quiz_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        end=findViewById(R.id.end_quiz);
        finalscore=findViewById(R.id.score_val);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        Intent intent = getIntent();
        score =intent.getStringExtra("score");
        Roll_no = intent.getStringExtra("Roll_no");
        quiz_name = intent.getStringExtra("quiz_name");

        Log.d("Test", "Test4 passed");


        Log.d("ROOLLLL",Roll_no);

        finalscore.setText(score);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFirstActivity();
            }
        });

        saveScore();

    }

    private void saveScore(){
        final String roll = SharedPrefManager.getInstance(getApplicationContext()).getRollNo();
        final String scr = score;
        final String quizName = quiz_name;

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_SET,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                Toast.makeText(getApplicationContext(),"Result is stored.. Thank you for taking the quiz !",Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("roll_no",roll);
                params.put("score",scr);
                params.put("quiz_name", quizName);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void onBackPressed() {

        Toast.makeText(EndActivity.this, "Please select END", Toast.LENGTH_SHORT).show();

    }

    public void openFirstActivity(){
        Intent intent= new Intent(this, StudentChooseActivity.class);
        startActivity(intent);
    }
}
