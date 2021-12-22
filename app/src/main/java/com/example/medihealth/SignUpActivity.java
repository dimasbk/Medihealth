package com.example.medihealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    private EditText regis_email, regis_pass, regis_nama, tgl_lahir;
    private RadioGroup rg_regis;
    private RadioButton rb1, rb2, rb;
    private CheckBox termsandcond;
    private Button sign_up, btn_date;
    private TextView tv_date;
    public String date;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dbHelper = new DBHelper(this);
        regis_email = findViewById(R.id.email);
//        tgl_lahir = findViewById(R.id.editTextDate);
        regis_pass = findViewById(R.id.password);
        regis_nama =  findViewById(R.id.nama);
        rg_regis = findViewById(R.id.rg);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        termsandcond = findViewById(R.id.termsandcond);
        sign_up = findViewById(R.id.btn_regis);
        btn_date = findViewById(R.id.tgl_btn);


        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TampilTanggal();
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = regis_email.getText().toString();
                String pass =  regis_pass.getText().toString();
                String lahir = date;
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
//                }else if(lahir.equals("")) {
//                    tgl_lahir.setError("Masukkan Tanggal Lahir");
                }else if(idradio==-1){
                    Toast.makeText(SignUpActivity.this, "Pilih Salah Satu Jenis Kelamin", Toast.LENGTH_SHORT).show();
                }else if(!termsandcond.isChecked()){
                    Toast.makeText(SignUpActivity.this, "Pilih Minimal Satu Genre", Toast.LENGTH_SHORT).show();
                }else {
                    regis(nama, email, pass, lahir, jk);
                }
            }
        });

    }

    private void regis(String nama, String email, String password, String lahir, String jk){
        Boolean insertdata = dbHelper.insertDataUser(nama, email, password, lahir, jk);
        if(insertdata==true) {
            Toast.makeText(SignUpActivity.this, "Data Telah Masuk", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(SignUpActivity.this, "Data gagal Masuk", Toast.LENGTH_SHORT).show();
        }

    }


    public void TampilTanggal(){
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "data");
        datePickerFragment.setOnDateClickListener(new DatePickerFragment.onDateClickListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String tahun = ""+datePicker.getYear();
                String bulan = ""+(datePicker.getMonth()+1);
                String hari = ""+datePicker.getDayOfMonth();
                String text = "Tanggal : "+hari+" - "+bulan+" - "+tahun;
                date = ""+hari+"/"+bulan+"/"+tahun;
                Toast.makeText(SignUpActivity.this, date, Toast.LENGTH_SHORT).show();
                btn_date.setText(text);
            }
        });
    }
}