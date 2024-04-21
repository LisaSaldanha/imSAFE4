package com.example.imsafe3;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String name;
    FloatingActionButton fab;
    ListView listView;
    ArrayList<LvItem> arrayList = new ArrayList<>();

    // One Button
    Button btnUpload;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.lv);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.fabitem);
                final EditText editTextText = dialog.findViewById(R.id.editTextText);
                Button btnUpload = dialog.findViewById(R.id.btnUpload);

                btnUpload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        name = editTextText.getText().toString();

                        LvItem lvItem = new LvItem();
                        lvItem.setName(name);
                        arrayList.add(lvItem);

                        dialog.dismiss();

                        ContactAdapter contactAdapter = new ContactAdapter(arrayList, MainActivity.this);
                        listView.setAdapter(contactAdapter);
                    }
                });

                dialog.show();
            }
        });
    }

}
