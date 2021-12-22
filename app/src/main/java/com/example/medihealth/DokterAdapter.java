package com.example.medihealth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DokterAdapter extends RecyclerView.Adapter<DokterAdapter.MyViewHolder> {

    String namaAdapter[], spesialisAdapter[], jamAdapter[];
    Context context;

    public  DokterAdapter(Context ct, String nama[], String spesialis[], String jam[]){

        context = ct;
        namaAdapter = nama;
        spesialisAdapter = spesialis;
        jamAdapter = jam;

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
        holder.text_nama.setText(namaAdapter[position]);
        holder.text_spesialis.setText(spesialisAdapter[position]);
        holder.text_jam.setText(jamAdapter[position]);

    }

    @Override
    public int getItemCount() {
        return namaAdapter.length;
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
}
