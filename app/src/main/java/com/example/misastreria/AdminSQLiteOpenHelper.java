package com.example.misastreria;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table clientes(codigo integer primary key autoincrement, cedula text, nombre text, apellido text, telefono text, direccion text, correo text)");
        db.execSQL("create table prendas(codigo integer primary key autoincrement,codigo_cliente int, tipo_prenda text, fecha_ingreso text, fecha_salida text, color text, marca text, cantidad int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
