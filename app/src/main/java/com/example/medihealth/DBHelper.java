package com.example.medihealth;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String db_name="db_medihealth";
    public static final String table_user="tb_user";  public static final String row_id_user ="id_user";
    public static final String row_nama_user ="nama_user";
    public static final String row_email ="email";
    public static final String row_password="password";
    public static final String row_lahir="tgl_lahir";
    public static final String row_jk="jenis_kelamin";
    public static final int VER=2;

    private SQLiteDatabase db;


    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
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

    public boolean insertDataUser(String nama_user, String password, String tgl_lahir, String email, String jenis_kelamin){

        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(row_email, nama_user);
        contentValues.put(row_password, password);
        contentValues.put(row_email, email);
        contentValues.put(row_lahir, tgl_lahir);
        contentValues.put(row_jk, jenis_kelamin);
        long result = DB.insert(table_user,null ,contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
}
