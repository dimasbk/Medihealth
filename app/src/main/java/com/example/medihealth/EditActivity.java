package com.example.medihealth;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    DBHelper dbHelper;
    EditText nama_depan, nama_belakang, email, jenis_kelamin;
    Button btn_edit, tgl_lahir;

    String u_id, u_nama, u_email, u_password, u_tgl, u_jk;
    String i_id, i_nama, i_email, i_password, i_tgl, i_jk;
    String date, jk;

    private RadioGroup rJk;
    private RadioButton rL;
    private RadioButton rP;

    SharedPreferences getDataMail, getDataPass, getDataId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        dbHelper = new DBHelper(this);

        getDataMail = getSharedPreferences("SESSION_mail", MODE_PRIVATE);
        getDataPass = getSharedPreferences("SESSION_pass", MODE_PRIVATE);
        getDataId = getSharedPreferences("SESSION_id", MODE_PRIVATE);

        btn_edit = findViewById(R.id.btn_editUser);
        nama_depan = findViewById(R.id.tv_id_nama_depan_user);
        nama_belakang = findViewById(R.id.tv_id_nama_belakang_user);
        email = findViewById(R.id.tv_id_input_view_email);
        tgl_lahir = findViewById(R.id.btn_id_input_view_umur);
//        jenis_kelamin = findViewById(R.id.tv_id_input_view_jenis_kelamin);
        String id = getDataId.getString("SESSION_id","");

        rJk = findViewById(R.id.rgJk);
        rL = findViewById(R.id.rbL);
        rP = findViewById(R.id.rbP);


        Cursor curEmail = dbHelper.readEmailProfil(id);
        Cursor curNama = dbHelper.readNamaProfil(id);
        Cursor curLahir = dbHelper.readTglProfil(id);
        Cursor curJk = dbHelper.readJkProfil(id);
        Cursor curPass = dbHelper.readPassProfil(id);

        u_nama = curNama.getString(0);

        String lastName = "";
        String firstName= "";
        if(u_nama.split("\\w+").length>1){

            lastName = u_nama.substring(u_nama.lastIndexOf(" ")+1);
            firstName = u_nama.substring(0, u_nama.lastIndexOf(' '));
        }
        else{
            firstName = u_nama;
            lastName = "";
        }

        u_email = curEmail.getString(0);
        u_tgl = curLahir.getString(0);
        date = u_tgl;
        u_jk = curJk.getString(0);
        u_password = curPass.getString(0);

        jk = u_jk;

        nama_depan.setText(firstName);
        nama_belakang.setText(lastName);
        email.setText(u_email);
        tgl_lahir.setText(u_tgl);
//        jenis_kelamin.setText(u_jk);

        if (rJk.equals("Perempuan")){
            rP.setChecked(true);
        }else{
            rL.setChecked(true);
        }


        rJk.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged (RadioGroup radioGroup, int id) {
                switch (id){
                    case R.id.rbL:
                        jk = "Laki - laki";
//                        statRadio = true;
                        break;
                    case R.id.rbP:
                        jk = "Perempuan";
//                        statRadio = true;
                        break;
                }
            }
        });


        tgl_lahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TampilTanggal();
            }
        });


        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String i_nama1 = "";
                String i_nama2 = "";

                i_nama1 = nama_depan.getText().toString().trim();
                i_nama2 = nama_belakang.getText().toString().trim();
                i_nama = i_nama1+" "+i_nama2;
                i_email = email.getText().toString().trim();
                i_password = u_password;
                i_tgl = date;
                i_jk = jk;

                DBHelper myDB = new DBHelper(EditActivity.this);
                myDB.updateDataProfil(id, i_nama, i_email, i_password, i_tgl, i_jk);
//                Toast.makeText(getApplicationContext(), "Tolong lengkapi form", Toast.LENGTH_LONG).show();
            }
        });

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
                String text = hari+" - "+bulan+" - "+tahun;
                date = ""+hari+"/"+bulan+"/"+tahun;
                Toast.makeText(EditActivity.this, date, Toast.LENGTH_SHORT).show();
                tgl_lahir.setText(text);
            }
        });
    }

}
