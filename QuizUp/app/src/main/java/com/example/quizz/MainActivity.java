package com.example.quizz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class MainActivity extends AppCompatActivity{
    private Button button;
    private EditText Rollno;
    private EditText password;
    private TextView user;
    private ProgressDialog progressDialog;

    private String checkuser;
    int check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Rollno= findViewById(R.id.editText1);
        password= findViewById(R.id.editText2);
        button = findViewById(R.id.enterdetails);
        user=findViewById(R.id.textView1);

        Intent intent = getIntent();
        checkuser = intent.getStringExtra("check");
        Log.d("check",checkuser);



        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });



    }

    private void userLogin(){
        final String roll = Rollno.getText().toString();
        final String pswd = password.getText().toString();
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_CHECK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                Toast.makeText(getApplicationContext(),"User Login Successful",Toast.LENGTH_LONG).show();
                                goNext();
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
                params.put("password",pswd);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void goNext() {

        if(checkuser.equals("0")){
            Intent i = new Intent(this,TeacherActivity.class);
            i.putExtra("stuff",check);
            i.putExtra("roll_no",Rollno.getText().toString());
            startActivity(i);

        }else{
            Intent i = new Intent(this,EnterCodeActivity.class);
            i.putExtra("stuff",check);
            i.putExtra("roll_no",Rollno.getText().toString());
            startActivity(i);
        }

    }

    public void openActivityEnterCode() {
        Intent intent= new Intent(this, CodeActivity.class);
        intent.putExtra("stuff",check);
        intent.putExtra("roll_no",Rollno.getText().toString());
        startActivity(intent);
    }

    public void openTeacherMain() {
        Intent intent1 = new Intent(this, TeacherActivity.class);
        startActivity(intent1);
    }

//    public void checkInfo() {
//        try {
//            int x = 0;
//            int flagS=0;
//            int flagT=0;
//            while (x < 3) {
//                AssetManager am = getAssets();
//                InputStream is = am.open("StudentDB.xls");
//                Workbook wb = Workbook.getWorkbook(is);
//                Sheet s = wb.getSheet(0);
//                Cell r = s.getCell(0, x);
//                Cell p = s.getCell(1, x);
//                String rn = "";
//                String pswd = "";
//                rn = rn + r.getContents();
//                pswd = pswd + p.getContents();
//                String RollNumber = Rollno.getText().toString();
//                String Password = password.getText().toString();
//                if((RollNumber.equals("123")) && (Password.equals("123"))) {
//                    flagT=1;
//
//                }
//                else if ((rn.equalsIgnoreCase(RollNumber)) && (pswd.equalsIgnoreCase(Password))) {
//                    flagS=1;
//
//                }
//                if(flagS==1 || flagT==1)
//                    break;
//                else
//                    x = x + 1;
//            }
//
//            if(flagT==1 && flagS==0){
//                check=0;
//                openTeacherMain();
//            }
//            else if(flagS==1 && flagT==0){
//                check=1;
//                openActivityEnterCode();
//            }
//            else if(flagS==0 && flagT==0){
//                Toast.makeText(MainActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
//            }
//
//        } catch (Exception e) {
//
//        }
//
//    }

}