package com.example.rovr5barzapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT= 3000;
    private boolean InternetCheck =true;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //progressBar
        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.setVisibility(View.VISIBLE);
        PostDelayedMethod();
    }

    public void PostDelayedMethod() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean InternetResult = checkConnection();
                if(InternetResult){
                    Intent intent= new Intent(SplashScreen.this,RovrDetectedActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    spinner.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.GONE);
                    DialogAppear();
                }
            }

        },SPLASH_TIME_OUT);
    }

    public  void DialogAppear(){
        AlertDialog.Builder builder =new AlertDialog.Builder(SplashScreen.this);
        builder.setTitle("Alert");
        builder.setMessage("WiFi is OFF, Please turn ON");

        builder.setNegativeButton("Exit",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dailog, int which){
                //close this activity
                finish();
            }
        });
        builder.setPositiveButton("Exit",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dailog, int which){
                PostDelayedMethod();
            }
        });
        builder.show();
    }

    protected  boolean isOnline(){
        ConnectivityManager cm =(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo != null && ((NetworkInfo) netInfo).isConnected()){
            return true;
        }else {
            return false;
        }
    }
    public  boolean checkConnection(){
        if(isOnline()){
            Toast.makeText(SplashScreen.this, "WiFi Connected", Toast.LENGTH_SHORT).show();
            return InternetCheck;
        }else {
            InternetCheck= false;
            Toast.makeText(SplashScreen.this, "WiFi not Connected", Toast.LENGTH_SHORT).show();
            return InternetCheck;
        }
    }

}