package com.example.medihealth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medihealth.model.Model_tb_dokter;

import java.util.ArrayList;

public class DokterAdapter extends RecyclerView.Adapter<DokterAdapter.MyViewHolder> {

    private ArrayList<Model_tb_dokter> mDokterList;
    String namaAdapter[], spesialisAdapter[], jamAdapter[];
    Context context;

    private DBHelper myDB;
    public  DokterAdapter(Context context, ArrayList<Model_tb_dokter> datarDokterList, DBHelper myDB){

        this.context = context;
        this.mDokterList = datarDokterList;
        this.myDB = myDB;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dokter_rv_row, parent, false);


        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.text_nama.setText(mDokterList.get(position).getNama_dokter());
        holder.text_spesialis.setText(mDokterList.get(position).getSpesialis());
        holder.text_jam.setText(mDokterList.get(position).getJam());

    }

    @Override
    public int getItemCount() {
        return mDokterList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView text_nama, text_spesialis, text_jam;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_nama = itemView.findViewById(R.id.nama_dokter);
            text_spesialis = itemView.findViewById(R.id.spesialis_dokter);
            text_jam = itemView.findViewById(R.id.jam_dokter);
        }
    }

    public ArrayList<Model_tb_dokter> getmDokterList(){
        return mDokterList;
    }
}
