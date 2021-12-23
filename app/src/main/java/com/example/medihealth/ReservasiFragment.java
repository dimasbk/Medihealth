package com.example.medihealth;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReservasiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReservasiFragment extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView empty_imageview;
    TextView no_data;
    LinearLayoutManager layoutManager;

    DBHelper myDB;
    ArrayList<String> id_rsv, poli_rsv, dokter_rsv, asuransi_rsv, tglrsv_rsv;
    DBAdapter DBadapter;
    MenuActivity activity = (MenuActivity) getActivity();
    String u_id;

    SharedPreferences getDataId;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReservasiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReservasiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReservasiFragment newInstance(String param1, String param2) {
        ReservasiFragment fragment = new ReservasiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reservasi, container, false);

        myDB = new DBHelper(getContext());
        id_rsv = new ArrayList<>();
        poli_rsv = new ArrayList<>();
        dokter_rsv = new ArrayList<>();
        asuransi_rsv = new ArrayList<>();
        tglrsv_rsv = new ArrayList<>();
        getDataId = getActivity().getSharedPreferences("SESSION_id", MODE_PRIVATE);
        u_id = getDataId.getString("SESSION_id","");



//        FloatingActionButton add_reservasi;
//        add_reservasi = view.findViewById(R.id.floatingActionButtonreservasi);
//        add_reservasi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent med = new Intent(getActivity(), ReservasiActivity.class);
//                startActivity(med);
//            }
//        });

        storeDataInArrays(u_id);


        Collections.reverse(id_rsv);
        Collections.reverse(poli_rsv);
        Collections.reverse(dokter_rsv);
        Collections.reverse(asuransi_rsv);
        Collections.reverse(tglrsv_rsv);

        recyclerView = view.findViewById(R.id.recyclerView);
        add_button = view.findViewById(R.id.add_button);
        empty_imageview = view.findViewById(R.id.empty_imageview);
        no_data = view.findViewById(R.id.no_data);

        DBAdapter dbAdapter = new DBAdapter(getActivity(), getContext(), id_rsv, poli_rsv, dokter_rsv, asuransi_rsv, tglrsv_rsv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(dbAdapter);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReservasiActivity.class);
                startActivity(intent);
            }
        });



//        DBadapter = new DBAdapter(getContext(), id_rsv, poli_rsv, dokter_rsv,
//                asuransi_rsv, tglrsv_rsv);
//        layoutManager = new LinearLayoutManager(view.getContext());
//        recyclerView = view.findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setAdapter(DBadapter);


        return view;
    }

//    public void startActivityForResult (int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1234 && resultCode == 1) {
//            // do your things
////            attach();
////            ReservasiFragment = new ReservasiFragment();
////            ft.beginTransaction(ReservasiFragment)
////                    .attach()
////                    .add(R.id.container, ReservasiFragment)
////                    .commit();
//        }
//    }

//    @Override
//    protected void onActivityForResult(Intent intent, int requestCode) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 1){
//            recreate();
//        }
//    }

    void storeDataInArrays(String u_id){
        Cursor cursor = myDB.readReservasiData(u_id);
        if(cursor.getCount() == 0){
//            Toast.makeText(activity,"Tidak Ada Reservasi!",Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                id_rsv.add(cursor.getString(0));
                poli_rsv.add(cursor.getString(1));
                dokter_rsv.add(cursor.getString(2));
                asuransi_rsv.add(cursor.getString(3));
                tglrsv_rsv.add(cursor.getString(4));
            }
        }
    }


//    @Override
//    public void onResume() {
//        super.onResume();
//        DBadapter.notifyDataSetChanged();
//    }

}