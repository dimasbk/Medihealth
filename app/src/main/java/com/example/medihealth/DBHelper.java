package com.example.medihealth;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String db_name="db_medihealth";
    public static final String table_user="tb_user";
    public static final String row_id_user ="id_user";
    public static final String row_nama_user ="nama_user";
    public static final String row_email ="email";
    public static final String row_password="password";
    public static final String row_lahir="tgl_lahir";
    public static final String row_jk="jenis_kelamin";
    public static final int VER=2;

    private SQLiteDatabase db;


    public DBHelper(@Nullable Context context) {
        super(context, db_name, null, VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        db.execSQL("create table " + table_user +" (id INTEGER PRIMARY KEY AUTOINCREMENT,nama_user TEXT,email TEXT,password TEXT, tanggal_lahir TEXT, jenis_kelamin TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ table_user);
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
        long insert = db.insert(table_user,null,values);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkUser(String email, String password){
        SQLiteDatabase DB = this.getWritableDatabase();
        String [] columns = {row_id_user};
        String selections = row_email + "=?" + " and " + row_password + "=?";
        String [] selectionArgs = {email, password};
        Cursor cursor = db.query(table_user, columns, selections, selectionArgs, null, null, null);
        int count = cursor.getCount();
        db.close();
        if(count>0){
            return true;
        }else{
            return false;
        }
    }
}
