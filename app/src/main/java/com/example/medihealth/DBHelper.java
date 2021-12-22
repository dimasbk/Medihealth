package com.example.medihealth;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String db_name="db_medihealth";
    public static final String table_user="tb_user";
    public static final String row_id_user ="id";
    public static final String row_nama_user ="nama_user";
    public static final String row_email ="email";
    public static final String row_password="password";
    public static final String row_lahir="tgl_lahir";
    public static final String row_jk="jenis_kelamin";


    public static final String table_reservasi="tb_reservasi";
    public static final String row2_id_reservasi="id_reservasi";
    public static final String row2_poli="poli";
    public static final String row2_dokter="dokter";
    public static final String row2_asuransi="asuransi";
    public static final String row2_tanggalrsv="tanggalrsv";

    private Context context;

    private SQLiteDatabase db;

//    SharedPreferences getDataMail, getDataId;
//
//    getDataMail = getSharedPreferences("SESSION_mail", MODE_PRIVATE);
//    getDataId = getSharedPreferences("SESSION_id", MODE_PRIVATE);

    public DBHelper(@Nullable Context context) {
        super(context, db_name, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + table_user +" (id INTEGER PRIMARY KEY AUTOINCREMENT,nama_user TEXT,email TEXT,password TEXT, tgl_lahir TEXT, jenis_kelamin TEXT)");
        db.execSQL("create table " + table_reservasi +" (id_reservasi INTEGER PRIMARY KEY AUTOINCREMENT,poli TEXT,dokter TEXT,asuransi TEXT, tanggalrsv TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ table_user);
        db.execSQL("DROP TABLE IF EXISTS "+ table_reservasi);
        onCreate(db);
    }

    public boolean insertDataUser(String nama, String email, String pass, String lahir, String jk){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.row_nama_user, nama);
        values.put(DBHelper.row_email, email);
        values.put(DBHelper.row_password, pass);
        values.put(DBHelper.row_lahir, lahir);
        values.put(DBHelper.row_jk, jk);
        long insert = DB.insert(table_user,null,values);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }


    public boolean updateDataProfil(String row_id, String nama, String email, String pass, String lahir, String jk){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.row_nama_user, nama);
        values.put(DBHelper.row_email, email);
        values.put(DBHelper.row_password, pass);
        values.put(DBHelper.row_lahir, lahir);
        values.put(DBHelper.row_jk, jk);
        long result = DB.update(table_user, values,"id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
            return true;
        }
    }


    public boolean insertDataReservasi(String poli, String dokter, String asuransi, String tanggalrsv){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.row2_poli, poli);
        values.put(DBHelper.row2_dokter, dokter);
        values.put(DBHelper.row2_asuransi, asuransi);
        values.put(DBHelper.row2_tanggalrsv, tanggalrsv);
        long insert = DB.insert(table_reservasi,null,values);
        if (insert == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public String readUserData(String email){
        SQLiteDatabase DB = this.getReadableDatabase();
        String [] columns = {row_id_user};
        String selections = row_email + "=?";
        String [] selectionArgs = {email};

        Cursor cursor = DB.query(table_user, columns, selections, selectionArgs, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        String id = cursor.getString(0);
        return id;

    }


    public Cursor readReservasiData(){
        String query = "SELECT * FROM " + table_reservasi;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public boolean updateDataReservasi(String row_id, String poli, String dokter, String asuransi, String tanggalrsv){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.row2_poli, poli);
        values.put(DBHelper.row2_dokter, dokter);
        values.put(DBHelper.row2_asuransi, asuransi);
        values.put(DBHelper.row2_tanggalrsv, tanggalrsv);
        long result = DB.update(table_reservasi, values,"id_reservasi=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
            return true;
        }
    }


    public boolean deleteDataReservasi(String row_id){
        SQLiteDatabase DB = this.getWritableDatabase();
        long result = DB.delete(table_reservasi,"id_reservasi=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(context, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public boolean checkUser(String email, String password){
        SQLiteDatabase DB = this.getReadableDatabase();
        String [] columns = {row_id_user};
        String selections = row_email + "=?" + " and " + row_password + "=?";
        String [] selectionArgs = {email, password};
        Cursor cursor = DB.query(table_user, columns, selections, selectionArgs, null, null, null);
        int count = cursor.getCount();
        DB.close();
        if(count>0){
            return true;
        }else{
            return false;
        }
    }

    Cursor readEmailProfil(String id){
        SQLiteDatabase DB = this.getReadableDatabase();
        String [] columns = {row_email};
        String selections = row_id_user + "=?";
        String [] selectionArgs = {id};

        Cursor cursor = DB.query(table_user, columns, selections, selectionArgs, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    Cursor readNamaProfil(String id){
        SQLiteDatabase DB = this.getReadableDatabase();
        String [] columns = {row_nama_user};
        String selections = row_id_user + "=?";
        String [] selectionArgs = {id};

        Cursor cursor = DB.query(table_user, columns, selections, selectionArgs, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    Cursor readTglProfil(String id){
        SQLiteDatabase DB = this.getReadableDatabase();
        String [] columns = {row_lahir};
        String selections = row_id_user + "=?";
        String [] selectionArgs = {id};

        Cursor cursor = DB.query(table_user, columns, selections, selectionArgs, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    Cursor readJkProfil(String id){
        SQLiteDatabase DB = this.getReadableDatabase();
        String [] columns = {row_jk};
        String selections = row_id_user + "=?";
        String [] selectionArgs = {id};

        Cursor cursor = DB.query(table_user, columns, selections, selectionArgs, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    Cursor readPassProfil(String id){
        SQLiteDatabase DB = this.getReadableDatabase();
        String [] columns = {row_password};
        String selections = row_id_user + "=?";
        String [] selectionArgs = {id};

        Cursor cursor = DB.query(table_user, columns, selections, selectionArgs, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }



}
