package com.example.t4_projecte2_portfolio;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.example.t4_projecte2_portfolio.BDD.DBInterface;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class CryptoList extends ListActivity {

    private ListAdapter adapter;
    private ImageView mImatge;
    private DBInterface bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_crypto_list);
        hashmap();
    }

    public void hashmap() {
        mImatge = (ImageView) findViewById(R.id.imageViewCryptoList);
        bd = new DBInterface(this);
        bd.obre();
        Cursor c = bd.obtenirAllCrypto();
        c.moveToFirst();
        ArrayList<HashMap<String, String>> llista = new ArrayList<HashMap<String, String>>();
        while (!c.isAfterLast()) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id", c.getString(0));
            map.put("nom", c.getString(1));
            //map.put("email", c.getBlob(2));
            map.put("other", c.getString(3));
            llista.add(map);
            c.moveToNext();
        }

        bd.tanca();
        adapter = new SimpleAdapter(this, llista, R.layout.activity_crypto_list,
                new String[]{"id", "nom", "other"}, new int[]{R.id.textViewLlistaID, R.id.textViewLlistaNom, R.id.textView20});
        setListAdapter(adapter);

    }
}