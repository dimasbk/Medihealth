package com.example.medihealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class SplashScreenActivity extends AppCompatActivity {

    SharedPreferences getDataMail, getDataPass, getDataId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //menghilangkan ActionBar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        getDataMail = getSharedPreferences("SESSION_mail", MODE_PRIVATE);
        getDataPass = getSharedPreferences("SESSION_pass", MODE_PRIVATE);
        getDataId = getSharedPreferences("SESSION_id", MODE_PRIVATE);

        int loss = getIntent().getIntExtra("loss", 0);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(loss == 1){
                    startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                    finish();
                }else{
                    if(getDataMail.contains("SESSION_mail")){
                        startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                        finish();
                    }else{
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                }
            }
        }, 3000L); //3000 L = 3 detik
    }
}