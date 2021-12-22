package com.example.medihealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class JadwalDokterActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    String nama[], spesialis[], jam[];
    DokterAdapter dokterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_dokter);

        recyclerView = findViewById(R.id.recyclerViewDokter);

        nama = getResources().getStringArray(R.array.nama_dokter);
        spesialis =  getResources().getStringArray(R.array.spesialis_dokter);
        jam = getResources().getStringArray(R.array.jam_dokter);

        DokterAdapter dokterAdapter = new DokterAdapter(this, nama, spesialis, jam);
        recyclerView.setAdapter(dokterAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}