package com.example.expriment7;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ContactProvider extends ContentProvider {

    // 匹配码
    public static final int CONTACT_DIR=0;
    public static final int CONTACT_ITEM=1;

    public static final String AUTHORITY = "com.example.expriment7.ContactProvider";
    private static UriMatcher uriMatcher;
    private ContactsDB dbHandler;

    static{

        // 不匹配则采用UriMatcher.NO_MATCH  -1 返回
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // 对等待匹配的URI进行匹配操作，必须符合：com.example.expriment7.ContactProvider/contacts 格式
        // 匹配返回 CONTACT_DIR (0)
        uriMatcher.addURI(AUTHORITY,"contacts",CONTACT_DIR);

        // #表示数字 com.example.expriment7.ContactProvider/contacts/2
        // 匹配返回 CONTACT_ITEM （1）
        uriMatcher.addURI(AUTHORITY,"contacts/#",CONTACT_ITEM);

    }

    // 创建数据库
    @Override
    public boolean onCreate() {
        dbHandler = new ContactsDB(this.getContext(), "contacts", null, 1);
        return false;
    }
    // 查询
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        Cursor cursor = null;

        switch (uriMatcher.match(uri)){
            case CONTACT_DIR: //查询所有信息
                cursor = db.query("contacts", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case CONTACT_ITEM: //查询单行数据
                String displayname = uri.getPathSegments().get(1);
                cursor = db.query("contacts", projection, "displayname = ?", new String[]{displayname}, null, null
                ,sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());

        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch(uriMatcher.match(uri)){
            case CONTACT_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.expriment7.ContactProvider.Contacts";
            case CONTACT_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.expriment7.ContactProvider.Contacts";

        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)){
            case CONTACT_DIR:
            case CONTACT_ITEM:
                long newContacts = db.insert("contacts", null, values);
                uriReturn = Uri.parse("content//"+AUTHORITY+"/Contacts/"+newContacts);
                break;
            default:throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
        return uriReturn;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
       SQLiteDatabase db = dbHandler.getWritableDatabase();
       int deleteData = 0;
       switch (uriMatcher.match(uri)){
           case CONTACT_DIR:
               deleteData = db.delete("contacts", selection, selectionArgs);
               break;
           case CONTACT_ITEM:
               String displayname = uri.getPathSegments().get(1);
               db.delete("contacts", "displayname = ?", new String[]{displayname});
               break;
           default:throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
       }


        return deleteData;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        int update = 0;
        switch (uriMatcher.match(uri)){
            case CONTACT_DIR:
                update = db.update("contacts", values, selection, selectionArgs);
                break;
            case CONTACT_ITEM:
                String displayname = uri.getPathSegments().get(1);
                update = db.update("contacts", values, "displapname = ?", new String[]{displayname});
                break;
            default:throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
        return update;
    }
}
