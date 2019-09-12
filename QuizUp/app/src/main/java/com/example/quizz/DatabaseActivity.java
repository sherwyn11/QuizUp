package com.example.quizz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
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

public class DatabaseActivity extends AppCompatActivity {

    private RecyclerView tRecyclerView;
    private StudentAdapter tAdapter;
    private RecyclerView.LayoutManager tLayoutManager;

    private ProgressDialog progressDialog;
    ArrayList<JSONArray> events = new ArrayList<>();
    ArrayList<StudentItem> studentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        tRecyclerView = findViewById(R.id.recyclerView);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");


        printDatabase();
    }

    private void printDatabase(){

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_OPEN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray obj = new JSONArray(response);
                            if(obj.getString(1).equals("false")){
                                for(int i = 2 ; i < Integer.parseInt(obj.getString(0))+2 ; i++ ){
                                    JSONArray a = obj.getJSONArray(i);
                                    events.add(a);
                                }
                                makeCards();
                            }else{
                                Toast.makeText(getApplicationContext(),"No records",Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                openHome();
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
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

    private void openHome() {
        Intent i = new Intent(this,TeacherActivity.class);
        startActivity(i);
    }

    private void makeCards() {

        JSONArray obj;
        final ArrayList<StudentItem> eventdes = new ArrayList<>();

        tRecyclerView.setHasFixedSize(true);
        tLayoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL,false);

        studentList = new ArrayList<>();

        Log.d("SIZEEE",Integer.toString(events.size()));

        for(int i = 0 ; i < events.size() ; i++){
            obj = events.get(i);
            try {
                studentList.add(new StudentItem(obj.getString(0),obj.getString(1)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        tAdapter = new StudentAdapter(studentList);
        tRecyclerView.setLayoutManager(tLayoutManager);
        tRecyclerView.setAdapter(tAdapter);

        progressDialog.dismiss();

        tAdapter.setOnItemClickListener(new StudentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                final JSONArray obj = events.get(position);
                try {
                    String event = obj.getString(1);
                    openFrom(events.get(position));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void openFrom(JSONArray obj) {

        Intent intent1 = new Intent(this, GraphActivity.class);
        try {
            intent1.putExtra("roll_no",obj.getString(0));
            Log.d("Roll_no",obj.getString(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        startActivity(intent1);

    }
}
