package com.example.quizz;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.*;

import java.io.InputStream;
import java.util.ArrayList;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class QuizMainActivity extends AppCompatActivity {
    private static final long COUNT_DOWN_MILLIS=30000;
    private TextView textViewQuestion;
    int count=0;
    int x=0;
    int flag=0;
    String answer="";
    int score=0;
    int arr[]=new int[10];
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
    // List<Question> questionList=new ArrayList<>();
    private ColorStateList textColorDefaultCd;
    private ColorStateList radio1ColorDefaultCd;
    private ColorStateList radio2ColorDefaultCd;
    private ColorStateList radio3ColorDefaultCd;
    private ColorStateList radio4ColorDefaultCd;
    private CountDownTimer countDownTimer;
    private long timeleftinmillis;



    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        arr[0]=0;
        for(int i=1;i<=9;i++) {
            arr[i] = 0;
            number.add(i);
        }

        Intent intent = getIntent();
        Roll_no = intent.getStringExtra("Roll_no");

        setContentView(R.layout.activity_quiz_main);
        Collections.shuffle(number);
        textViewQuestion = findViewById(R.id.text_view_question);
        textViewScore = findViewById(R.id.text_view_score);
        textViewQuestionCount = findViewById(R.id.text_view_qno);
        textViewCountDown = findViewById(R.id.text_view_countdown);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);
        ansDisp=findViewById(R.id.answer_text);
        buttonConfirmnext = findViewById(R.id.button_submit_next);
        textColorDefaultCd=textViewCountDown.getTextColors();
        radio1ColorDefaultCd=rb1.getTextColors();
        radio2ColorDefaultCd=rb2.getTextColors();
        radio3ColorDefaultCd=rb3.getTextColors();
        radio4ColorDefaultCd=rb4.getTextColors();
        Question();

        buttonConfirmnext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {

                if(flag==0) {
                    countDownTimer.cancel();
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
                        buttonConfirmnext.setText("NEXT");
                        flag=1;
                        checkAnswer(answer);

                    } else
                        Toast.makeText(QuizMainActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();

                }
                else{
                    if ((count + 1) < 10) {
                        flag=0;
                        Question();
                    } else {
                        endQuiz();
                    }


                }

            }
        });
    }
    private void openActivityEnd() {
        Intent intent= new Intent(this, EndActivity.class);
        intent.putExtra("Roll_no",Roll_no);
        Log.d("ROOLLLL",Roll_no);
        intent.putExtra("score",Integer.toString(score));
        startActivity(intent);
    }

    protected void Question()
    {


        rbGroup.clearCheck();
        try {
            ansDisp.setText("");
            buttonConfirmnext.setText("SUBMIT");
            rb1.setTextColor(radio1ColorDefaultCd);
            rb2.setTextColor(radio1ColorDefaultCd);
            rb3.setTextColor(radio1ColorDefaultCd);
            rb4.setTextColor(radio1ColorDefaultCd);
            count=count+1;
            timeleftinmillis=COUNT_DOWN_MILLIS;
            startCountdown();
            textViewQuestionCount.setText(count+"/9");
            int x=number.remove(0);
            String a = "", b = "", c = "", d = "";
            answer="";
            AssetManager am = getAssets();// If this line gives you ERROR then try AssetManager am=getActivity().getAssets();
            InputStream is = am.open("Excel.xls");
            Workbook wb = Workbook.getWorkbook(is);
            Sheet s = wb.getSheet(0);
            Cell z = s.getCell(1, x);
            String xx = "";
            xx = xx + z.getContents();
            textViewQuestion.setText(xx);
            z = s.getCell(2, x);
            a = a + z.getContents();
            rb1.setText(a);
            z = s.getCell(3, x);
            b = b + z.getContents();
            rb2.setText(b);
            z = s.getCell(4, x);
            c = c + z.getContents();
            rb3.setText(c);
            z = s.getCell(5, x);
            d = d + z.getContents();
            rb4.setText(d);
            z = s.getCell(6, x);
            answer = answer + z.getContents();

        } catch (Exception e) {

        }


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


