package com.example.medihealth;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateActivity extends AppCompatActivity {

    private SeekBar sBar;
    private TextView tView;
    private TextView tvDaerah;
    private Button btnUpdate;
    private Button btnDelete;
    private EditText txtNama;
    private EditText txtNamaPenerima;
    private CheckBox cbDrop;
    private RadioGroup rDaerah;
    private RadioButton rJbd;
    private RadioButton rLJbd;


    String row_id, namaBarang, namaPenerima, ddropshiper, jumlah;
    String ddaerah, daerah;
    int jml, jumlahi;
    int jml2;

    Boolean stat = false;
    Boolean statRadio = false;
    Boolean statJml = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        txtNama = (EditText) findViewById(R.id.etNama);
        tvDaerah = (TextView) findViewById(R.id.tvDaerah);
        txtNamaPenerima = (EditText) findViewById(R.id.etNamaPenerima);
        cbDrop = (CheckBox) findViewById(R.id.cBDrop);
        sBar = (SeekBar) findViewById(R.id.seekBar);
        tView = (TextView) findViewById(R.id.tvJumlah);
        rJbd = findViewById(R.id.rbJABODETABEK);
        rLJbd = findViewById(R.id.rbLuarJABODETABEK);
        rDaerah = findViewById(R.id.rgJawaban1);

//        namaBarang_input = findViewById(R.id.etNama);
//        namaPenerima_input = findViewById(R.id.etNamaPenerima);
//        ddropshiper_input = (CheckBox) findViewById(R.id.cBDrop);
//        jml_input = (SeekBar) findViewById(R.id.seekBar);
//        ddaerah_input = (RadioButton) findViewById(R.id.seekBar);
        btnUpdate = findViewById(R.id.btUpdate);
        btnDelete = findViewById(R.id.btDelete);

        //First we call this
        getAndSetIntentData();

        //Set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(namaBarang);
        }


        txtNama.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (txtNama.getText().toString().length()==0){
                        txtNama.setError("Nama barang tidak boleh kosong");
                        stat = false;
                    }else{
                        stat = true;
                    }
                }
            }
        });

        txtNamaPenerima.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (txtNamaPenerima.getText().toString().length()==0){
                        txtNamaPenerima.setError("Nama penerima tidak boleh kosong");
                        stat = false;
                    }else{
                        stat = true;
                    }
                }
            }
        });

        tView.setText("Jumlah   " + sBar.getProgress() + "/" + sBar.getMax());
        sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int pval = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pval = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //write custom code to on start progress
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tView.setText("Jumlah   " + pval + "/" + seekBar.getMax());
                jumlahi = pval;
            }
        });

        rDaerah.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged (RadioGroup radioGroup, int id) {
                switch (id){
                    case R.id.rbJABODETABEK:
                        daerah = "JABODETABEK";
                        statRadio = true;
                        break;
                    case R.id.rbLuarJABODETABEK:
                        daerah = "Luar JABODETABEK";
                        statRadio = true;
                        break;
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //And only then we call this
                DBHelper myDB = new DBHelper(com.example.medihealth.UpdateActivity.this);
                namaBarang = txtNama.getText().toString().trim();
                namaPenerima = txtNamaPenerima.getText().toString().trim();


                if(jumlahi >= 1){
                    statJml = true;
                }else {
                    statJml = false;
                    tView.setError("Jumlah tidak boleh kosong");
                }

                if (rJbd.isChecked() || rLJbd.isChecked()){

                }else{
                    statRadio = false;
                    tvDaerah.setError("Nama daerah tidak boleh kosong");
                }

                if (cbDrop.isChecked()) {
                    ddropshiper = "Ya";
                }else {
                    ddropshiper = "Tidak";
                }

                if (txtNama.getText().toString().length()==0) {
                    txtNama.setError("Nama barang tidak boleh kosong");
                }
                if (txtNamaPenerima.getText().toString().length()==0) {
                    txtNamaPenerima.setError("Nama penerima tidak boleh kosong");
                }


                if(stat == true && statRadio == true && statJml == true) {
                    myDB.updateDataReservasi(row_id, namaBarang, namaPenerima, ddropshiper, daerah);

                }else {
                    Toast.makeText(getApplicationContext(), "Tolong lengkapi form"+ stat + statRadio + statJml, Toast.LENGTH_LONG).show();
                }

//                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                confirmDialog();
            }
        });

    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("namaBarang") &&
                getIntent().hasExtra("namaPenerima") && getIntent().hasExtra("dropshiper") &&
                getIntent().hasExtra("jumlah") && getIntent().hasExtra("daerah")){
            //Getting Data from Intent
            row_id = getIntent().getStringExtra("id");
            namaBarang = getIntent().getStringExtra("namaBarang");
            namaPenerima = getIntent().getStringExtra("namaPenerima");
            ddropshiper = getIntent().getStringExtra("dropshiper");
            jumlah = getIntent().getStringExtra("jumlah");
            jumlahi = Integer.parseInt(jumlah);
            ddaerah = getIntent().getStringExtra("daerah");

            //Setting Intent Data
            txtNama.setText(namaBarang);
            txtNamaPenerima.setText(namaPenerima);

            if (ddropshiper.equals("Ya")){
                cbDrop.setChecked(true);
            }else{
                cbDrop.setChecked(false);
            }

            sBar.setProgress(jumlahi);
            tView.setText("Jumlah   " + jumlah + "/10");

            if (ddaerah.equals("JABODETABEK")){
                rJbd.setChecked(true);
            }else{
                rLJbd.setChecked(true);
            }
            stat = true;
            statRadio = true;

            Log.d("stev", namaBarang+" "+namaPenerima+" "+ddropshiper);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Delete " + namaBarang + " ?");
        builder.setMessage("Are you sure you want to delete " + namaBarang + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBHelper myDB = new DBHelper(com.example.medihealth.UpdateActivity.this);
                myDB.deleteDataReservasi(row_id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}
