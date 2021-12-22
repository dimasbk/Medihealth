package com.example.medihealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    SQLiteDatabase mydb;
    private TextView tv_signup;
    private EditText login_email, login_pass;
    private Button btn_login;
    DBHelper dbHandler;
//    String email;
    SharedPreferences.Editor setDataMail, setDataPass, setDataId;

    int loss = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHandler = new DBHelper(this);

        login_email = findViewById(R.id.email);
        login_pass = findViewById(R.id.password);
        btn_login = findViewById(R.id.login_button);
        tv_signup = findViewById(R.id.textView9);

        setDataMail = getSharedPreferences("SESSION_mail", MODE_PRIVATE).edit();
        setDataPass = getSharedPreferences("SESSION_pass", MODE_PRIVATE).edit();
        setDataId = getSharedPreferences("SESSION_id", MODE_PRIVATE).edit();

        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupintent = new Intent(getApplicationContext(), com.example.medihealth.SignUpActivity.class);
                startActivity(signupintent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = login_email.getText().toString().trim();
                String password = login_pass.getText().toString().trim();
                boolean result = dbHandler.checkUser(email, password);
                if(result == true){
                    setDataMail.putString("SESSION_mail", email);
                    setDataMail.apply();
//                    email = getText().toString.trim(setDataMail);
                    setDataPass.putString("SESSION_pass", password);
                    setDataPass.apply();
                    String id = dbHandler.readUserData(email);
                    setDataId.putString("SESSION_id", id);
                    setDataId.commit();
                    loss = 1;

//                    dbHandler.readUserData(email);


                    Toast.makeText(LoginActivity.this, "Berhasil Login", Toast.LENGTH_SHORT).show();
                    Intent loggedin = new Intent(getApplicationContext(), SplashScreenActivity.class);

                    loggedin.putExtra("loss", loss);
                    startActivity(loggedin);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Login Gagal, Mohon Coba Kembali", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}