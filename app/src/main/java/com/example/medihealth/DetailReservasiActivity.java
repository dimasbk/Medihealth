package com.example.medihealth;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class DetailReservasiActivity extends AppCompatActivity {


    TextView tgl_res, poli, dokter;
    String id_reservasi, nama_poli, nama_dokter, tgl_reservasi, asuransi, qrinput;
    ImageView qr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_reservasi);





        //First we call this
        getAndSetIntentData();

        //Set actionbar title after getAndSetIntentData method

    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id_rsv") && getIntent().hasExtra("poli") &&
                getIntent().hasExtra("dokter") && getIntent().hasExtra("asuransi") &&
                getIntent().hasExtra("tglrsv")) {
            //Getting Data from Intent
            id_reservasi = getIntent().getStringExtra("id_rsv");
            nama_poli = getIntent().getStringExtra("poli");
            nama_dokter = getIntent().getStringExtra("dokter");
            tgl_reservasi = getIntent().getStringExtra("tglrsv");
            asuransi = getIntent().getStringExtra("asuransi");

            tgl_res = findViewById(R.id.tv_reservasi);
            poli = findViewById(R.id.tv_poli);
            dokter = findViewById(R.id.tv_dokter);

            tgl_res.setText(tgl_reservasi);
            poli.setText(nama_poli);
            dokter.setText(nama_dokter);
            qrinput =id_reservasi+nama_poli+nama_dokter+tgl_reservasi+asuransi;

            qr = findViewById(R.id.qr_code);

            MultiFormatWriter writer = new MultiFormatWriter();
            try {
                BitMatrix matrix = writer.encode(qrinput, BarcodeFormat.QR_CODE, 700, 700);
                BarcodeEncoder encoder = new BarcodeEncoder();
                Bitmap bitmap = encoder.createBitmap(matrix);
                qr.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }

        }
    }


}
