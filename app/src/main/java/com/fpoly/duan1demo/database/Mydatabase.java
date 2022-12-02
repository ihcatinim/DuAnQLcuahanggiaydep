package com.fpoly.duan1demo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Mydatabase extends SQLiteOpenHelper {
    public Mydatabase(@Nullable Context context) {
        super(context, "GIAYDEP.VN", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(KhachHangDAO.SQL_NGUOIDUNG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + KhachHangDAO.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
