package com.example.expriment7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView contactsview;

    //List<Map<String, String>> contactsList = new ArrayList<Map<String, String>>();
    List<Contacts> contactsList = new ArrayList<Contacts>();

    //SimpleAdapter adapter;

    ContactAdapt adapter;

    Contacts contacts;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        contacts = new Contacts();
//        contactsview = findViewById(R.id.list_item);
//        for (int i = 0; i <5 ; i++) {
//            contacts.setDisplayname("displayname");
//            contacts.setNumber("number");
//            contactsList.add(contacts);
//        }
//
//        adapter = new ContactAdapt(MainActivity.this,R.layout.item,contactsList);
//        contactsview.setAdapter(adapter);

        //判断用户是否已经授权给我们了 如果没有，调用下面方法向用户申请授权，之后系统就会弹出一个权限申请的对话框
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 1);

        }else {
            readContacts();
        }
    }

    // 获取联系人信息
    public void readContacts() {
        // 获取联系人信息

        // 获取listview
        contactsview = findViewById(R.id.list_item);

        //contactsList = new ArrayList<Map<String, String>>();

        Cursor cursor = null;

        try {
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

            if (null != cursor) {
                while (cursor.moveToNext()) {
                    String displayname = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contacts = new Contacts();
                    contacts.setDisplayname(displayname);
                    contacts.setNumber(number);
//                    Map<String, String> map = new HashMap<String, String>();
//
//                    map.put("displayname",displayname);
//                    map.put("number", number);
//                    contactsList.add(map);
                    Log.d("a", displayname + ":" + number);
                    contactsList.add(contacts);
                }

                for (int i = 0; i < contactsList.size() ; i++) {
                    String name = contactsList.get(i).displayname;
                    String number = contactsList.get(i).number;
                    Log.d("TAG", "readContacts: "+name+":"+number);
                }
                adapter = new ContactAdapt(MainActivity.this,R.layout.item,contactsList);
                contactsview.setAdapter(adapter);


                // listview显示
//                adapter = new SimpleAdapter(MainActivity.this, contactsList, R.layout.item,
//                        new String[]{"displayname", "number"},
//                        new int[]{R.id.displayname, R.id.number});
//                contactsview.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }
    }


    // 用户同意则调用readContacts（）方法，失败则会弹窗提示失败
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts();
                } else {
                    Toast.makeText(this, "获取联系人权限失败", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
