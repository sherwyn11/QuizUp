package com.example.quizz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.*;

import java.io.InputStream;
import java.util.ArrayList;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class QuizMainActivity extends AppCompatActivity {
    private static final long COUNT_DOWN_MILLIS = 30000;
    private TextView textViewQuestion;
    int count = 0;
    int x = 0;
    int flag = 0;
    String answer = "";
    int score = 0;
    int arr[] = new int[10];
    ArrayList<Integer> number = new ArrayList<Integer>();
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private TextView textViewCountDown;
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button buttonConfirmnext;
    private String Roll_no;
    private TextView ansDisp;
    private String quiz_name;
    // List<Question> questionList=new ArrayList<>();
    private ColorStateList textColorDefaultCd;
    private ColorStateList radio1ColorDefaultCd;
    private ColorStateList radio2ColorDefaultCd;
    private ColorStateList radio3ColorDefaultCd;
    private ColorStateList radio4ColorDefaultCd;
    private CountDownTimer countDownTimer;
    private long timeleftinmillis;

    ArrayList<String> questions = new ArrayList<>();
    ArrayList<String> optA = new ArrayList<>();
    ArrayList<String> optB = new ArrayList<>();
    ArrayList<String> optC = new ArrayList<>();
    ArrayList<String> optD = new ArrayList<>();
    ArrayList<String> ans = new ArrayList<>();
    ArrayList<String> reason = new ArrayList<>();

    ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_main);

        Intent intent = getIntent();
        Roll_no = intent.getStringExtra("Roll_no");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        textViewQuestion = findViewById(R.id.text_view_question);
        textViewScore = findViewById(R.id.text_view_score);
        textViewQuestionCount = findViewById(R.id.text_view_qno);
        textViewCountDown = findViewById(R.id.text_view_countdown);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);
        ansDisp = findViewById(R.id.answer_text);
        buttonConfirmnext = findViewById(R.id.button_submit_next);
        textColorDefaultCd = textViewCountDown.getTextColors();
        radio1ColorDefaultCd = rb1.getTextColors();
        radio2ColorDefaultCd = rb2.getTextColors();
        radio3ColorDefaultCd = rb3.getTextColors();
        radio4ColorDefaultCd = rb4.getTextColors();


        getQuestions();

        buttonConfirmnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag == 0) {
                    countDownTimer.cancel();
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
                        buttonConfirmnext.setText("NEXT");
                        flag = 1;
                        checkAnswer(answer);

                    } else
                        Toast.makeText(QuizMainActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();

                } else {
                    if (count <= 9) {
                        flag = 0;
                        Question(count);
                    } else {
                        endQuiz();
                    }


                }

            }
        });
    }

    private void openActivityEnd() {
        Intent intent = new Intent(this, EndActivity.class);
        intent.putExtra("Roll_no", SharedPrefManager.getInstance(getApplicationContext()).getRollNo());
        intent.putExtra("score", Integer.toString(score));
        intent.putExtra("quiz_name", quiz_name);
        startActivity(intent);
    }


    private void getQuestions() {
        final String year = SharedPrefManager.getInstance(getApplicationContext()).getUserYear();
        final String branch = SharedPrefManager.getInstance(getApplicationContext()).getUserBranch();

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_GET_QUESTIONS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (!jsonArray.getBoolean(1)) {
                                quiz_name = jsonArray.getString(2);
                                for (int i = 3; i < jsonArray.getInt(0) + 3; i++) {
                                    JSONArray js = jsonArray.getJSONArray(i);
                                    questions.add(js.getString(0));
                                    optA.add(js.getString(1));
                                    optB.add(js.getString(2));
                                    optC.add(js.getString(3));
                                    optD.add(js.getString(4));
                                    ans.add(js.getString(5));
                                    reason.add(js.getString(6));
                                }
                                Log.d("dataValues", questions.toString());
                                progressDialog.dismiss();
                                Question(0);
                            } else {
                                Toast.makeText(getApplicationContext(), jsonArray.getString(0), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("year", year);
                params.put("branch", branch);
                params.put("subject", "DWM");
                params.put("code", "000000");
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }


    protected void Question(int i) {

        rbGroup.clearCheck();
        timeleftinmillis=COUNT_DOWN_MILLIS;
        startCountdown();
        ansDisp.setText("");
        buttonConfirmnext.setText("SUBMIT");
        textViewQuestionCount.setText(count+1 +"/10");
        rb1.setTextColor(radio1ColorDefaultCd);
        rb2.setTextColor(radio1ColorDefaultCd);
        rb3.setTextColor(radio1ColorDefaultCd);
        rb4.setTextColor(radio1ColorDefaultCd);
        textViewQuestion.setText(questions.get(i));
        rb1.setText(optA.get(i));
        rb2.setText(optB.get(i));
        rb3.setText(optC.get(i));
        rb4.setText(optD.get(i));
        answer = ans.get(i);
        count = count+1;

    }

    public void startCountdown(){
        countDownTimer = new CountDownTimer(timeleftinmillis,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timeleftinmillis=millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeleftinmillis=0;
                updateCountDownText();

            }
        }.start();
    }

    public void updateCountDownText(){
        int minutes=(int)(timeleftinmillis/1000)/60;
        int seconds=(int)(timeleftinmillis/1000)%60;

        String timeFormatted=String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        textViewCountDown.setText(timeFormatted);
        if(timeleftinmillis<10000){
            textViewCountDown.setTextColor(Color.RED);
        }
        else{
            textViewCountDown.setTextColor(textColorDefaultCd);
        }

    }

    public void endQuiz(){
        openActivityEnd();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
    }
    public void checkAnswer(String ans){

        if(rbGroup.getCheckedRadioButtonId()!=-1)
        {

            int id=rbGroup.getCheckedRadioButtonId();
            View radioButton=findViewById(id);
            int radioId=rbGroup.indexOfChild(radioButton);
            RadioButton btn=(RadioButton)rbGroup.getChildAt(radioId);
            String selection=(String)btn.getText();
            if(selection.compareTo(ans)==0){
                score=score+4;
                btn.setTextColor(Color.GREEN);
                textViewScore.setText("Score : "+score);
            }
            else{
                score=score-1;
                textViewScore.setText("Score : "+score);
                btn.setTextColor(Color.RED);
                if(ans.compareTo((String)rb1.getText())==0){
                    rb1.setTextColor(Color.GREEN);
                }
                else if (ans.compareTo((String)rb2.getText())==0){
                    rb2.setTextColor(Color.GREEN);
                }else if (ans.compareTo((String)rb3.getText())==0){
                    rb3.setTextColor(Color.GREEN);
                }else if (ans.compareTo((String)rb4.getText())==0){
                    rb4.setTextColor(Color.GREEN);
                }

                ansDisp.setText(ans+" is the correct Answer");

            }
        }



    }

}


