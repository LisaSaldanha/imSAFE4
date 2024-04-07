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

    // Image
    ImageView image;
    Button button_upload;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.lv);
        fab = findViewById(R.id.fab);
        image = findViewById(R.id.Image);
        button_upload = findViewById(R.id.button_upload);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.fabitem);
                final EditText editTextText = dialog.findViewById(R.id.editTextText);
                final ImageView imageViewDialog = dialog.findViewById(R.id.Image);

                Button btnUpload = dialog.findViewById(R.id.button_upload);
                Button btnAddToList = dialog.findViewById(R.id.btn);

                btnUpload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
                    }
                });

                btnAddToList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        name = editTextText.getText().toString();

                        LvItem lvItem = new LvItem();
                        lvItem.setName(name);

                        // Convert ImageView to Bitmap
                        BitmapDrawable drawable = (BitmapDrawable) imageViewDialog.getDrawable();
                        Bitmap imageBitmap = drawable.getBitmap();

                        // Add Bitmap to ArrayList
                        arrayList.add(lvItem);
                        //arrayList.add(imageBitmap);

                        dialog.dismiss();

                        ContactAdapter contactAdapter = new ContactAdapter(arrayList, MainActivity.this);
                        listView.setAdapter(contactAdapter);
                    }
                });

                dialog.show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
