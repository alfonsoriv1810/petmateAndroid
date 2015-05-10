package com.petmate.fcfm.petmate.sqllite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Luis Mares on 05/04/2015.
 */

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Usuario(id integer primary key autoincrement, nombre text,contrasena text)");
        db.execSQL("create table Razas(id integer primary key autoincrement, idRaza integer, nombreRaza text, fotoRaza text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnte, int versionNue) {
        db.execSQL("drop table if exists Usuario");
        db.execSQL("create table Usuario(id integer primary key autoincrement, nombre text,contrasena text)");

        db.execSQL("drop table if exist Razas");
        db.execSQL("create table Razas(id integer primary key autoincrement, idRaza integer, nombreRaza text, fotoRaza text)");
    }
}
