package com.example.medihealth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class DBAdapter extends RecyclerView.Adapter<DBAdapter.MyViewHolder>  {

    private Context context;
    private Activity activity;
    private ArrayList id_rsv, poli, dokter, asuransi, tglrsv;



    public DBAdapter(Activity activity, Context context, ArrayList id_rsv, ArrayList poli, ArrayList dokter,
                     ArrayList asuransi, ArrayList tglrsv){
        this.activity = activity;
        this.context = context;
        this.id_rsv = id_rsv;
        this.poli = poli;
        this.dokter = dokter;
        this.asuransi = asuransi;
        this.tglrsv = tglrsv;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.reservasi_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
//        holder.book_id_txt.setText(String.valueOf(id_pur.get(position)));
        holder.rsv_poli_txt.setText(String.valueOf(poli.get(position)));
        holder.rsv_dokter_txt.setText(String.valueOf(dokter.get(position)));
        holder.rsv_tanggal_txt.setText(String.valueOf(tglrsv.get(position)));
//        holder.book_author_txt.setText(String.valueOf(jumlah.get(position)));
//        holder.book_pages_txt.setText(String.valueOf(daerah.get(position)));
        //Recyclerview onClickListener
        holder.rsvRowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailReservasiActivity.class);
                intent.putExtra("id_rsv", String.valueOf(id_rsv.get(position)));
                intent.putExtra("poli", String.valueOf(poli.get(position)));
                intent.putExtra("dokter", String.valueOf(dokter.get(position)));
                intent.putExtra("asuransi", String.valueOf(asuransi.get(position)));
                intent.putExtra("tglrsv", String.valueOf(tglrsv.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return id_rsv.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView rsv_poli_txt, rsv_dokter_txt, rsv_tanggal_txt;
        LinearLayout rsvRowLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rsv_poli_txt = itemView.findViewById(R.id.rsv_poli);
            rsv_dokter_txt = itemView.findViewById(R.id.rsv_dokter);
            rsv_tanggal_txt = itemView.findViewById(R.id.rsv_tanggal);
//            book_pages_txt = itemView.findViewById(R.id.book_pages_txt);
            rsvRowLayout = itemView.findViewById(R.id.newrow);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            rsvRowLayout.setAnimation(translate_anim);
        }

    }

}
