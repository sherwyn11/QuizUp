package com.example.quizz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GraphActivity extends AppCompatActivity {

    private static final String TAG = "GraphActivity";
    ProgressDialog progressDialog;
    private LineChart mChart;

    private String rollno;

    ArrayList<JSONArray> events  = new ArrayList<>();
    ArrayList<Entry> yValues = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphy);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        mChart = findViewById(R.id.linechart);

        getCardData();

//        mChart.setOnChartGestureListener(GraphActivity.this);
//        mChart.setOnChartValueSelectedListener(GraphActivity.this);


    }

    private void getCardData(){

        Intent intent = getIntent();
        final String roll_no = intent.getStringExtra("roll_no");
        rollno = roll_no;

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_GRAPH,
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
                                Toast.makeText(getApplicationContext(),"message",Toast.LENGTH_LONG).show();
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
                params.put("roll_no",roll_no);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void makeCards() {

        JSONArray obj;
        progressDialog.dismiss();


        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);

        for(int i = 0 ; i < events.size() ; i++){
            obj = events.get(i);
            try {
                Log.d("MARRRRRKKKKKSSSS",obj.getString(1));
                yValues.add(new Entry(i,Float.valueOf(obj.getString(1))));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


//
//        yValues.add(new Entry(0,60f));
//        yValues.add(new Entry(1,70f));
//        yValues.add(new Entry(2,80f));
//        yValues.add(new Entry(3,30f));
//        yValues.add(new Entry(4,20f));
//        yValues.add(new Entry(5,60f));

        LineDataSet set1 = new LineDataSet(yValues,rollno);

        set1.setFillAlpha(110);
        set1.setColor(Color.RED);
        set1.setLineWidth(3f);
        set1.setValueTextSize(10f);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);

        mChart.setData(data);



    }
}
