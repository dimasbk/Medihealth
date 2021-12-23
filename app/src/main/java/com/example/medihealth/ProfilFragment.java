package com.example.medihealth;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View mView;
    public Button button_logout;
    FloatingActionButton button_edit;
    DBHelper dbHelper;
    TextView nama_depan, nama_belakang, email, tgl_lahir, jenis_kelamin;

    String u_id, u_nama, u_email, u_tgl, u_jk;

    SharedPreferences getDataMail, getDataPass, getDataId;

//    SharedPreferences getDataId = getActivity().getSharedPreferences("SESSION_id", MODE_PRIVATE);

    public ProfilFragment() {
        // Required empty public constructor aa
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilFragment newInstance(String param1, String param2) {
        ProfilFragment fragment = new ProfilFragment();
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
        mView = inflater.inflate(R.layout.fragment_profil, container, false);

        dbHelper = new DBHelper(getActivity());

        getDataMail = getActivity().getSharedPreferences("SESSION_mail", MODE_PRIVATE);
        getDataPass = getActivity().getSharedPreferences("SESSION_pass", MODE_PRIVATE);
        getDataId = getActivity().getSharedPreferences("SESSION_id", MODE_PRIVATE);

        nama_depan = mView.findViewById(R.id.tv_id_nama_depan_user);
        nama_belakang = mView.findViewById(R.id.tv_id_nama_belakang_user);
        email = mView.findViewById(R.id.tv_id_input_view_email);
        tgl_lahir = mView.findViewById(R.id.tv_id_input_view_umur);
        jenis_kelamin = mView.findViewById(R.id.tv_id_input_view_jenis_kelamin);
        u_id = getDataId.getString("SESSION_id","");

        Cursor curEmail = dbHelper.readEmailProfil(u_id);
        Cursor curNama = dbHelper.readNamaProfil(u_id);
        Cursor curLahir = dbHelper.readTglProfil(u_id);
        Cursor curJk = dbHelper.readJkProfil(u_id);

        u_nama = curNama.getString(0);

        String lastName = "";
        String firstName= "";
        if(u_nama.split("\\w+").length>1){

            lastName = u_nama.substring(u_nama.lastIndexOf(" ")+1);
            firstName = u_nama.substring(0, u_nama.lastIndexOf(' '));
        }
        else{
            firstName = u_nama;
            lastName = "";
        }

        u_email = curEmail.getString(0);
        u_tgl = curLahir.getString(0);
        u_jk = curJk.getString(0);

        nama_depan.setText(firstName);
        nama_belakang.setText(lastName);
        email.setText(u_email);
        tgl_lahir.setText(u_tgl);
        jenis_kelamin.setText(u_jk);



        button_logout = (Button)mView.findViewById(R.id.btn_logout);
        button_edit = mView.findViewById(R.id.btn_edit);

        button_logout.setOnClickListener(this);
        button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditActivity.class);
                startActivity(intent);
            }
        });


//        dataAkun();

        return mView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logout:
                showDialogLogout();
                Toast.makeText(getActivity(), "Long pressing", Toast.LENGTH_SHORT).show();
                //do your stuff
                break;
            case R.id.btn_edit:
                showDialogLogout();
                Toast.makeText(getActivity(), "Long pressing", Toast.LENGTH_SHORT).show();
                //do your stuff
                break;
        }
        // implements your things
    }


    private void showDialogLogout(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireContext());
        alertDialogBuilder.setTitle("Yakin ingin keluar aplikasi?");

        alertDialogBuilder
//                .setMessage("Klik Ya untuk keluar!")
                .setIcon(R.drawable.logo)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        getDataMail.edit().clear().commit();
                        getDataPass.edit().clear().commit();
                        getDataId.edit().clear().commit();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }


//    void dataAkun(){
//        Cursor cursor = dbHelper.readUserData();
//
//        if(cursor.getCount() == 0){
////            empty_imageview.setVisibility(View.VISIBLE);
////            no_data.setVisibility(View.VISIBLE);
//        }else {
//            StringBuffer buffer = new StringBuffer();
//            while (cursor.moveToNext()) {
//                u_id = cursor.getString(0);
//                u_nama = cursor.getString(1);
//                u_email = cursor.getString(2);
//                u_password = cursor.getString(3);
//                u_tgl = cursor.getString(4);
//                u_jk = cursor.getString(5);
//            }
//        }
//    }

}