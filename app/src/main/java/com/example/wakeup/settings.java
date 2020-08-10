package com.example.wakeup;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;

public class settings extends AppCompatActivity {

   static ArrayList <String> contacts = new ArrayList<>();


    public void pickcontacts()
    {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts/people"));
            startActivityForResult(intent, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1) {
            Cursor cursor = null;
            String phoneNumber = "";
            int phoneIdx = 0;
            try {
                Uri result = data.getData();
                String id = result.getLastPathSegment();
                cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{id}, null);
                phoneIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);
                if (cursor.moveToFirst()) {
                    while (cursor.isAfterLast() == false) {
                        phoneNumber = cursor.getString(phoneIdx);
                        Toast.makeText(getApplicationContext(),phoneNumber,Toast.LENGTH_SHORT).show();
                        cursor.moveToNext();
                    }
                } else {
                    //no results actions
                }
            } catch (Exception e) {
                //error actions
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ListView listView = findViewById(R.id.listView);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Add Contact Number");
        arrayList.add("Change the ringtone");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if( position == 0 )
                {
                    pickcontacts();
                }else {

                }
            }
        });
    }
}
