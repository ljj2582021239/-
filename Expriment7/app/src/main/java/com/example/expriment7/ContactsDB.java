package com.example.expriment7;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;



public class ContactsDB extends SQLiteOpenHelper {

    // 创建数据库的sql 语句
    public static final String CREATE_CONTACTS="create table Contacts ("+
            "displayname TEXT primary key ,"+
            "number text,"+
            "sex text)";


    public ContactsDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_CONTACTS);

        ContentValues contentValues = new ContentValues();
        contentValues.put("displayname","杨过");
        contentValues.put("number","13412341234");
        contentValues.put("sex","男");
        db.insert("contacts",null, contentValues);

        contentValues.clear();

        contentValues.put("displayname","小龙女");
        contentValues.put("number","13412341235");
        contentValues.put("sex","女");
        db.insert("contacts",null, contentValues);

        contentValues.clear();


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
