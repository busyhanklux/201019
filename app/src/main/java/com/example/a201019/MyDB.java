package com.example.a201019;

import android.database.sqlite.SQLiteDatabase;

public class MyDB {
    public SQLiteDatabase db = null;
    private final static String DATABASE_NAME = "db1.db";   //資料庫
    private final static String TABLE_NAME = "table01";     //資料表
    private final static String _ID = "_id";                //資料表欄位
    private final static String NAME = "name";
    private final static String PRICE = "price";

    private final static String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME +  "(" + _ID + "INTEGER PRIMARYKEY," +
                    NAME + "TEXT," + PRICE + "INTERGER)";
}
