package com.example.quizz;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static SharedPrefManager instance;
    private static Context mCtx;

    private static final String ROLL_NO = "8334";
    private static final String YEAR = "1";
    private static final String BRANCH = "Comps";


    private SharedPrefManager(Context context) {
        mCtx = context;

    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public boolean userLogin(String roll_no, String year,String branch){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(ROLL_NO,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(ROLL_NO,roll_no);
        editor.putString(YEAR,year);
        editor.putString(BRANCH,branch);

        editor.apply();

        return true;

    }

    public boolean isLogedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(ROLL_NO,Context.MODE_PRIVATE);
        return !sharedPreferences.getString(ROLL_NO, "null").equals("null");
//        if(!sharedPreferences.getString(ROLL_NO,"null").equals("null")){
//            return true;
//        }
//        else{
//            return false;
//        }
    }

    public boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(ROLL_NO,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getRollNo(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(ROLL_NO,Context.MODE_PRIVATE);
        return sharedPreferences.getString(ROLL_NO,null);
    }

    public String getUserYear(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(ROLL_NO,Context.MODE_PRIVATE);
        return sharedPreferences.getString(YEAR,null);
    }

    public String getUserBranch(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(ROLL_NO,Context.MODE_PRIVATE);
        return sharedPreferences.getString(BRANCH,null);
    }

}