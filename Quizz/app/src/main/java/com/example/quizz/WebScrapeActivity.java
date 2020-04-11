package com.example.quizz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebScrapeActivity extends AppCompatActivity {
    TextView text1;
    TextView text2;
    TextView text3;
    TextView text4;
    TextView text5;
    Button btn;
    EditText editText;
    String link;
    private ProgressDialog progressDialog;

    ArrayList<String> questions = new ArrayList<>();
    ArrayList<String> optA = new ArrayList<>();
    ArrayList<String> optB = new ArrayList<>();
    ArrayList<String> optC = new ArrayList<>();
    ArrayList<String> optD = new ArrayList<>();
    ArrayList<String> ans = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_scrape);

        text1 = findViewById(R.id.txt1);
        text2 = findViewById(R.id.txt2);
        text3 = findViewById(R.id.txt3);
        text4 = findViewById(R.id.txt4);
        text5 = findViewById(R.id.txt5);
        btn = findViewById(R.id.btn_1);
        editText = findViewById(R.id.edit_text);
        link = editText.getText().toString();
        progressDialog = new ProgressDialog(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getData().execute();
            }
        });

    }

    public class getData extends AsyncTask<Void,Void,Void> {

        String words;

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document doc = Jsoup.connect("https://www.examtiger.com/mcq/internet-email-web-general-knowledge/").get();
                words = doc.text();
                Log.d("questions",words);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            String s = words;
            //text1.setText(words);
            Pattern q = Pattern.compile(Pattern.quote("Q.") + "(.*?)" + Pattern.quote("Answer"));
            Matcher m = q.matcher(words);
            while (m.find()) {
                questions.add(m.group(1));
            }
            text1.setText(questions.get(0));
            q = Pattern.compile(Pattern.quote("A.") + "(.*?)" + Pattern.quote("B."));
            m = q.matcher(words);
            while (m.find()) {
                optA.add(m.group(1));
            }
            text2.setText(optA.get(0));
            q = Pattern.compile(Pattern.quote("B.") + "(.*?)" + Pattern.quote("C."));
            m = q.matcher(words);
            while (m.find()) {
                optB.add(m.group(1));
            }
            text3.setText(optB.get(0));
            q = Pattern.compile(Pattern.quote("C.") + "(.*?)" + Pattern.quote("D."));
            m = q.matcher(words);
            while (m.find()) {
                optC.add(m.group(1));
            }
            text4.setText(optC.get(0));
            q = Pattern.compile(Pattern.quote("D.") + "(.*?)" + Pattern.quote("Answer"));
            m = q.matcher(words);
            while (m.find()) {
                optD.add(m.group(1));
            }
            text5.setText(optD.get(0));
            q = Pattern.compile(Pattern.quote("Answer :") + "(.*?)" + Pattern.quote("Show Answer"));
            m = q.matcher(words);
            while (m.find()) {
                ans.add(m.group(1));
            }
            saveQuestions();
        }
    }
    private void saveQuestions() {

        Gson gson = new Gson();

        final String newDataQuestions = gson.toJson(questions);
        final String newDataoptA = gson.toJson(optA);
        final String newDataoptB = gson.toJson(optB);
        final String newDataoptC = gson.toJson(optC);
        final String newDataoptD = gson.toJson(optD);
        final String newDatans = gson.toJson(ans);

        Log.d("questions",newDatans);

        progressDialog.setMessage("Saving Questions...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_CHECK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            Log.d("done","done");
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
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
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("question",newDataQuestions);
                params.put("optionA",newDataoptA);
                params.put("optionB",newDataoptB);
                params.put("optionC",newDataoptC);
                params.put("optionD",newDataoptD);
                params.put("answer",newDatans);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }
}
