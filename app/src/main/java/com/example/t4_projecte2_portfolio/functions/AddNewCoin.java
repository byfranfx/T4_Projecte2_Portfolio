package com.example.t4_projecte2_portfolio.functions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t4_projecte2_portfolio.BDD.DBInterface;
import com.example.t4_projecte2_portfolio.Dashboard;
import com.example.t4_projecte2_portfolio.MainActivity;
import com.example.t4_projecte2_portfolio.R;

import java.io.ByteArrayOutputStream;

public class AddNewCoin extends AppCompatActivity {

    private int GALLERY_REQUEST_CODE = 1;
    private DBInterface bd;
    private EditText abr, nom, value;
    private Button AddNewCoinSender;
    private int STORAGE_PERMISSION_CODE = 23;
    private static final int PICK_IMAGE = 88;
    private byte[] bitmapmap;
    private ImageView mImatge;
    private Bitmap imatge_bitmap;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_coin);

        //
        Bundle b1 = getIntent().getExtras();
        Bundle b2 = getIntent().getExtras();

        String s1 = b1.getString("key1");
        String s2 = b2.getString("key2");
        int foo = Integer.parseInt(s2);
        //


        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);

        abr = findViewById(R.id.editTextAddNewCoinABR);
        nom = findViewById(R.id.editTextAddNewCoinNom);
        value = findViewById(R.id.editTextAddNewCoinValue);
        AddNewCoinSender = (Button) findViewById(R.id.buttonAddNewCoinSender);

        // image
        mImatge = (ImageView)findViewById(R.id.imageViewAddNewCoinImg);
        mImatge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });


        AddNewCoinSender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bd = new DBInterface(getApplicationContext());
                bd.obre();
                //
                String svalue = value.getText().toString();
                int value = Integer.parseInt(svalue);
                if (bd.addNewCoin(abr.getText().toString(), nom.getText().toString(), bitmapmap, value) != -1) {
                    Toast.makeText(getApplicationContext(), "Coin agregada correctament", Toast.LENGTH_SHORT).show();
                    if (bitmapmap != null) {
                        Toast.makeText(getApplicationContext(), "Bitmap amb dades", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Bitmap sense dades", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error, la Crypto no se ha creado!", Toast.LENGTH_SHORT).show();
                }
                //
                bd.tanca();
                //finish();
                returnLoggin(null);
            }
        });
    }

    // abrir galeria
    private void openGallery(){
        //Cream l'Intent amb l'acció ACTION_PICK
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Establim tipus d'imatges, per tant només s'acceptaran els tipus imagtge
        intent.setType("image/*");
        //Establim uns tipus de format de fotografia per assegurar-nos d'acceptar només aquest tipus de format jpg i png
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Llançam l'Intent
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }


    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        // Result code is RESULT_OK only if the user selects an Image
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY_REQUEST_CODE) {//data.getData return the content URI for the selected Image
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();
                //Get the column index of MediaStore.Images.Media.DATA
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                //Gets the String value in the column
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                // Set the Image in ImageView after decoding the String
                imatge_bitmap = BitmapFactory.decodeFile(imgDecodableString);

                ByteArrayOutputStream blob = new ByteArrayOutputStream();
                imatge_bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /* Ignored for PNGs */, blob);
                bitmapmap = blob.toByteArray();
                mImatge.setImageBitmap(imatge_bitmap);

            }
        }
    }

    // Menu Dashboard
    public void returnLoggin(View view) {
        finish();

        Intent intent = this.getIntent();
        String s1 = intent.getStringExtra("key1");
        String s2 = intent.getStringExtra("key2");

        Intent i = new Intent(this, Dashboard.class);

        i.putExtra("key1", s1);
        i.putExtra("key2", s2);

        startActivity(i);
    }
}