package com.example.medihealth;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ReservasiActivity extends AppCompatActivity{
//    private Spinner spinnerPoli, spinnerDokter;
    EditText txtPoli, txtDokter;
    TextView tvAsuransi, tvTanggal;
    RadioButton rUmum, rJkn;
    private RadioGroup rAsuransi;
    FloatingActionButton add_button;
    Button btn_date, btnSubmit;
    String date = "";
    String asuransi, dokter, poli, id_user;
    SharedPreferences getDataId;

    private DBHelper db = new DBHelper(this);

    Boolean stat = false;
    Boolean statRadio = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservasi);

//        spinnerPoli = findViewById(R.id.poli_spinner);
//        spinnerDokter = findViewById(R.id.dokter_spinner);

        txtPoli = (EditText) findViewById(R.id.etPoli);
        txtDokter = (EditText) findViewById(R.id.etDokter);
        tvAsuransi = (TextView) findViewById(R.id.pilih_car);
        tvTanggal = (TextView) findViewById(R.id.tanggal_res);
        rUmum = findViewById(R.id.byrUmum);
        rJkn = findViewById(R.id.byrJkn);

        txtPoli.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (txtPoli.getText().toString().length()==0){
                        txtPoli.setError("Nama barang tidak boleh kosong");
                        stat = false;
                    }else{
                        stat = true;
                    }
                }
            }
        });

        txtDokter.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (txtDokter.getText().toString().length()==0){
                        txtDokter.setError("Nama penerima tidak boleh kosong");
                        stat = false;
                    }else{
                        stat = true;
                    }
                }
            }
        });

        rUmum = findViewById(R.id.byrUmum);
        rJkn = findViewById(R.id.byrJkn);
        rAsuransi = findViewById(R.id.rgAsuransi);
        rAsuransi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged (RadioGroup radioGroup, int id) {
                switch (id){
                    case R.id.byrUmum:
                        asuransi = "UMUM";
                        statRadio = true;
                        break;
                    case R.id.byrJkn:
                        asuransi = "JKN";
                        statRadio = true;
                        break;
                }
            }
        });

        btn_date = findViewById(R.id.tgl_btn);
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TampilTanggal();
            }
        });


        btnSubmit = (Button) findViewById(R.id.btSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                poli = txtPoli.getText().toString();
                dokter = txtDokter.getText().toString();
                getDataId = getApplicationContext().getSharedPreferences("SESSION_id", MODE_PRIVATE);
                id_user = getDataId.getString("SESSION_id","");

                if (rUmum.isChecked() || rJkn.isChecked()){

                }else{
                    statRadio = false;
                    tvAsuransi.setError("Nama daerah tidak boleh kosong");
                }

                if (txtPoli.getText().toString().length()==0) {
                    txtPoli.setError("Nama barang tidak boleh kosong");
                }
                if (txtDokter.getText().toString().length()==0) {
                    txtDokter.setError("Nama penerima tidak boleh kosong");
                }

                if (date.length()==0){
                    tvTanggal.setError("Tanggal tidak boleh kosong");
                    stat = false;
                }else {
                    stat = true;
                }

                if(stat == true && statRadio == true) {
                    showDialog(poli, dokter, asuransi, date, id_user);
                    stat = false;
                }else {
                    Toast.makeText(getApplicationContext(), "Tolong lengkapi form", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    private void showDialog(String poli, String dokter, String asuransi, String date, String id_user){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Reservasi?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Nama Poli : " + poli + "\nNama Dokter : " + dokter + "\nAsuransi : " + asuransi + "\nTanggal : " + date)
                .setIcon(R.drawable.logo)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini

                        DBHelper myDB = new DBHelper(ReservasiActivity.this);
                        myDB.insertDataReservasi(poli.trim(),
                                dokter.trim(),
                                asuransi.trim(),
                                date.trim(),
                                id_user.trim());
                        Intent intent = new Intent(ReservasiActivity.this, MenuActivity.class);
//                        intent.putExtra("namaBarang", namaBarang);
//                        intent.putExtra("namaPenerima", namaPenerima);
//                        intent.putExtra("dropshiper", dropshiper);
//                        intent.putExtra("jml2", jml2);
//                        intent.putExtra("daerah", daerah);

//                        db.insert(namaBarang, namaPenerima, dropshiper, jml2, daerah);
//
//                        Boolean checkinsertdata = db.insert(namaBarang, namaPenerima, dropshiper, jml2, daerah);
//
//                        if(checkinsertdata==true) {
//                            Toast.makeText(id.bagas.modul4recyclerview.PurchaseActivity.this, "Data Telah Masuk", Toast.LENGTH_SHORT).show();
//                        }else {
//                            Toast.makeText(id.bagas.modul4recyclerview.PurchaseActivity.this, "Data gagal Masuk", Toast.LENGTH_SHORT).show();
//                        }
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
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
                Toast.makeText(ReservasiActivity.this, date, Toast.LENGTH_SHORT).show();
                btn_date.setText(text);
            }
        });
    }
}