package com.example.scanpaper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {
    private static final String TABLE_CREATE = "CREATE TABLE productos (codigo NVARCHAR(100) PRIMARY KEY , articulo TEXT, minimo INTEGER, costo REAL, familia TEXT)";

    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL("INSERT INTO productos VALUES('P01A2','Papel bond A2','6','14.5','FAMILIA PAPEL BOND')");
        db.execSQL("INSERT INTO productos VALUES('P01A3','Papel bond A3','12','15.5','FAMILIA PAPEL BOND')");
        db.execSQL("INSERT INTO productos VALUES('P01A4','Papel bond A4','24','15.5','FAMILIA PAPEL BOND')");
        db.execSQL("INSERT INTO productos VALUES('P01A5','Papel bond A5','36','16.5','FAMILIA PAPEL BOND')");
        db.execSQL("INSERT INTO productos VALUES('P01A6','Papel bond A6','6','17.5','FAMILIA PAPEL BOND')");
        db.execSQL("INSERT INTO productos VALUES('P02A0','Papel dúplex A0','4','11.5','FAMILIA PAPEL DÚPLEX')");
        db.execSQL("INSERT INTO productos VALUES('P02A1','Papel dúplex A1','4','12.5','FAMILIA PAPEL DÚPLEX')");
        db.execSQL("INSERT INTO productos VALUES('P02A2','Papel dúplex A2','6','13.5','FAMILIA PAPEL DÚPLEX')");
        db.execSQL("INSERT INTO productos VALUES('P02A3','Papel dúplex A3','12','14.5','FAMILIA PAPEL DÚPLEX')");
        db.execSQL("INSERT INTO productos VALUES('P02A4','Papel dúplex A4','24','15.5','FAMILIA PAPEL DÚPLEX')");
        db.execSQL("INSERT INTO productos VALUES('P03A0','Papel kraft A0','4','11.5','FAMILIA PAPEL KRAFT')");
        db.execSQL("INSERT INTO productos VALUES('P03A1','Papel kraft A1','4','12.5','FAMILIA PAPEL KRAFT')");
        db.execSQL("INSERT INTO productos VALUES('P03A2','Papel kraft A2','6','13.5','FAMILIA PAPEL KRAFT')");
        db.execSQL("INSERT INTO productos VALUES('P03A3','Papel kraft A3','12','14.5','FAMILIA PAPEL KRAFT')");
        db.execSQL("INSERT INTO productos VALUES('P03A4','Papel kraft A4','24','15.5','FAMILIA PAPEL KRAFT')");
        db.execSQL("INSERT INTO productos VALUES('P04A0','Papel periodico A0','4','11.5','FAMILIA PAPEL PERIODICO')");
        db.execSQL("INSERT INTO productos VALUES('P04A1','Papel periodico A1','4','12.5','FAMILIA PAPEL PERIODICO')");
        db.execSQL("INSERT INTO productos VALUES('P04A2','Papel periodico A2','6','13.5','FAMILIA PAPEL PERIODICO')");
        db.execSQL("INSERT INTO productos VALUES('P04A3','Papel periodico A3','12','14.5','FAMILIA PAPEL PERIODICO')");
        db.execSQL("INSERT INTO productos VALUES('P04A4','Papel periodico A4','24','15.5','FAMILIA PAPEL PERIODICO')");
        db.execSQL("INSERT INTO productos VALUES('P04A5','Papel periodico A5','36','16.5','FAMILIA PAPEL PERIODICO')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS productos");
        onCreate(db);
    }

}




