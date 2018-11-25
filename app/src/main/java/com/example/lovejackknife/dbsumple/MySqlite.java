package com.example.lovejackknife.dbsumple;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class MySqlite extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    String text1="";
    double text2;
    double text3;

    public MySqlite(Context context) {
        super(context, "mydb", null, 1);
        // 2番目の引数は、データベース名です。
        // nullにした場合はメモリ上に、データベース名を指定した場合はデータベースファイルが作成されます。
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL(
                "create table locations"
                        + "( _id integer primary key autoincrement, number text not null, latitude double, longitude double );");
        /*
        db.beginTransaction();
        try {
            SQLiteStatement stmt =
                    db.compileStatement(
                            "insert into locations"
                                    + "( number, latitude, longitude )"
                                    + " values ( ?, ?, ? );"
                    );
            stmt.bindString(1, "1000000");
            stmt.bindDouble(2, 34.1250);
            stmt.bindDouble(3, 135.12);
            stmt.executeInsert();
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        */
    }

    public void setDB(SQLiteDatabase db) {
        this.db = db;
    }
    public void insertData(String number, double latitude, double longitude) {
        db.beginTransaction();
        try {
            SQLiteStatement stmt =
                    db.compileStatement(
                            "insert into locations"
                                    + "( number, latitude, longitude )"
                                    + " values ( ?, ?, ? );"
                    );
            stmt.bindString(1, number);
            stmt.bindDouble(2, latitude);
            stmt.bindDouble(3, longitude);
            stmt.executeInsert();
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists locationsE;");
        onCreate(db);
    }
}
