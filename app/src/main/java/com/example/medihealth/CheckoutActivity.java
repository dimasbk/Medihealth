package com.example.medihealth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

//import id.bagas.modul4recyclerview.model.Data;

public class CheckoutActivity extends AppCompatActivity {
    private TextView tvNamaBarang;
    private TextView tvNamaPenerima;
    private TextView tvDropshiper;
    private TextView tvJml;
    private TextView tvDrh;
    Button btCheckout;

    String namaBarang;
    String namaPenerima;
    String dropshiper;
    String daerah;
    String jml;
    int jml2;

//    List<Data> lists = new ArrayList<Data>();
    DBAdapter dbadapter;
    DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        tvNamaBarang = (TextView) findViewById(R.id.tvNamaBarang);
        tvNamaPenerima = (TextView) findViewById(R.id.tvNamaPenerima);
        tvDropshiper = (TextView) findViewById(R.id.tvDropshiper);
        tvJml = (TextView) findViewById(R.id.tvJml);
        tvDrh = (TextView) findViewById(R.id.tvDrh);
        btCheckout = (Button) findViewById(R.id.btCheckout);

        showForm();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Halaman checkout", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Aplikasi onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "Aplikasi onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "Aplikasi onStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Selamat Tinggal", Toast.LENGTH_LONG).show();
    }

    private void showForm(){
        namaBarang = getIntent().getStringExtra("namaBarang");
        namaPenerima = getIntent().getStringExtra("namaPenerima");
        dropshiper = getIntent().getStringExtra("dropshiper");
        daerah = getIntent().getStringExtra("daerah");
        jml2 = getIntent().getIntExtra("jml2", 0);
        jml = Integer.toString(jml2);

//        ArrayList<HashMap<String, String>> rows = db.getAll();
//        for (int i = 0; i < rows.size(); i++){
//            String id = rows.get(i).get("id");
//            String namaBarang = rows.get(i).get("nama_barang");
//            String namaPenerima = rows.get(i).get("nama_penerima");
//
//            Data data = new Data();
//            data.setId(id);
//            data.setnama_barang(namaBarang);
//            data.setnama_penerima(namaPenerima);
////            lists.add(data);
//        }
        tvNamaBarang.setText(namaBarang);
        tvNamaPenerima.setText(namaPenerima);
//        dbadapter.notifyDataSetChanged();
        tvDropshiper.setText(dropshiper);
        tvJml.setText(jml);
        tvDrh.setText(daerah);

        btCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper myDB = new DBHelper(com.example.medihealth.CheckoutActivity.this);
                myDB.insertDataReservasi(namaBarang.trim(),
                        namaPenerima.trim(),
                        dropshiper.trim(),
                        daerah.trim());


                Intent intent = new Intent(com.example.medihealth.CheckoutActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
