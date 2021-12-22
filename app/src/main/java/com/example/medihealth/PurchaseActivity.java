package com.example.medihealth;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class PurchaseActivity extends AppCompatActivity {
    private SeekBar sBar;
    private TextView tView;
    private TextView tvDaerah;
    private Button btnPesan;
    private EditText txtNama;
    private EditText txtNamaPenerima;
    private CheckBox cbDrop;
    private RadioGroup rDaerah;
    private RadioButton rJbd;
    private RadioButton rLJbd;

    private DBHelper db = new DBHelper(this);
    private String id;

    String namaBarang, dropshiper, daerah, namaPenerima;
    int jumlah;
    int jml2 = 0;
    Boolean stat = false;
    Boolean statRadio = false;
    Boolean statJml = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        txtNama = (EditText) findViewById(R.id.etNama);
        tvDaerah = (TextView) findViewById(R.id.tvDaerah);
        txtNamaPenerima = (EditText) findViewById(R.id.etNamaPenerima);
        cbDrop = (CheckBox) findViewById(R.id.cBDrop);

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

        sBar = (SeekBar) findViewById(R.id.seekBar);
        tView = (TextView) findViewById(R.id.tvJumlah);
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
                jml2 = pval;
            }
        });

        rJbd = findViewById(R.id.rbJABODETABEK);
        rLJbd = findViewById(R.id.rbLuarJABODETABEK);
        rDaerah = findViewById(R.id.rgJawaban1);
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

        btnPesan = (Button) findViewById(R.id.btPesan);
        btnPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                namaBarang = txtNama.getText().toString();
                namaPenerima = txtNamaPenerima.getText().toString();

                if(jml2 >= 1){
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
                    dropshiper = "Ya";
                }else {
                    dropshiper = "Tidak";
                }

                if (txtNama.getText().toString().length()==0) {
                    txtNama.setError("Nama barang tidak boleh kosong");
                }
                if (txtNamaPenerima.getText().toString().length()==0) {
                    txtNamaPenerima.setError("Nama penerima tidak boleh kosong");
                }

                if(stat == true && statRadio == true && statJml == true) {
                    showDialog(namaBarang, namaPenerima, jml2, dropshiper, daerah);
                    stat = false;
                }else {
                    Toast.makeText(getApplicationContext(), "Tolong lengkapi form", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void showDialog(String namaBarang, String namaPenerima, int jml2, String dropshiper, String daerah){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Pesan Barang?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Nama Barang : " + namaBarang + "\nNama Penerima : " + namaPenerima + "\nDropshiper : " + dropshiper + "\nJumlah : " + jml2 + "\nDaerah  : " + daerah)
                .setIcon(R.drawable.logo)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        txtNama.setText("");
                        txtNamaPenerima.setText("");
                        cbDrop.setChecked(false);
                        rJbd.setChecked(false);
                        rLJbd.setChecked(false);
                        sBar.setProgress(0);
                        tView.setText("Jumlah   " + "0" + "/10");
                        Intent intent = new Intent(com.example.medihealth.PurchaseActivity.this, CheckoutActivity.class);
                        intent.putExtra("namaBarang", namaBarang);
                        intent.putExtra("namaPenerima", namaPenerima);
                        intent.putExtra("dropshiper", dropshiper);
                        intent.putExtra("jml2", jml2);
                        intent.putExtra("daerah", daerah);

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
}