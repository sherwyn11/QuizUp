package com.example.quizz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SelectQuizActivity extends AppCompatActivity{

    ProgressDialog progressDialog;
    ArrayList<String> subjects = new ArrayList<>();
    private Spinner spinner;
    private Button next_act;
    private String subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_quiz);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        spinner = findViewById(R.id.spinner1);
        next_act = findViewById(R.id.next);

        next_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNext();
            }
        });
        getSubjects();
    }

    private void openNext() {
        Intent intent= new Intent(this, QuizMainActivity.class);
        startActivity(intent);
    }

    private void getSubjects(){
        final String year = SharedPrefManager.getInstance(getApplicationContext()).getUserYear();
        final String branch = SharedPrefManager.getInstance(getApplicationContext()).getUserBranch();

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_GET_SUBJECTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if(!jsonArray.getBoolean(1)){
                                for(int i = 2; i < jsonArray.getInt(0) + 2; i++){
                                    subjects.add(jsonArray.getString(i));
                                }
                                ArrayAdapter<String> adapter =
                                        new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, subjects);
                                adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                                spinner.setAdapter(adapter);
                                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        Toast.makeText(getApplicationContext(), subjects.get(position), Toast.LENGTH_LONG).show();
                                        subject = subjects.get(position);
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }else{
                                Toast.makeText(getApplicationContext(),jsonArray.getString(0),Toast.LENGTH_LONG).show();
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
                params.put("year", year);
                params.put("branch", branch);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }


}
