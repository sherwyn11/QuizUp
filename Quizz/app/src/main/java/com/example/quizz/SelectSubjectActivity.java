package com.example.quizz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class SelectSubjectActivity extends AppCompatActivity {

    private Spinner spinner, spinner1;
    private String subject, quiz;
    ProgressDialog progressDialog;
    ArrayList<String> quizzes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_subject);

        spinner = findViewById(R.id.spinner_sub);
        spinner1 = findViewById(R.id.spinner_1);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.subjects, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subject = parent.getItemAtPosition(position).toString();
                getQuizes();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getQuizes() {

        quizzes.clear();

        final String year = SharedPrefManager.getInstance(getApplicationContext()).getUserYear();
        final String branch = SharedPrefManager.getInstance(getApplicationContext()).getUserBranch();
        final String sub = subject;

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_GET_QUIZ_NAMES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            Log.d("dataValues", jsonArray.toString());
                            if(!jsonArray.getBoolean(1)){
                                for(int i = 2; i < jsonArray.getInt(0) + 2; i++){
                                    quizzes.add(jsonArray.getString(i));
                                }
                                ArrayAdapter<String> adapter =
                                        new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, quizzes);
                                adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                                spinner1.setAdapter(adapter);
                                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        Toast.makeText(getApplicationContext(), quizzes.get(position), Toast.LENGTH_LONG).show();
                                        quiz = quizzes.get(position);
                                        openNext();
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
                params.put("subject", sub);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void openNext() {
        Intent intent = new Intent(this, ScoresActivity.class);
        intent.putExtra("quiz_name", quiz);
        startActivity(intent);
    }


}
