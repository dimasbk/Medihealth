package com.example.medihealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private EditText regis_email, regis_pass, regis_nama, tgl_lahir;
    private RadioGroup rg_regis;
    private RadioButton rb1, rb2, rb;
    private CheckBox termsandcond;
    private Button btn_regis;
    DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbHelper = new DBHelper(this);
        regis_email = findViewById(R.id.regis_email);
        tgl_lahir = findViewById(R.id.tgl_lahir);
        regis_pass = findViewById(R.id.regis_pass);
        regis_nama =  findViewById(R.id.regis_nama);
        rg_regis = findViewById(R.id.rg_regis);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        termsandcond = findViewById(R.id.termsandcond);
        btn_regis = findViewById(R.id.btn_regis);

        btn_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = regis_email.getText().toString();
                String pass =  regis_pass.getText().toString();
                String lahir = tgl_lahir.getText().toString();
                String nama = regis_nama.getText().toString();
                int idradio = rg_regis.getCheckedRadioButtonId();
                rb = findViewById(idradio);
                String jk = rb.getText().toString();
                if(email.equals("")){
                    regis_email.setError("Masukkan Email");
                }else if(pass.equals("")) {
                    regis_pass.setError("Masukkan Password");
                }else if(nama.equals("")) {
                    regis_nama.setError("Masukkan Nama");
                }else if(lahir.equals("")) {
                    tgl_lahir.setError("Masukkan Tanggal Lahir");
                }else if(idradio==-1){
                    Toast.makeText(RegisterActivity.this, "Pilih Salah Satu Jenis Kelamin", Toast.LENGTH_SHORT).show();
                }else if(!termsandcond.isChecked()){
                    Toast.makeText(RegisterActivity.this, "Pilih Minimal Satu Genre", Toast.LENGTH_SHORT).show();
                }else {
                    ContentValues values = new ContentValues();
                    values.put(DBHelper.row_nama_user, nama);
                    values.put(DBHelper.row_email, email);
                    values.put(DBHelper.row_password, pass);
                    values.put(DBHelper.row_lahir, lahir);
                    values.put(DBHelper.row_jk, jk);
                    dbHelper.insertDataUser(values);
                    Intent toLogin = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(toLogin);
                }





            }
        });





    }
}