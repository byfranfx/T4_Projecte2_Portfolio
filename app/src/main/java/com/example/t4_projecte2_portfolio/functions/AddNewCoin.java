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
import android.widget.Toast;

import com.example.t4_projecte2_portfolio.BDD.DBInterface;
import com.example.t4_projecte2_portfolio.Dashboard;
import com.example.t4_projecte2_portfolio.MainActivity;
import com.example.t4_projecte2_portfolio.R;

import java.io.ByteArrayOutputStream;

public class AddNewCoin extends AppCompatActivity {

    private int GALLERY_REQUEST_CODE = 1;
    private DBInterface bd;
    private EditText abr, nom;
    private Button AddNewCoinSender;
    private int STORAGE_PERMISSION_CODE = 23;
    private static final int PICK_IMAGE = 88;
    private byte[] bitmapmap;
    private ImageView mImatge;
    Uri imageUri;
    private Bitmap imatge_bitmap;



    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_coin);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);

        abr = findViewById(R.id.editTextAddNewCoinABR);
        nom = findViewById(R.id.editTextAddNewCoinNom);
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

                if (bd.addNewCoin(abr.getText().toString(), nom.getText().toString(), bitmapmap) != -1) {
                    Toast.makeText(getApplicationContext(), "Coin agregada correctament", Toast.LENGTH_SHORT).show();
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
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            foto_gallery.setImageURI(imageUri);

        }
    }*/

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
        Intent i = new Intent(this, Dashboard.class);
        startActivity(i);
    }
}