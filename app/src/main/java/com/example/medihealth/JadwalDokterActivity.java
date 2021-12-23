package com.example.medihealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.medihealth.model.Model_tb_dokter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JadwalDokterActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    String nama[], spesialis[], jam[];
    DokterAdapter dokterAdapter;
    private DokterAPI dokterAPI;
    ArrayList<Model_tb_dokter> daftarDokterAdapter = new ArrayList<>();
    private Call<ArrayList<Model_tb_dokter>> call;
    private Model_tb_dokter dokterModel;

    DBHelper db;

    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_dokter);


        Retrofit retrofit= new Retrofit.Builder().baseUrl("http://172.16.53.156:8000/api/").addConverterFactory(GsonConverterFactory.create()).build();
        dokterAPI = retrofit.create(DokterAPI.class);
        call = dokterAPI.getDokter();

        daftarDokterAdapter = db.getAllData_tb_dokter();
        nama = getResources().getStringArray(R.array.nama_dokter);
        spesialis =  getResources().getStringArray(R.array.spesialis_dokter);
        jam = getResources().getStringArray(R.array.jam_dokter);
//        recyclerView = findViewById(R.id.recyclerViewDokter);
        mulaiAdapter();



//        DokterAdapter dokterAdapter = new DokterAdapter(this, nama, spesialis, jam);
//        recyclerView.setAdapter(dokterAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    public void mulaiAdapter() {

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewDokter);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

        ArrayList<Model_tb_dokter> dokterArrayList = db.getAllData_tb_dokter();
        if (dokterArrayList.isEmpty()) {
            call.enqueue(new Callback<ArrayList<Model_tb_dokter>>() {
                @Override
                public void onResponse(Call<ArrayList<Model_tb_dokter>> call, Response<ArrayList<Model_tb_dokter>> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Code : " + Integer.toString(response.code()), Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        daftarDokterAdapter = response.body();
                        db.insertAllData(daftarDokterAdapter);
                        Toast.makeText(getApplicationContext(), "Code : " + daftarDokterAdapter.get(0).getNama_dokter(), Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Model_tb_dokter>> call, Throwable t) {
                    Log.d("api", t.getMessage());
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
//                daftarMakananAdapter=db.getAllData_tb_makanan();
                }
            });
        } else {


            dokterAdapter = new DokterAdapter(getApplicationContext(), dokterArrayList, db);
            recyclerView.setAdapter(dokterAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

//            dokterAdapter = new DokterAdapter(getApplicationContext(), dokterArrayList, db);
//            mRecyclerView.setLayoutManager(mLayoutManager);
//            mRecyclerView.setAdapter(dokterAdapter);

        }
    }

}