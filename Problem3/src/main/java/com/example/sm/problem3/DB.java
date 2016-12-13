package com.example.sm.problem3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JSR on 2016-12-13.
 */

public class DB extends SQLiteOpenHelper {

    public DB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // 테이블 생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE list (id INTEGER PRIMARY KEY AUTOINCREMENT , name TEXT , salary INTEGER);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String name , String age , int salary) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO list values(null,'" + name +"','" + salary +"');";
        db.execSQL(sql);
    }

    public void drop() {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DROP TABLE list;";
        db.execSQL(sql);
        onCreate(db);
    }

    public String print() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from list;";
        Cursor c = db.rawQuery(sql,null);
        String result = "";
        while(c.moveToNext()) {
            result += c.getString(1) + "  " ;
            result += c.getString(2) + "\n" ;

        }
        return result;
    }
}
